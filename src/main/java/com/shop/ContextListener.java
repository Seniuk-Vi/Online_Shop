package com.shop;

import com.shop.db.DbHelper;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        // initialization log4j
        ServletContext context = sce.getServletContext();
        context.setAttribute("path",sce.getServletContext().getRealPath("images/"));
      //  req.getSession().setAttribute("path", sce.getServletContext().getRealPath("images/"));

        try {
            DbHelper.getInstance().getConnection().close();
        } catch (SQLException ex) {
            throw new IllegalStateException("Cannot obtain connection from DB");
        }

        // obtain file name with locales descriptions
        String localesFileName = context.getInitParameter("locales");

        // obtain real path on server
        String localesFileRealPath = context.getRealPath(localesFileName);

        // locales descriptions
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // save descriptions to servlet context
        context.setAttribute("locales", locales);
        locales.list(System.out);

    }

}
