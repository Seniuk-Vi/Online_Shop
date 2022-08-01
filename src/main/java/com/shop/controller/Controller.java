package com.shop.controller;

import com.shop.command.Command;
import com.shop.command.CommandContainer;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet({"/controller"})
public class Controller extends HttpServlet {

    final static Logger logger = Logger.getLogger(Controller.class);
    final static String error = "Error from jsp";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "error.jsp";
        String commandName = req.getParameter("command");
        Command command;
        command = CommandContainer.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            logger.error(error,ex);
            req.getSession().setAttribute("errorMessage", ex.getMessage());
        }
        resp.sendRedirect(address);
    }
}
