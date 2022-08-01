
package com.shop.command;

import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements Command {
    final static Logger logger = Logger.getLogger(RegistrationCommand.class);
    private final String error = "Can't sign up";
    private final String regPage = "registration.jsp";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showHomePage";
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");
        if (user != null) {
            return address;
        }
        // get params
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String locale = req.getParameter("locale");
        // return params to user
        Map<String, String> registrationAttributes = new HashMap<>();
        registrationAttributes.put("login", login);
        registrationAttributes.put("name", name);
        registrationAttributes.put("surname", surname);
        registrationAttributes.put("password", password);
        registrationAttributes.put("phoneNumber", phoneNumber);
        registrationAttributes.put("email", email);
        registrationAttributes.put("locale", locale);
        // validate params
        if (!Validation.isLoginValid(login)) {
            registrationAttributes.put("loginMessage", "2-20 length, only characters and digits");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isNameValid(name)) {
            registrationAttributes.put("nameMessage", "2-10 length, only characters");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isSurnameValid(surname)) {
            registrationAttributes.put("surnameMessage", "2-10 length, only characters");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isPhoneValid(phoneNumber)) {
            registrationAttributes.put("phoneNumberMessage", "8-10 length, only digits");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isEmailValid(email)) {
            registrationAttributes.put("emailMessage", "email don't valid");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isPasswordCorrect(password)) {
            registrationAttributes.put("passwordMessage", "8-25 length, at least one letter and one number");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!locale.equals("uk") && !locale.equals("en")) {
            registrationAttributes.put("localeMessage", "locale 'uk' or 'en'");
            return passAttributesToSession(req, resp, registrationAttributes);
        }

        Connection con = DbHelper.getInstance().getConnection();
        UserDao userDao = new UserDao();
        try {
            if (userDao.findByLogin(con, login) != null) {
                registrationAttributes.put("loginMessage", "login isn't available");
                return passAttributesToSession(req, resp, registrationAttributes);
            }
        } catch (DbException ex) {
            req.getSession().setAttribute("fatalError",error505);
            return errorPage;
        }

        try {
            if (userDao.findByEmail(con, email) != null) {
                registrationAttributes.put("emailMessage", "email isn't available");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            }
        } catch (DbException ex) {
            req.getSession().setAttribute("fatalError",error505);
            return errorPage;
        }
        logger.info("Trying to add user, Login ==> "+login+", Email ==> "+email);
        try {
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setPhoneNumber(Integer.parseInt(phoneNumber));
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setLocale(locale);
            newUser.setRole(0);
            userDao.add(con, newUser);
            logger.info("User was added ==> "+newUser);
        } catch (DbException ex) {
            registrationAttributes.put("LoginError", error);
            return passAttributesToSession(req, resp, registrationAttributes);
        }

        try {
            user = userDao.findByLogin(con, login);
        } catch (DbException ex) {
            req.getSession().setAttribute("fatalError", error505);
            req.getSession().setAttribute("fatalErrorMessage", "Can't login");
            return errorPage;
        }

        try {
            con.close();
        } catch (SQLException ex) {
            logger.error("Can't close con",ex);
        }
        session = req.getSession();
        session.setAttribute("currentUser", user);
        session.setAttribute("userLocale", user.getLocale());
        return address;
    }

    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
        return regPage;
    }
}
