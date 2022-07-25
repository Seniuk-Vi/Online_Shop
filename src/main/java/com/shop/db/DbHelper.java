package com.shop.db;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbHelper {
    final static Logger logger = Logger.getLogger(DbHelper.class);

    private static DbHelper INSTANCE;
    private static Context ctx;
    private static Context  initCtx;
    private static   DataSource ds;


    private DbHelper() {
    }

    public static synchronized DbHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbHelper();
            try {
                ctx = new InitialContext();
                initCtx = (Context)ctx.lookup("java:/comp/env");
                ds = (DataSource)initCtx.lookup("jdbc/shop");
            } catch (NamingException e) {
                logger.fatal("Can't initialize INSTANCE ==> "+e.getMessage());
                throw new RuntimeException("Can't initialize INSTANCE ==> ",e);
            }
        }

        return INSTANCE;
    }

    public Connection getConnection() {
        Connection c ;
        try {
            c = ds.getConnection();
        } catch (SQLException ex) {
            logger.fatal("Can't getConnection",ex);
            throw new RuntimeException("Can't initialize INSTANCE ==> ",ex);
        }
        return c;
    }

    public void close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            logger.info("Can't close Connection",ex);}
    }
}
