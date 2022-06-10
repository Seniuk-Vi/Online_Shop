package com.shop.command;

import com.mysql.cj.Session;
import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddProductCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "homePage.jsp";

        ProductDao productDao = new ProductDao();
        Product product = new Product();

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int price = Integer.parseInt(req.getParameter("price"));
        String imageUrl = req.getParameter("image_url");
        int model_year = Integer.parseInt(req.getParameter("model_year"));
        int inStock = Integer.parseInt(req.getParameter("in_stock"));
        String categotyId = req.getParameter("category");
        String condition = req.getParameter("state");

        // todo check if param is valid

        //

        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setModelYear(model_year);
        product.setInStock(inStock);
        product.setCategory(categotyId);
        product.setCondition(condition);

        Connection con;

        con = DbHelper.getInstance().getConnection();

        try {
            productDao.add(con, product);

        } catch (DbException ex) {
            System.out.println("Can't add product ==> " + product);
            req.getSession().setAttribute("errorMessage","Can't add product!!" );
            address="addProduct.jsp";
        }

        return address;

    }


}
