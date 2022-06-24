package com.shop.command.admin;

import com.shop.Validation;
import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UpdateUserDataCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)  {
        String address = "user.jsp";

        //check if user is not signed in
        HttpSession session = req.getSession();
        User user;
        user = (User) session.getAttribute("currentUser");
        System.out.println("session user ==> " + user);


        // get user data
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String locale = req.getParameter("locale");





        // validate

        if (!Validation.isEmailValid(email)) {
            System.out.println("email don't valid");
        }
        if (name == null || name.isBlank()) {
            System.out.println("name is required");
        }

        // filling user
        user.setLogin(login);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setLocale(locale);

        // all request parameters are valid
        Connection con = DbHelper.getInstance().getConnection();
        UserDao userDao = new UserDao();
        try {
            userDao.update(con,user,user);

        } catch (SQLException e) {
            System.out.println("Can't update user ==> "+user);
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        session = req.getSession();
        session.setAttribute("currentUser", user);



        return address;
    }
}
