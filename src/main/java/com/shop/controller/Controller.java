//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.controller;

import com.shop.command.Command;
import com.shop.command.CommandContainer;
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
    public Controller() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = "error.jsp";
        String commandName = req.getParameter("command");
        System.out.println("commandName ==> " + commandName);
        Command command = null;
        command = CommandContainer.getCommand(commandName);
        System.out.println("command ==> " + command);
        if (commandName.equals("addProduct")) {
            req.getSession().setAttribute("path", this.getServletContext().getRealPath("images/"));
        }

        try {
            address = command.execute(req, resp);
        } catch (Exception var7) {
            req.getSession().setAttribute("errorMessage", var7.getMessage());
        }

        System.out.println("address ==> " + address);
        resp.sendRedirect(address);
    }
}
