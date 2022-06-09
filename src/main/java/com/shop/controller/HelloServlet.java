package com.shop.controller;

import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/hello-servlet")
public class HelloServlet extends HttpServlet {


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        long id;
        id = Long.parseLong(request.getParameter("id"));
        Connection con;
        con = DbHelper.getInstance().getConnection();
        UserDao userDao = new UserDao();
        User user;
        try {
            user = userDao.findById(con, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + user.getId() + "</h1>");
        out.println("<h1>" + user.getEmail() + "</h1>");
        out.println("<h1>" + user.getPhoneNumber() + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}