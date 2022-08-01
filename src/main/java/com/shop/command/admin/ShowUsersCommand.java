package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUsersCommand implements Command {
    final static Logger logger = Logger.getLogger(ShowUsersCommand.class);
    final String error = "Can't show all users";
    final String info = "No user yet";

      public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "showUsers.jsp";
        UserDao userDao = new UserDao();
        Connection con = DbHelper.getInstance().getConnection();
        List<User> table;
        try {
            table = userDao.findAll(con);
        } catch (DbException ex) {
            req.getSession().setAttribute("errorMessage", error);
            return "error.jsp";
        }
        if(table.isEmpty()){
            logger.error(info);
            req.getSession().setAttribute("errorMessage", info);
        }else {
            req.getSession().setAttribute("users", table);
        }
        return address;
    }
}
