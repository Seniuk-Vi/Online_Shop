package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "login.jsp";

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        System.out.println("login ==> " + login);
        System.out.println("password ==> " + password);

        // get User
        User user;
        UserDao userDao = new UserDao();
        Connection con = DbHelper.getInstance().getConnection();

        try {
            user = userDao.findByLogin(con, login);
            if (user.getPassword().equals(password)) {
                System.out.println("user ==> " + user);
                req.getSession().setAttribute("currentUser", user);
                ArrayList<OrderItem> cart = new ArrayList<>();
                req.getSession().setAttribute("cart", cart);
                address = "homePage.jsp";

            }else{
                address="login.jsp";
            }
        } catch (SQLException ex) {
            System.out.println("Illegal login or password!");
            req.getSession().setAttribute("errorMessage", "Illegal login or password!");
            System.out.println(req.getParameter("errorMessage"));
            return "login.jsp";
        }

        ShowHomePageCommand s = new ShowHomePageCommand();
        address = s.execute(req, resp);
        return address;

    }
}
