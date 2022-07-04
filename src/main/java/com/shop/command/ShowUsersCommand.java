package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUsersCommand implements Command {
      public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "showUsers.jsp";
        UserDao userDao = new UserDao();
        Connection con = DbHelper.getInstance().getConnection();
        List<User> table;

        try {
            table = userDao.findAll(con);
        } catch (SQLException ex) {
            System.out.println("Can't show all users");
            req.getSession().setAttribute("errorMessage", "Can't show all users");
            return "error.jsp";
        }

        System.out.println("Users ==> " + table);
        req.getSession().setAttribute("users", table);
        return address;
    }
}
