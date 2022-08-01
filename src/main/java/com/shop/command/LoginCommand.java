package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {

    final static Logger logger = Logger.getLogger(LoginCommand.class);
    private final String error = "Can't login";
    private final String passwordInfo = "Password doesn't correct";
    private final String loginInfo = "User doesn't exists with this login";
    private final String blocked = "User is blocked";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "login.jsp";
        if (req.getSession().getAttribute("currentUser") != null) {
            return "controller?command=showHomePage";
        }
        // getting params
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        Connection con = DbHelper.getInstance().getConnection();
        req.getSession().setAttribute("login", login);
        try {
            User user = userDao.findByLogin(con, login);
            if (user == null) {
                req.getSession().setAttribute("errorMessage", loginInfo);
                return address;
            }
            if (!user.getPassword().equals(password)) {
                req.getSession().setAttribute("errorMessage", passwordInfo);
                return address;
            }
            if (user.getRole() == 2) {
                req.getSession().setAttribute("errorMessage", blocked);
                return address;
            }
            req.getSession().setAttribute("currentUser", user);
            req.getSession().setAttribute("userLocale", user.getLocale());
            req.getSession().setAttribute("userLocale", user.getLocale());
            Map<Product, OrderItem> cart = new HashMap();
            if (req.getSession().getAttribute("cart") == null) {
                req.getSession().setAttribute("cart", cart);
            }
            return "homePage.jsp";
        } catch (DbException ex) {
            req.getSession().setAttribute("errorMessage", error);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Can't close connection", e);
            }
        }
        return address;

    }
}
