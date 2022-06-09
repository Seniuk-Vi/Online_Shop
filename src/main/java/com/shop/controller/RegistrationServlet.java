package com.shop.controller;

import com.shop.Validation;
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
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(value = "/registration")
public class RegistrationServlet extends HttpServlet {
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
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");
        if (user!= null) {
            // if we somehow opened /login page while being already logged in, we just do redirect to catalog (/)
            resp.sendRedirect("homePage.jsp");
        }
        String login = req.getParameter("login").toLowerCase().trim();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email").toLowerCase().trim();
        String password = req.getParameter("password");
        String locale = req.getParameter("locale");

        // filling map with parameters which will be passed to the view
        Map<String, String> registrationAttributes = new HashMap<>();
        registrationAttributes.put("login", login);
        registrationAttributes.put("name", name);
        registrationAttributes.put("surname", surname);
        registrationAttributes.put("phoneNumber", phoneNumber);
        registrationAttributes.put("email", email);
        registrationAttributes.put("password",password);
        registrationAttributes.put("locale", locale);


        if(!Validation.isPasswordValid(password)) {
            registrationAttributes.put("errorMessage", "password don't valid");
            passErrorToView(req, resp, registrationAttributes);

        }
        if(!Validation.isEmailValid(email)) {
            registrationAttributes.put("errorMessage", "email don't valid");
            passErrorToView(req, resp, registrationAttributes);

        }
        if(name == null || name.isBlank()) {
            registrationAttributes.put("errorMessage", "name is required");
            passErrorToView(req, resp, registrationAttributes);

        }
        if(phoneNumber != null && !Validation.isPhoneValid(phoneNumber)) {
            registrationAttributes.put("errorMessage", "Password don't valid");
            passErrorToView(req, resp, registrationAttributes);

        }
        // all request parameters are valid
        Connection con = DbHelper.getInstance().getConnection();
        UserDao userDao = new UserDao();

        try {
            if(userDao.findByLogin(con,login) != null) {
                registrationAttributes.put("errorMessage", "Password don't valid");
                passErrorToView(req, resp, registrationAttributes);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // lets create user in DB and make him logged in
        boolean userAdded = false;
        try {
            User newUser = new User();
            user.setLogin(login);
            user.setName(name);
            user.setSurname(surname);
            user.setPhoneNumber(Integer.parseInt(phoneNumber));
            user.setEmail(email);
            user.setPassword(password);
            user.setLocale(locale);
            user.setRole(2);

            userDao.add(con,newUser);
        } catch (Exception e) {
            registrationAttributes.put("errorMessage", "can't add user");
            passErrorToView(req, resp, registrationAttributes);
        }

        // put user into session
        try {
            user = userDao.findByLogin(con,login);
        } catch (SQLException e) {
            System.out.println("user was added, but can't found him");
        }
            session = req.getSession(true);
            session.setAttribute("currentUser", user);
            resp.sendRedirect("homePage.jsp");


    }

    public void destroy() {
    }
    private void passErrorToView(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) throws ServletException, IOException {
        for(Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.setAttribute(entry.getKey(), entry.getValue());
        request.getRequestDispatcher("registration.jsp").forward(request, response);
    }
}