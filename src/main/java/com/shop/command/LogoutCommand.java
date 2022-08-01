package com.shop.command;

import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    final static Logger logger = Logger.getLogger(LogoutCommand.class);


    public String execute(HttpServletRequest req, HttpServletResponse resp)  {
        String address = "controller?command=showHomePage";
        User user = (User)req.getSession().getAttribute("currentUser");
        if (user != null) {
            logger.info("User ==> "+user.getLogin()+" - was logout");
            req.getSession().invalidate();
        }
        return address;
    }
}
