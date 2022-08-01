//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.Category;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao extends GenericDAO<Category> {
    final static Logger logger = Logger.getLogger(CategoryDao.class);

    public String SQL_GET_ALL_CATEGORIES = "SELECT * FROM category";
    public String SQL_FIND_BY_NAME = "SELECT * FROM category WHERE category = ?";
    public static final String SQL_ADD_CATEGORY = "INSERT INTO category (category) VALUES(?)";
    public static final String SQL_UPDATE_CATEGORY = "UPDATE category SET category=? WHERE category = ?";
    public static final String SQL_DELETE_CATEGORY = "DELETE * FROM category WHERE category = ?";

    public List<Category> findAll(Connection con) throws DbException {
        return findAll(con, this.SQL_GET_ALL_CATEGORIES);
    }
    public Category findByName(Connection con, String name) throws DbException {
        List<Category> list = findByField(con, SQL_FIND_BY_NAME, name);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);

    }
    public void add(Connection con, Category category) throws DbException {
        add(con, SQL_ADD_CATEGORY, category);
    }

    public void update(Connection con, Category category, Category newCategory) throws DbException {
        updateByField(con, SQL_UPDATE_CATEGORY, newCategory, 2, category.getCategory());
    }

    public void delete(Connection con, Category category) throws DbException {
        deleteByField(con, SQL_DELETE_CATEGORY, category.getCategory());
    }

    protected void mapFromEntity(PreparedStatement pstm, Category category) throws SQLException {
        int k = 1;
        pstm.setString(k++, category.getCategory());
        logger.info("PreparedStatement ==>"+pstm);
    }

    protected Category mapToEntity(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setCategory(rs.getString("category"));
        logger.info("Category ==>"+category);
        return category;
    }
}
