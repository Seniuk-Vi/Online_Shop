package com.shop;

import com.shop.db.DbHelper;
import com.shop.db.dao.OrderDao;
import org.apache.log4j.Logger;


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
    final static Logger logger = Logger.getLogger(ContextListener.class);
    final static String error = "Can't load locales";

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("path",sce.getServletContext().getRealPath("images/"));
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
            logger.fatal(error,e);
            throw new RuntimeException(error,e);
        }
        // save descriptions to servlet context
        context.setAttribute("locales", locales);
     //   locales.list(System.out);

    }

}
