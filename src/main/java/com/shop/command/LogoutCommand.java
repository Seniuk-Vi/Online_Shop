//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command;

import com.shop.models.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    public LogoutCommand() {
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "homePage.jsp";
        User user = (User)req.getSession().getAttribute("currentUser");
        if (user != null) {
            req.getSession().invalidate();
        }

        return address;
    }
}
