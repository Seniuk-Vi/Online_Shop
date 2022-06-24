package com.shop.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbHelper {
        DataSource ds;

    private static DbHelper INSTANCE;

    private DbHelper() {

    }
    public static synchronized DbHelper getInstance() {
       if(INSTANCE == null){
           INSTANCE = new DbHelper();

       }
       return INSTANCE;
    }
    public Connection getConnection(){

        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            Context initCtx  = (Context) ctx.lookup("java:/comp/env");
            DataSource ds = (DataSource) initCtx.lookup("jdbc/shop");
            c = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

   public void close(Connection con){
       try {
           con.close();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

   }



}
