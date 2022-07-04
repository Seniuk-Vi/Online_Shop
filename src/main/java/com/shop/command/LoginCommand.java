//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command;

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
    public LoginCommand() {
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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

            String var10;
            try {
                User user = userDao.findByLogin(con, login);
                if (!user.getPassword().equals(password)) {
                    throw new SQLException();
                }

                if (user.getRole() == 2) {
                    req.getSession().setAttribute("login", login);
                    System.out.println(user.getLogin() + " is blocked!");
                    req.getSession().setAttribute("errorMessage", user.getLogin() + " is blocked!");
                    String var16 = address;
                    return var16;
                }

                System.out.println("user ==> " + user);
                req.getSession().setAttribute("currentUser", user);
                Map<Product, OrderItem> cart = new HashMap();
                if (req.getSession().getAttribute("cart") == null) {
                    req.getSession().setAttribute("cart", cart);
                }

                address = "homePage.jsp";
                return address;
            } catch (SQLException var14) {
                req.getSession().setAttribute("login", login);
                System.out.println("Illegal login or password!");
                req.getSession().setAttribute("errorMessage", "Illegal login or password!");
                var10 = address;
            } finally {
                con.close();
            }

            return var10;
        }
    }
}
