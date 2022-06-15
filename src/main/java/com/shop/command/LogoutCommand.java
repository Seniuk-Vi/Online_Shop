package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.List;

public class LogoutCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "homePage.jsp";

        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null) {
           req.getSession().removeAttribute("currentUser");
        }
        ShowHomePageCommand s = new ShowHomePageCommand();
        address = s.execute(req, resp);
        return address;
    }
}
