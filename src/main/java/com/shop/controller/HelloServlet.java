package com.shop.controller;

import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


public class HelloServlet extends HttpServlet {


    public void init() {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String address = "homePage.jsp";

        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        List<Product> table = null;
        try {
            table = productDao.findAll(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(table);
        req.setAttribute("products",table);
        req.getRequestDispatcher(address).forward(req, resp);
    }

    public void destroy() {
    }
}