package com.shop.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    String errorPage="error.jsp";
    String error404="404";
    String error505="505";
    String execute(HttpServletRequest req, HttpServletResponse resp);
}
