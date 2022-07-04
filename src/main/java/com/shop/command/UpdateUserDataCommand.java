package com.shop.command;

import com.shop.Validation;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateUserDataCommand implements Command {
    public UpdateUserDataCommand() {
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "user.jsp";
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");
        System.out.println("session user ==> " + user);
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String locale = req.getParameter("locale");
        Map<String, String> registrationAttributes = new HashMap();
        if (!Validation.isLoginValid(login)) {
            registrationAttributes.put("loginMessage", "2-20 length, only characters and digits");
            System.out.println("password don't valid");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isNameValid(name)) {
            registrationAttributes.put("nameMessage", "2-10 length, only characters");
            System.out.println("name is required");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isSurnameValid(surname)) {
            registrationAttributes.put("surnameMessage", "2-10 length, only characters");
            System.out.println("surname must contain only characters (2-10)");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isEmailValid(email)) {
            registrationAttributes.put("emailMessage", "email don't valid");
            System.out.println("email don't valid");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        }
        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setLocale(locale);
        try (Connection con = DbHelper.getInstance().getConnection()) {
            UserDao userDao = new UserDao();
            userDao.update(con, user, user);
        } catch (SQLException ex) {
            registrationAttributes.put("loginMessage", "this login isn't available");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        req.getSession().setAttribute("currentUser", user);
        return address;
    }


    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) {
        Iterator iterator = viewAttributes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry) iterator.next();
            request.getSession().setAttribute((String) entry.getKey(), entry.getValue());
        }
        return "user.jsp";
    }
}
