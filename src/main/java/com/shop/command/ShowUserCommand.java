//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command;

import com.shop.models.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserCommand implements Command {
    public ShowUserCommand() {
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "user.jsp";
        User user = (User)req.getSession().getAttribute("currentUser");
        if (user == null) {
            req.getSession().removeAttribute("currentUser");
            req.getSession().setAttribute("errorMessage", "You aren't logged in");
            return "login.jsp";
        } else {
            address = "user.jsp";
            return address;
        }
    }
}
