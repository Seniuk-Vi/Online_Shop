package com.shop.command;

import com.mysql.cj.Session;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowHomePageCommand implements Command {
    private final int recordsPerPage = 12;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "homePage.jsp";

        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        List<Product> table = null;

        HttpSession session = null;
        session = req.getSession();
        Integer currentPage;
        currentPage = (Integer) session.getAttribute("currentPage");
        var page = req.getParameter("tPage");
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        int rowsCount = productDao.getRowsCount(con);
        if (rowsCount == 0) {
            req.getSession().setAttribute("errorMessage", "Show is empty");
            return "errorPage.jsp";
        }
        int noOfPages = rowsCount / recordsPerPage;
        if (noOfPages % recordsPerPage > 0) {
            noOfPages++;
        }

        if (currentPage == null) {
            currentPage = 1;

        }
        session.setAttribute("currentPage", currentPage);
        session.setAttribute("noOfPages", noOfPages);
        try {

            table = productDao.findAllPagination(con,  recordsPerPage, (currentPage - 1) * recordsPerPage);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(table.isEmpty()){
            return "errorPage.jsp";
        }

        System.out.println(table);
        req.getSession().setAttribute("products", table);
        return address;
    }
}
