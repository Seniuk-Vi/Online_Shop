package com.shop.db.dao;

import com.shop.db.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {

    protected List<T> findAll(Connection con, String sql) throws SQLException {
        List<T> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(mapToEntity(rs));
            }

        } finally {
            close(pstm, rs);
        }
        return list;

    }

    protected <V> List<T> findByField(Connection con, String sql, V value) throws SQLException {
        List<T> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = con.prepareStatement(sql);
            switch (value.getClass().getSimpleName()) {
                case "String":
                    pstm.setString(1, (String) value);
                    break;
                case "Integer":
                    pstm.setInt(1, (Integer) value);
                    break;
                default:
                    throw new IllegalArgumentException("Can't find by field ==> "+value.getClass().getSimpleName() +sql);
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(mapToEntity(rs));
            }

        } finally {
            close(pstm, rs);
        }
        return list;

    }

    protected int add(Connection con, String sql, T item) throws DbException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int id = -1;

        try {
            // add item
            pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            mapFromEntity(pstm, item);
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // get id
            try (ResultSet generatedKeys = pstm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println("Can't get generated keys");

            }
        } catch (SQLException ex) {
            // toDO
            System.out.println("Cant add" + item.getClass().getSimpleName().toString());
            System.out.println(ex.getMessage());
            throw new DbException("Cant add" + item.getClass().getSimpleName().toString(), ex);
        } finally {
            close(pstm, rs);
        }


        return id;
    }

    protected <V> void updateByField(Connection con, String sql, T item, int parameterIndex, V value) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = con.prepareStatement(sql);
            mapFromEntity(pstm, item);
            switch (value.getClass().getSimpleName()) {
                case "String":
                    pstm.setString(parameterIndex, (String) value);
                    break;
                case "Integer":
                    pstm.setInt(parameterIndex, (Integer) value);
                    break;
                default:
                    throw new IllegalArgumentException("Can't find by field ==> " + value);
            }
            if (pstm.executeUpdate() == 0) {
                System.out.println("Not updated");
            }

        } finally {
            close(pstm, rs);
        }


    }

    protected <V> void deleteByField(Connection con, String sql, V value) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = con.prepareStatement(sql);
            switch (value.getClass().getSimpleName()) {
                case "String":
                    pstm.setString(1, (String) value);
                    break;
                case "Integer":
                    pstm.setInt(1, (Integer) value);
                    break;
                default:
                    throw new IllegalArgumentException("Can't find by field");
            }
            if (pstm.executeUpdate() == 0) {
                System.out.println("Not deleted");
            }

        } finally {
            close(pstm, rs);
        }
    }

    protected <V> void deleteByMultFields(Connection con, String sql, V... value) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        V[] values = value;
        try {
            pstm = con.prepareStatement(sql);
            switch (value.getClass().getSimpleName()) {
                case "String":
                    for (int i = 0; i < values.length; i++) {
                        pstm.setString(i + 1, (String) values[i]);
                    }

                    break;
                case "Integer":
                    for (int i = 0; i < values.length; i++) {
                        pstm.setInt(i + 1, (Integer) values[i]);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Can't find by field");
            }
            if (pstm.executeUpdate() == 0) {
                System.out.println("Not deleted");
            }

        } finally {
            close(pstm, rs);
        }
    }

    private void close(PreparedStatement pstm, ResultSet rs) {
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                System.out.println("Cant close!!!");
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Cant close!!!");
            }
        }


    }

    protected abstract void mapFromEntity(PreparedStatement pstm, T item) throws SQLException;

    protected abstract T mapToEntity(ResultSet rs) throws SQLException;


}
