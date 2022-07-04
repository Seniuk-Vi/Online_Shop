package com.shop;

import com.shop.db.DbHelper;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    public ContextListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getRealPath("WEB-INF/log4j.log");
        System.setProperty("log4j.path", path);

        try {
            DbHelper.getInstance().getConnection().close();
        } catch (SQLException ex) {
            throw new IllegalStateException("Cannot obtain connection from DB");
        }
    }
}
