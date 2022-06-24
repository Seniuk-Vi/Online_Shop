package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "login.jsp";

        req.getSession().removeAttribute("errorMessage");
        // move to homaPage if already loggined
        if (req.getSession().getAttribute("currentUser") != null) {
            return "homePage.jsp";
        }

        // get data
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        // todo check data
        System.out.println("login ==> " + login);
        System.out.println("password ==> " + password);

        // get User
        User user;
        UserDao userDao = new UserDao();
        Connection con = DbHelper.getInstance().getConnection();

        try {
            user = userDao.findByLogin(con, login);
            if (user.getPassword().equals(password)) {
                if(user.getRole()==2){
                    req.getSession().setAttribute("login", login);
                    System.out.println(user.getLogin()+" is blocked!");
                    req.getSession().setAttribute("errorMessage", user.getLogin()+" is blocked!");
                    return address;
                }
                System.out.println("user ==> " + user);
                req.getSession().setAttribute("currentUser", user);
                Map<Product, OrderItem> cart = new HashMap<>();
                if (req.getSession().getAttribute("cart") == null) {
                    req.getSession().setAttribute("cart", cart);
                }

                address = "homePage.jsp";

            } else {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            req.getSession().setAttribute("login", login);
            System.out.println("Illegal login or password!");
            req.getSession().setAttribute("errorMessage", "Illegal login or password!");
            return address;
        }finally {
            con.close();
        }


        return address;

    }
}
