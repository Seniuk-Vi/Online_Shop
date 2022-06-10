package com.shop;

import com.shop.db.DbHelper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // configure logging subsystem
        String path = sce.getServletContext().getRealPath("WEB-INF/log4j.log");
        System.setProperty("log4j.path", path);

        // check if database connection exists
        try {
            DbHelper.getInstance().getConnection().close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot obtain connection from DB");
        }
    }
}
