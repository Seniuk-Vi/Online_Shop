package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showHomePage";


        // get user data
        String productId = req.getParameter("product_id");


        // todo validate product data


        // filling user
        Connection con = DbHelper.getInstance().getConnection();
        ProductDao productDao = new ProductDao();

        try {
             productDao.deleteById(con, Integer.parseInt(productId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("info", "product was deleted");



        return address;

    }
}
