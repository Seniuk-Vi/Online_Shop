package com.shop.controller;

import com.shop.command.Command;
import com.shop.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //toDo logger

        // address
        String address = "error.jsp";

        // get command name
        String commandName = req.getParameter("command");
        System.out.println(commandName);
        // get command
        Command command = CommandContainer.getCommand(commandName);

        // do action
        try {
            address = command.execute(req, resp);
        } catch (Exception e) {
            // go to error page
            req.setAttribute("errorMessage",e.getMessage());
        }

        // go to view
        req.getRequestDispatcher(address).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // address
        String address = "error.jsp";

        // get command name
        String commandName = req.getParameter("command");
        System.out.println("commandName ==> "+commandName);
        // get command
        Command command = null;
        command = CommandContainer.getCommand(commandName);
        System.out.println("command ==> "+command);
        // do action
        try {
            address = command.execute(req, resp);
        } catch (Exception e) {
            // go to error page
            req.getSession().setAttribute("errorMessage",e.getMessage());
        }
        System.out.println("address ==> "+address);

        // go to view
        resp.sendRedirect(address);
    }


}
