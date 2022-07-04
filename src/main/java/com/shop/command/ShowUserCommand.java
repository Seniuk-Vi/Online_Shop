package com.shop.command;

import com.shop.models.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserCommand implements Command {


    public String execute(HttpServletRequest req, HttpServletResponse resp)  {
        String address = "user.jsp";
        User user = (User)req.getSession().getAttribute("currentUser");
        if (user == null) {
            req.getSession().removeAttribute("currentUser");
            req.getSession().setAttribute("errorMessage", "You aren't logged in");
            return "login.jsp";
        } else {
            return address;
        }
    }
}
