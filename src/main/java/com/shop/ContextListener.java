package com.shop;

import com.shop.db.DbHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getRealPath("WEB-INF/log4j.log");
        System.setProperty("log4j.path", path);

        try {
            DbHelper.getInstance().getConnection().close();
        } catch (SQLException ex) {
            throw new IllegalStateException("Cannot obtain connection from DB");
        }

        // obtain file name with locales descriptions
        ServletContext context = sce.getServletContext();
        String localesFileName = context.getInitParameter("locales");

        // obtain reale path on server
        String localesFileRealPath = context.getRealPath(localesFileName);

        // locad descriptions
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
