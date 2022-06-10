package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.Category;
import com.shop.models.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao extends GenericDAO<Category>{





    public String SQL_GET_ALL_CATEGORIES = "SELECT * FROM category";

    public static final String SQL_ADD_CATEGORY = "INSERT INTO category (category) "
            + "VALUES" + "(?)";
    public static final String SQL_UPDATE_CATEGORY = "UPDATE category SET" +
            "category=?" + "WHERE category = ?";
    public static final String SQL_DELETE_CATEGORY = "DELETE * FROM category WHERE category = ?";


    public List<Category> findAll(Connection con) throws SQLException {
        List<Category> list;
        list = findAll(con, SQL_GET_ALL_CATEGORIES);
        return null;
    }

    public void add(Connection con, Category category) throws DbException {
        add(con, SQL_ADD_CATEGORY, category);


    }


    public void update(Connection con, Category category, Category newCategory) throws SQLException {
        updateByField(con, SQL_UPDATE_CATEGORY, newCategory, 2, category.getCategory());
    }


    public void delete(Connection con, Category category) throws SQLException {
        deleteByField(con, SQL_DELETE_CATEGORY, category.getCategory());
    }


    protected void mapFromEntity(PreparedStatement pstmt,  Category category ) throws SQLException {
        int k = 1;
        pstmt.setString(k++, category.getCategory());
        System.out.println("mapFromEntity category ==> " + pstmt.toString());
    }

    @Override
    protected Category mapToEntity(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategory(rs.getString("category"));
        System.out.println("mapToEntity category ==> "+category);
        return category;
    }



}
