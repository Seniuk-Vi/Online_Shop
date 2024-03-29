package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Category;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowHomePageCommand implements Command {
    final static Logger logger = Logger.getLogger(ShowHomePageCommand.class);
    private final String error = "Can't display store";
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "homePage.jsp";

        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        List<Product> table=null;
        List<Category> categories = null;
        CategoryDao categoryDao = new CategoryDao();
        int maxPrice = (int) productDao.getMaxPrice(con);
        int minPrice = (int) productDao.getMinPrice(con);
        req.getSession().setAttribute("priceMax", maxPrice);
        req.getSession().setAttribute("priceMin", minPrice);
        try {
            categories = categoryDao.findAll(con);
            table = selection(req, con, maxPrice, minPrice);
        } catch (DbException ex){
           req.getSession().setAttribute("fatalError",error505);
           req.getSession().setAttribute("fatalErrorMessage",error);
        }finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Can't close connection",ex);
            }
        }

        if (table != null&&table.isEmpty()) {
            req.getSession().setAttribute("errorMessage", "No results for you");
        }
        req.getSession().setAttribute("categories", categories);
        req.getSession().setAttribute("products", table);
        return address;
    }

    private List<Product> selection(HttpServletRequest req, Connection con, int maxPrice, int minPrice) throws DbException {
        ProductDao productDao = new ProductDao();
        String category = req.getParameter("category");
        String priceMinS = req.getParameter("priceMin");
        String priceMaxS = req.getParameter("priceMax");
        String order = req.getParameter("sort");
        String search = req.getParameter("search");
        if (category == null) {
            category = String.valueOf(req.getSession().getAttribute("category"));
        }
        if (search == null) {
            search = String.valueOf(req.getSession().getAttribute("search"));
        }
        if (priceMinS == null) {
            priceMinS = String.valueOf(req.getSession().getAttribute("priceMinS"));
        }
        if (priceMaxS == null) {
            priceMaxS = String.valueOf(req.getSession().getAttribute("priceMaxS"));
        }
        if (order == null) {
            order = String.valueOf(req.getSession().getAttribute("order"));
        }
        int minPriceS = minPrice;
        int maxPriceS = maxPrice;
        if (category.equals("null")) {
            category = "%";
        }
        String currentCategory = category;
        if (category.equals("all")) {
            category = "%";
        }
        if (category.equals("%")) {
            currentCategory = "all";
        }
        if (search.equals("null")) {
            search = "";
        }
        if (!priceMinS.equals("null")) {
            minPriceS = Integer.parseInt(priceMinS);
        }
        if (!priceMaxS.equals("null")) {
            maxPriceS = Integer.parseInt(priceMaxS);
        }
        if (order.equals("null")) {
            order = "price(asc)";
        }
        String way = "ASC";
        String sort;
        switch (order) {
            case "price(asc)":
                sort = "price";
                break;
            case "price(desc)":
                sort = "price";
                way = "DESC";
                break;
            case "newest":
                sort = "model_year";
                break;
            case "oldest":
                sort = "model_year";
                way = "DESC";
                break;
            case "a-z":
                sort = "title";
                break;
            case "z-a":
                sort = "title";
                way = "DESC";
                break;
            default:
                sort = "price";
        }
        Integer currentPage = (Integer) req.getSession().getAttribute("currentPage");
        String page = req.getParameter("tPage");
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        int rowsCount = productDao.getRowsCount(con, category, minPriceS, maxPriceS, search);
        int recordsPerPage = 8;
        int noOfPages = rowsCount / recordsPerPage;
        if (noOfPages * rowsCount % recordsPerPage > 0) {
            ++noOfPages;
        }
        if (currentPage == null) {
            currentPage = 1;
        }
        req.getSession().setAttribute("category", currentCategory);
        req.getSession().setAttribute("sort", order);
        req.getSession().setAttribute("priceMinS", minPriceS);
        req.getSession().setAttribute("priceMaxS", maxPriceS);
        req.getSession().setAttribute("currentPage", currentPage);
        req.getSession().setAttribute("noOfPages", noOfPages);
        req.getSession().setAttribute("search", search);
        List<Product> table = productDao.findAllWithSelect(con, recordsPerPage, (currentPage - 1) * recordsPerPage, category, minPriceS, maxPriceS, sort, way, search);
        return table;
    }
}
