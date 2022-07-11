package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class ShowProductCommand implements Command {
    String address = "product.jsp";
    String errorAddress = "homePage.jsp";
    String errorMessage = "Product doesn't exist";
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {


        ProductDao productDao = new ProductDao();
        Product product;
        String productId;
        Connection con;
        productId = req.getParameter("product_id");
        if (productId.isEmpty()) {
            return returnError(req, errorMessage, errorAddress);
        }
        con = DbHelper.getInstance().getConnection();
        try {
            product = productDao.findById(con, Integer.parseInt(productId));
            req.getSession().setAttribute("showProduct", product);
        } catch (SQLException e) {
            return returnError(req, errorMessage, errorAddress);
        }
        return address;
    }

    public String returnError(HttpServletRequest req, String message, String errorPage) {
        req.getSession().setAttribute("errorMessage", message);
        return errorPage;
    }
}
