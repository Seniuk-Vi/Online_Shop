//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.Category;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends GenericDAO<Category> {
    public String SQL_GET_ALL_CATEGORIES = "SELECT * FROM category";
    public static final String SQL_ADD_CATEGORY = "INSERT INTO category (category) VALUES(?)";
    public static final String SQL_UPDATE_CATEGORY = "UPDATE category SET category=? WHERE category = ?";
    public static final String SQL_DELETE_CATEGORY = "DELETE * FROM category WHERE category = ?";
    public List<Category> findAll(Connection con) {

        List<Category> list = new ArrayList<>();
        try {
            list = this.findAll(con, this.SQL_GET_ALL_CATEGORIES);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void add(Connection con, Category category) throws DbException {
        this.add(con, SQL_ADD_CATEGORY, category);
    }

    public void update(Connection con, Category category, Category newCategory) throws SQLException {
        this.updateByField(con, SQL_UPDATE_CATEGORY, newCategory, 2, category.getCategory());
    }

    public void delete(Connection con, Category category) throws SQLException {
        this.deleteByField(con, SQL_DELETE_CATEGORY, category.getCategory());
    }

    protected void mapFromEntity(PreparedStatement pstmt, Category category) throws SQLException {
        int k = 1;
        pstmt.setString(k++, category.getCategory());
    }

    protected Category mapToEntity(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategory(rs.getString("category"));
        return category;
    }
}
