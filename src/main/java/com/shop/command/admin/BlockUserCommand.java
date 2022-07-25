//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockUserCommand implements Command {

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "error.jsp";
        UserDao userDao = new UserDao();
        int userId = Integer.parseInt(req.getParameter("user_id"));
        Connection con = DbHelper.getInstance().getConnection();

        try {
            User user = userDao.findById(con, userId);
            user.setRole(2);
            userDao.update(con, user, user);
            address = "controller?command=showUsers";
        } catch (DbException ex) {
            ex.printStackTrace();
        }

        return address;
    }
}
