package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {

    public String execute(HttpServletRequest req, HttpServletResponse resp){
        String address = "login.jsp";
        req.getSession().removeAttribute("errorMessage");
        if (req.getSession().getAttribute("currentUser") != null) {
            return "homePage.jsp";
        } else {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            System.out.println("login ==> " + login);
            System.out.println("password ==> " + password);
            UserDao userDao = new UserDao();
            Connection con = DbHelper.getInstance().getConnection();
            try {
                User user = userDao.findByLogin(con, login);
                if (!user.getPassword().equals(password)) {
                    throw new DbException("");
                }
                if (user.getRole() == 2) {
                    req.getSession().setAttribute("login", login);
                    System.out.println(user.getLogin() + " is blocked!");
                    req.getSession().setAttribute("errorMessage", user.getLogin() + " is blocked!");
                    return address;
                }
                System.out.println("user ==> " + user);
                req.getSession().setAttribute("currentUser", user);
                req.getSession().setAttribute("userLocale", user.getLocale());
                req.getSession().setAttribute("userLocale", user.getLocale());
                Map<Product, OrderItem> cart = new HashMap();
                if (req.getSession().getAttribute("cart") == null) {
                    req.getSession().setAttribute("cart", cart);
                }
                return "homePage.jsp";
            } catch (DbException ex) {
                req.getSession().setAttribute("login", login);
                System.out.println("Illegal login or password!");
                req.getSession().setAttribute("errorMessage", "Illegal login or password!");
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    //todo log
                }
            }

            return address;
        }
    }
}
