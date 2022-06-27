package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class EditProductShowCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "editProduct.jsp";

        String productId = req.getParameter("product_id");
        // getting product
        Connection con = DbHelper.getInstance().getConnection();
        ProductDao productDao = new ProductDao() ;
        Product product  = new Product();
        try {
            product = productDao.findById(con, Integer.parseInt(productId));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.getSession().setAttribute("product",product);



        return address;

    }
}
