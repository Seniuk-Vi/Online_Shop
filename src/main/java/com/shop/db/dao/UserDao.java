//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends GenericDAO<User> {
    final static Logger logger = Logger.getLogger(UserDao.class);

    public static final String SQL_GET_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_ADD_USER = "INSERT INTO user (login,name,surname,phone_number,email,role,password,locale)VALUES(?,?,?,?,?,?,?,?)";
    public static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    public static final String SQL_FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    public static final String SQL_FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String SQL_UPDATE_USER = "UPDATE user SET login=?, name=?, surname=?, phone_number=?, email=?, role=?, password=?, locale=? WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE * FROM user WHERE id = ?";

    public List<User> findAll(Connection con) throws DbException {
        return findAll(con, SQL_GET_ALL_USERS);
    }

    /**
     * @param con
     * @param login
     * @return User if it exists or null if doesn't
     * @throws DbException if the request failed
     */
    public User findByLogin(Connection con, String login) throws DbException {
        List<User> list = this.findByField(con, SQL_FIND_BY_LOGIN, login);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * @param con
     * @param email
     * @return USER if it exists or NULL if doesn't
     * @throws DbException if the request failed
     */
    public User findByEmail(Connection con, String email) throws DbException {
        List<User> list = this.findByField(con, SQL_FIND_BY_EMAIL, email);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
    /**
     * @param con
     * @param id
     * @return USER if it exists or NULL if doesn't
     * @throws DbException if the request failed
     */
    public User findById(Connection con, int id) throws DbException {
        List<User> list = this.findByField(con, SQL_FIND_BY_ID, id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }

    }

    public void add(Connection con, User user) throws DbException {
        add(con, SQL_ADD_USER, user);
    }

    public void update(Connection con, User user, User newUser) throws DbException {
        updateByField(con, SQL_UPDATE_USER, newUser, 9, user.getId());
    }

    public void delete(Connection con, User user) throws DbException {
        deleteByField(con, SQL_DELETE_BY_ID, user.getId());
    }

    protected void mapFromEntity(PreparedStatement pstm, User user) throws SQLException {
        int k = 1;
        pstm.setString(k++, user.getLogin());
        pstm.setString(k++, user.getName());
        pstm.setString(k++, user.getSurname());
        pstm.setInt(k++, user.getPhoneNumber());
        pstm.setString(k++, user.getEmail());
        pstm.setInt(k++, user.getRole());
        pstm.setString(k++, user.getPassword());
        pstm.setString(k++, user.getLocale());
        logger.info("PreparedStatement ==>"+pstm);

    }

    protected User mapToEntity(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPhoneNumber(rs.getInt("phone_number"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getInt("role"));
        user.setPassword(rs.getString("password"));
        user.setLocale(rs.getString("locale"));
        logger.info("Order ==>"+user);
        return user;
    }
}
