package com.shop.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbHelper {
    DataSource ds;
    private static DbHelper INSTANCE;

    private DbHelper() {
    }

    public static synchronized DbHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DbHelper();
        }

        return INSTANCE;
    }

    public Connection getConnection() {
        Connection c = null;

        try {
            Context ctx = new InitialContext();
            Context initCtx = (Context)ctx.lookup("java:/comp/env");
            DataSource ds = (DataSource)initCtx.lookup("jdbc/shop");
            c = ds.getConnection();
        } catch (NamingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    public void close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
