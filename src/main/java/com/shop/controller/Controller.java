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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("MyServlet's doGet() called");
        logger.error("MyServlet's doGet() called");
        logger.fatal("MyServlet's doGet() called");
        logger.info("MyServlet's doGet() called");
        logger.info("MyServlet's doGet() called");
        logger.info("MyServlet's doGet() called");
        String address = "error.jsp";
        String commandName = req.getParameter("command");
        System.out.println("commandName ==> " + commandName);
        Command command;
        command = CommandContainer.getCommand(commandName);
        System.out.println("command ==> " + command);

        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            req.getSession().setAttribute("errorMessage", ex.getMessage());
        }
        System.out.println("address ==> " + address);
        resp.sendRedirect(address);
    }
}
