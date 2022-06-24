package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class UnblockUserCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "error.jsp";

        UserDao userDao= new UserDao();
        User user;
        int userId = Integer.parseInt(req.getParameter("user_id"));

        Connection con;
        con = DbHelper.getInstance().getConnection();
        try {
            user = userDao.findById(con,userId);
            // unblock
            user.setRole(1);
            userDao.update(con,user,user);
            address = "controller?command=showUsers";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;


    }
}