package com.shop.controller;

import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    //
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");
        if (user!= null) {
            // if we somehow opened /login page while being already logged in, we just do redirect to catalog (/)
            resp.sendRedirect("homePage.jsp");
        } else {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (!Validation.isPasswordValid(password)) {
            req.getSession().setAttribute("errorMessage", "Password don't valid");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }


        Connection con;
        con = DbHelper.getInstance().getConnection();
        UserDao userDao = new UserDao();
        User user = null;
        try {
            user = userDao.findByLogin(con, login);
        } catch (SQLException e) {
            req.getSession().setAttribute("errorMessage", "Email or password is not valid!!!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        if (user != null) {
            try {
                if(!Validation.isPasswordCorrect(password, user.getPassword())) {
                    req.getSession().setAttribute("errorMessage", "Login or password is not valid!!!");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception e) {
                req.getSession().setAttribute("errorMessage", "Login or password is not valid!!!");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("currentUser", user);

        resp.sendRedirect("homePage.jsp");


    }

    public void destroy() {
    }
}