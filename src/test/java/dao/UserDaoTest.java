package dao;

import MySqlScriptRunner.ScriptRunner;
import com.shop.db.dao.UserDao;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import pool.ConnectionPool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class UserDaoTest {
    final static Logger logger = Logger.getLogger(UserDaoTest.class);
    private static String initMessage = "test started";
    private static String tearDownMessage = "test ended";
    private static String testDBCreated = "test database created";
    private static String testDBError= "test database wasn't created";
    private static String connectionMessage = "connection received";
    private static String connectionErrorMessage = "connection not received!!!";

    private static Connection connection;
    private static final UserDao userDao = new UserDao();

    @BeforeAll
    public static void init() {
        logger.info(initMessage);
        //connection = ConnectionPool.getInstance().getConnection();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shop" ,"root","1085");
        } catch (SQLException e) {
            logger.info(connectionErrorMessage+" ==> "+e.getMessage());
            throw new RuntimeException(e);
        }

        ScriptRunner runner = new ScriptRunner(connection, false, false);
        String file = "src/test/resources/tableCreator.sql";
        try {
            runner.runScript(new BufferedReader(new FileReader(file)));
            logger.info(testDBCreated);
        } catch (IOException | SQLException e) {
            logger.info(testDBError);
            throw new RuntimeException(e);
        }
        logger.info(connectionMessage);
    }

    @AfterAll
    public static void destroyConnectionPool() {
       // ConnectionPool.getInstance().close(connection);
        logger.info(tearDownMessage);
    }

    @BeforeEach
    void setUp() {
        System.out.println("sdf");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {

    }

    @Test
    void findByLogin() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findById() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void mapFromEntity() {
    }

    @Test
    void mapToEntity() {
    }
}