package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.Product;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDao extends GenericDAO<Product> {


    public String SQL_GET_ALL_PRODUCTS = "SELECT * FROM product";

    public static final String SQL_ADD_PRODUCT = "INSERT INTO product (title,description,price,image_url,model_year,in_stock,category,state) "
            + "VALUES" + "(?,?,?,?,?,?,?,?)";
    public static final String SQL_UPDATE_PRODUCT = "UPDATE product SET title=?, description=?, price=?, image_url=?, model_year=?, in_stock=?," +
            "category=?,state=?" + "WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE * FROM product WHERE id = ?";


    public List<Product> findAll(Connection con) throws SQLException {
        List<Product> list;
        list = findAll(con, SQL_GET_ALL_PRODUCTS);
        return list;
    }

    public void add(Connection con, Product product) throws DbException {
        add(con, SQL_ADD_PRODUCT, product);


    }


    public void update(Connection con, Product product, Product newProduct) throws SQLException {
        updateByField(con, SQL_UPDATE_PRODUCT, newProduct, 9, product.getId());
    }


    public void delete(Connection con, Product product) throws SQLException {
        deleteByField(con, SQL_DELETE_BY_ID, product.getId());
    }


    protected void mapFromEntity(PreparedStatement pstmt, Product product) throws SQLException {
        int k = 1;
        pstmt.setString(k++, product.getTitle());
        pstmt.setString(k++, product.getDescription());
        pstmt.setDouble(k++, product.getPrice());
        pstmt.setString(k++, product.getImageUrl());
        pstmt.setInt(k++, product.getModelYear());
        pstmt.setInt(k++, product.getInStock());
        pstmt.setString(k++, product.getCategory());
        pstmt.setString(k++, product.getCondition());
        System.out.println("mapFromEntity product ==> " + pstmt.toString());
    }

    @Override
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
        System.out.println(product);
        return product;
    }
}
