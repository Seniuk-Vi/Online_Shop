package com.shop.command;

import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserCommand implements Command {
    final static Logger logger = Logger.getLogger(ShowUserCommand.class);
    final String message = "Trying to enter user page without signing in";

    public String execute(HttpServletRequest req, HttpServletResponse resp)  {
        String address = "user.jsp";
        User user = (User)req.getSession().getAttribute("currentUser");
        if (user == null) {
            logger.info(message);
            req.getSession().removeAttribute("currentUser");
            req.getSession().setAttribute("errorMessage", "You aren't logged in");
            return "login.jsp";
        }
            return address;

    }
}
