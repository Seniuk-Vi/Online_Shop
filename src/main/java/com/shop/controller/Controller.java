package com.shop.controller;

import com.shop.command.Command;
import com.shop.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet(value = "/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //toDo logger
        doPost(req,resp);
//        // address
//        String address = "error.jsp";
//
//        // get command name
//        String commandName = req.getParameter("command");
//        System.out.println(commandName);
//        // get command
//        Command command = CommandContainer.getCommand(commandName);
//
//        // do action
//        try {
//            address = command.execute(req, resp);
//        } catch (Exception e) {
//            // go to error page
//            req.setAttribute("errorMessage",e.getMessage());
//        }
//
//        // go to view
//        req.getRequestDispatcher(address).forward(req, resp);

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

        //
//        if(commandName.equals("addProduct")){
//            Part filePart = req.getPart("image_url");
//            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//            InputStream fileContent = filePart.getInputStream();
//
//            String stream = new BufferedReader(
//                    new InputStreamReader(fileContent, StandardCharsets.UTF_8))
//                    .lines()
//                    .collect(Collectors.joining("\n"));
//            String imageAddress = System.getProperty("user.home");
//            req.getSession().setAttribute("imageStream",stream);
//            req.getSession().setAttribute("imageAddress",imageAddress);
//            req.getSession().setAttribute("imageName",fileName);
//        }
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
