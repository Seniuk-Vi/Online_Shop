package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnblockUserCommand implements Command {
    final static Logger logger = Logger.getLogger(UnblockUserCommand.class);
    private final String error = "Can't unblock user";
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "error.jsp";
        UserDao userDao = new UserDao();
        int userId = Integer.parseInt(req.getParameter("user_id"));
        Connection con = DbHelper.getInstance().getConnection();
        try {
            User user = userDao.findById(con, userId);
            user.setRole(1);
            userDao.update(con, user, user);
            address = "controller?command=showUsers";
        } catch (DbException ex) {
            logger.error(error,ex);
            req.getSession().setAttribute("errorMessage",error);
            return address;
        }

        return address;
    }
}
