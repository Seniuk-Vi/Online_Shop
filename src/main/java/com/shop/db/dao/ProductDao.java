//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.Category;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends GenericDAO<Product> {
    final static Logger logger = Logger.getLogger(ProductDao.class);

    public String SQL_GET_ALL_PRODUCTS = "SELECT * FROM product";
    public String SQL_GET_PRODUCTS = "SELECT * FROM product WHERE category like ? AND  title LIKE ? AND price BETWEEN ? AND ? ORDER BY CHANGE CHAANGE LIMIT ? OFFSET ? ";
    public String SQL_GET_NUMBER_OF_ROWS = "SELECT COUNT(*) FROM product WHERE category like ? AND  title LIKE ? AND price BETWEEN ? AND ? ";
    public String SQL_GET_MIN_PRICE = "SELECT min(price) FROM product";
    public String SQL_GET_MAX_PRICE = "SELECT max(price) FROM product";
    public String SQL_FIND_BY_ID = "SELECT * FROM product where id=?";
    public static final String SQL_ADD_PRODUCT = "INSERT INTO product (title,description,price,image_url,model_year,in_stock,category,state) VALUES(?,?,?,?,?,?,?,?)";
    public static final String SQL_UPDATE_PRODUCT = "UPDATE product SET title=?, description=?, price=?, image_url=?, model_year=?, in_stock=?,category=?,state=?WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM product WHERE id = ?";

    public double getMinPrice(Connection con) {
        PreparedStatement pstm;
        ResultSet rs;
        int min = 0;
        try {
            pstm = con.prepareStatement(SQL_GET_MIN_PRICE);
            rs = pstm.executeQuery();
            if (rs.next()) {
                min = rs.getInt(1);
            }
            close(pstm, rs);
        } catch (SQLException ex) {
            logger.info("Can't getMinPrice");
        }
        return min;
    }

    public double getMaxPrice(Connection con) {
        PreparedStatement pstm;
        ResultSet rs;
        int max = 0;
        try {
            pstm = con.prepareStatement(SQL_GET_MAX_PRICE);
            rs = pstm.executeQuery();
            if (rs.next()) {
                max = rs.getInt(1);
            }
            close(pstm, rs);
        } catch (SQLException ex) {
            logger.info("Can't getMinPrice");
        }
        return max;
    }

    public List<Product> findAll(Connection con) throws DbException {
        List<Product> list;
        try {
            list = findAll(con, SQL_GET_ALL_PRODUCTS);
        } catch (DbException e) {
            logger.error("Can't find all products");
            throw new DbException("Can't find all products", e);
        }
        return list;
    }

    public int getRowsCount(Connection con, String category, int priceMin, int priceMax, String search) throws DbException {
        PreparedStatement pstm = null;
        ResultSet rs;
        int count = 0;
        try {
            pstm = con.prepareStatement(SQL_GET_NUMBER_OF_ROWS);
            String searchT = "%" + search + "%";
            int k = 1;
            pstm.setString(k++, category);
            pstm.setString(k++, searchT);
            pstm.setInt(k++, priceMin);
            pstm.setInt(k++, priceMax);
            rs = pstm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            close(pstm, rs);
        } catch (SQLException ex) {
            logger.error("Can't getRowsCount, SQL ==> " + pstm);
            throw new DbException("Can't get the page count");
        }
        return count;
    }

    public List<Product> findAllWithSelect(Connection con, int limit, int offset, String category, int priceMin, int priceMax, String order, String way, String search) throws DbException {
        List<Product> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            String SQL = SQL_GET_PRODUCTS;
            SQL = SQL.replace("CHANGE", order);
            SQL = SQL.replace("CHAANGE", way);
            String searchT = "%" + search + "%";
            pstm = con.prepareStatement(SQL);
            int k = 1;
            pstm.setString(k++, category);
            pstm.setString(k++, searchT);
            pstm.setInt(k++, priceMin);
            pstm.setInt(k++, priceMax);
            pstm.setInt(k++, limit);
            pstm.setInt(k++, offset);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(mapToEntity(rs));
            }
        } catch (SQLException ex) {
            logger.error("Can't findAllWithSelect, SQL ==> " + pstm);
            throw new DbException("Error when receiving products", ex);
        } finally {
            close(pstm, rs);
        }
        return list;
    }

    public Product findById(Connection con, int id) throws DbException {
        List<Product> list = findByField(con, SQL_FIND_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public void add(Connection con, Product product) throws DbException {
        add(con, SQL_ADD_PRODUCT, product);
    }

    public void update(Connection con, Product product, Product newProduct) throws DbException {
        updateByField(con, SQL_UPDATE_PRODUCT, newProduct, 9, product.getId());
    }

    public void deleteById(Connection con, int product) throws DbException {
        deleteByField(con, SQL_DELETE_BY_ID, product);
    }

    protected void mapFromEntity(PreparedStatement pstm, Product product) throws SQLException {
        int k = 1;
        pstm.setString(k++, product.getTitle());
        pstm.setString(k++, product.getDescription());
        pstm.setDouble(k++, product.getPrice());
        pstm.setString(k++, product.getImageUrl());
        pstm.setInt(k++, product.getModelYear());
        pstm.setInt(k++, product.getInStock());
        pstm.setString(k++, product.getCategory());
        pstm.setString(k++, product.getCondition());
        logger.info("PreparedStatement ==>" + pstm);
    }

    protected Product mapToEntity(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setTitle(rs.getString("title"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getInt("price"));
        product.setImageUrl(rs.getString("image_url"));
        product.setModelYear(rs.getInt("model_year"));
        product.setInStock(rs.getInt("in_stock"));
        product.setCategory(rs.getString("category"));
        product.setCondition(rs.getString("state"));
        logger.info("Product ==>" + product);
        return product;
    }
}
