package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends GenericDAO<User> {


    public String SQL_GET_ALL_USERS = "SELECT * FROM user";

    public static final String SQL_ADD_USER = "INSERT INTO user (login,name,surname,phone_number,email,role,password,locale)"
            + "VALUES" + "(?,?,?,?,?,?,?,?)";

    public static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    public static final String SQL_FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String SQL_UPDATE_USER = "UPDATE user SET login=?, name=?, surname=?, phone_number=?, email=?, role=?," +
            "password=?,locale=?"+"WHERE login = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE * FROM user WHERE id = ?";


    public List<User> findAll(Connection con) throws SQLException {
        List<User> list;
        list = findAll(con, SQL_GET_ALL_USERS);
        return null;
    }

    public User findByLogin(Connection con, String login) throws SQLException {
        List<User> list = findByField(con, SQL_FIND_BY_LOGIN, login);
        if (list.isEmpty()) {
            throw new SQLException("Can't find by login");
        }
        return list.get(0);
    }

    public User findById(Connection con, long id) throws SQLException {
        List<User> list = findByField(con, SQL_FIND_BY_ID, id);
        if (list.isEmpty()) {
            throw new SQLException("Can't find by id");
        }
        return list.get(0);
    }


    public void add(Connection con, User user) throws DbException {
        add(con, SQL_ADD_USER, user);


    }


    public void update(Connection con, User user, User newUser) throws SQLException {
        updateByField(con,SQL_UPDATE_USER,newUser,9,user.getId());
    }


    public void delete(Connection con, User user) throws SQLException {
        deleteByField(con,SQL_DELETE_BY_ID,user.getId());
    }


    protected void mapFromEntity(PreparedStatement pstmt, User user) throws SQLException {
        int k = 1;
        pstmt.setString(k++, user.getLogin());
        pstmt.setString(k++, user.getName());
        pstmt.setString(k++, user.getSurname());
        pstmt.setInt(k++, user.getPhoneNumber());
        pstmt.setString(k++, user.getEmail());
        pstmt.setInt(k++, user.getRole());
        pstmt.setString(k++, user.getPassword());
        pstmt.setString(k++, user.getLocale());
        System.out.println(pstmt.toString());
    }

    @Override
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
        System.out.println(user);
        return user;
    }
}
