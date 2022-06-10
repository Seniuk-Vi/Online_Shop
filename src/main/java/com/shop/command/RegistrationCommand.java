package com.shop.command;

import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
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

public class RegistrationCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "error.jsp";

        //check if user is not signed in
        HttpSession session = req.getSession();
        User user = null;
        user = (User) session.getAttribute("currentUser");
        System.out.println("session user ==> " + user);
        if (user != null) {
            // if we somehow opened /login page while being already logged in, we just do redirect to catalog (/)
            return "homePage.jsp";
        }
        // get user data
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String locale = req.getParameter("locale");

        // filling map with parameters which will be passed to the view
        Map<String, String> registrationAttributes = new HashMap<>();
        registrationAttributes.put("login", login);
        registrationAttributes.put("name", name);
        registrationAttributes.put("surname", surname);
        registrationAttributes.put("phoneNumber", phoneNumber);
        registrationAttributes.put("email", email);

        registrationAttributes.put("locale", locale);

        System.out.println(registrationAttributes);

        // validate
        if (!Validation.isPasswordValid(password)) {
            registrationAttributes.put("errorMessage", "password don't valid");
            System.out.println("password don't valid");
            return passErrorToView(req, resp, registrationAttributes);
        }
        if (!Validation.isEmailValid(email)) {
            registrationAttributes.put("errorMessage", "email don't valid");
            System.out.println("email don't valid");
            return passErrorToView(req, resp, registrationAttributes);

        }
        if (name == null || name.isBlank()) {
            registrationAttributes.put("errorMessage", "name is required");
            System.out.println("name is required");
            return passErrorToView(req, resp, registrationAttributes);
        }
        if (phoneNumber != null && !Validation.isPhoneValid(phoneNumber)) {
            registrationAttributes.put("errorMessage", "Password don't valid");
            System.out.println("Password don't valid");
            return passErrorToView(req, resp, registrationAttributes);
        }

        // all request parameters are valid
        Connection con = DbHelper.getInstance().getConnection();
        UserDao userDao = new UserDao();
        try {
            if (userDao.findByLogin(con, login) != null) {
                registrationAttributes.put("errorMessage", "User already exist");
                return passErrorToView(req, resp, registrationAttributes);

            }
        } catch (SQLException e) {

        }

        // lets create user in DB and make him logged in
        try {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setPhoneNumber(Integer.parseInt(phoneNumber));
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setLocale(locale);
            newUser.setRole(2);
            userDao.add(con, newUser);
            System.out.println("user was added");
        } catch (DbException e) {
            registrationAttributes.put("errorMessage", "can't add user");
            System.out.println("can't add user");
            return passErrorToView(req, resp, registrationAttributes);
        }

        // put user into session
        try {
            user = userDao.findByLogin(con, login);
        } catch (SQLException e) {
            System.out.println("user was added, but can't found him");
        }
        con.close();
        session = req.getSession(true);
        session.setAttribute("currentUser", user);
        return "homePage.jsp";


    }

    private String passErrorToView(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) throws ServletException, IOException {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        return "registration.jsp";
    }
}