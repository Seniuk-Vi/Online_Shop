package com.shop.command.admin;

import com.shop.Validation;
import com.shop.command.Command;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EditProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "editProduct.jsp";


        // get user data
        String productId = req.getParameter("product_id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String model_year = req.getParameter("model_year");
        String in_stock = req.getParameter("in_stock");
        String category = req.getParameter("category");
        String state = req.getParameter("state");


        // todo validate product data


        // filling user
        Connection con = DbHelper.getInstance().getConnection();
        ProductDao productDao = new ProductDao();
        Product product = new Product();
        try {
            product = productDao.findById(con, Integer.parseInt(productId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(Double.parseDouble(price));
        product.setModelYear(Integer.parseInt(model_year));
        product.setInStock(Integer.parseInt(in_stock));
        product.setCategory(category);
        product.setCondition(state);

        // all request parameters are valid

        try {
            productDao.update(con, product, product);

        } catch (SQLException e) {
            System.out.println("Can't update product ==> " + product);
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        req.getSession().setAttribute("info", "product was changed");
        req.getSession().setAttribute("product", product);


        return address;

    }
}
