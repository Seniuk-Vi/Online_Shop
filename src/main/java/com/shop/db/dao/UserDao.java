//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
    public static final String SQL_ADD_USER = "INSERT INTO user (login,name,surname,phone_number,email,role,password,locale)VALUES(?,?,?,?,?,?,?,?)";
    public static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login = ?";
    public static final String SQL_FIND_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    public static final String SQL_FIND_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String SQL_UPDATE_USER = "UPDATE user SET login=?, name=?, surname=?, phone_number=?, email=?, role=?, password=?, locale=? WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE * FROM user WHERE id = ?";

    public UserDao() {
    }

    public List<User> findAll(Connection con) throws SQLException {
        List<User> list = this.findAll(con, this.SQL_GET_ALL_USERS);
        return list;
    }

    public User findByLogin(Connection con, String login) throws SQLException {
        List<User> list = this.findByField(con, "SELECT * FROM user WHERE login = ?", login);
        if (list.isEmpty()) {
            throw new SQLException("Can't find by login");
        } else {
            return (User)list.get(0);
        }
    }

    public User findByEmail(Connection con, String email) throws SQLException {
        List<User> list = this.findByField(con, "SELECT * FROM user WHERE email = ?", email);
        if (list.isEmpty()) {
            throw new SQLException("Can't find by email");
        } else {
            return (User)list.get(0);
        }
    }

    public User findById(Connection con, int id) throws SQLException {
        List<User> list = this.findByField(con, "SELECT * FROM user WHERE id = ?", id);
        if (list.isEmpty()) {
            throw new SQLException("Can't find by id");
        } else {
            return (User)list.get(0);
        }
    }

    public void add(Connection con, User user) throws DbException {
        this.add(con, "INSERT INTO user (login,name,surname,phone_number,email,role,password,locale)VALUES(?,?,?,?,?,?,?,?)", user);
    }

    public void update(Connection con, User user, User newUser) throws SQLException {
        this.updateByField(con, "UPDATE user SET login=?, name=?, surname=?, phone_number=?, email=?, role=?, password=?, locale=? WHERE id = ?", newUser, 9, user.getId());
    }

    public void delete(Connection con, User user) throws SQLException {
        this.deleteByField(con, "DELETE * FROM user WHERE id = ?", user.getId());
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
