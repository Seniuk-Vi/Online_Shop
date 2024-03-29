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

public class UpdateUserDataCommand implements Command {
    final static Logger logger = Logger.getLogger(UpdateUserDataCommand.class);
    final String error = "this login or email isn't available";
    final String info = "No user yet";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "user.jsp";
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("currentUser");
        // getting user input params
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String locale = req.getParameter("locale");
        Map<String, String> registrationAttributes = new HashMap<>();
        // validate user input params
        if (!Validation.isLoginValid(login)) {
            registrationAttributes.put("loginMessage", "2-20 length, only characters and digits");
            return passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isNameValid(name)) {
            registrationAttributes.put("nameMessage", "2-10 length, only characters");
            return passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isSurnameValid(surname)) {
            registrationAttributes.put("surnameMessage", "2-10 length, only characters");
            return passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isEmailValid(email)) {
            registrationAttributes.put("emailMessage", "email don't valid");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setLocale(locale);
        Connection con = null;
        try {
            con = DbHelper.getInstance().getConnection();
            UserDao userDao = new UserDao();
            userDao.update(con, user, user);
        } catch (DbException ex) {
            registrationAttributes.put("loginMessage", error);
            logger.error(error,ex);
            return passAttributesToSession(req, resp, registrationAttributes);
        } finally {
            DbHelper.getInstance().close(con);
        }
        req.getSession().setAttribute("currentUser", user);
        req.getSession().setAttribute("userLocale", user.getLocale());
        return address;
    }


    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) {
        Iterator<Map.Entry<String, String>> iterator = viewAttributes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
        return "user.jsp";
    }
}
