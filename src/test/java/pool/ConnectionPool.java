package pool;

import com.shop.db.ConException;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    final static Logger logger = Logger.getLogger(ConnectionPool.class);

    private static ConnectionPool INSTANCE;
    private static Context ctx;
    private static Context  initCtx;
    private static DataSource ds;


    private ConnectionPool() {
    }

    public static synchronized ConnectionPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool();
            try {
                ctx = new InitialContext();
                initCtx = (Context)ctx.lookup("java:/comp/env");
                ds = (DataSource)initCtx.lookup("jdbc/shop");
            } catch (NamingException e) {
                logger.fatal("Can't initialize INSTANCE ==> "+e.getMessage());
                throw new ConException("Can't initialize INSTANCE ==> ",e);
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
            throw new ConException("Can't initialize INSTANCE ==> ",ex);
        }
        return c;
    }

    public void close(Connection con) {
        if(con!=null){
            try {
                con.close();
            } catch (SQLException ex) {
                logger.info("Can't close Connection",ex);}
        }
    }
}
