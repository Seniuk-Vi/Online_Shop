//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.db.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {
    public GenericDAO() {
    }

    protected List<T> findAll(Connection con, String sql) throws SQLException {
        List<T> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(this.mapToEntity(rs));
            }
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    protected List<T> findAllPagination(Connection con, String sql, int limit, int offset) throws SQLException {
        List<T> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, limit);
            pstm.setInt(2, offset);
            System.out.println(pstm);
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(this.mapToEntity(rs));
            }
        } finally {
            this.close(pstm, rs);
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
                    String var10002 = value.getClass().getSimpleName();
                    throw new IllegalArgumentException("Can't find by field ==> " + var10002 + sql);
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(this.mapToEntity(rs));
            }
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    protected int add(Connection con, String sql, T item) throws DbException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int id = -1;
        try {
            pstm = con.prepareStatement(sql, 1);
            this.mapFromEntity(pstm, item);
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        } catch (SQLException ex) {
            throw new DbException("Can't add" + item.getClass().getSimpleName(), ex);
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
            this.mapFromEntity(pstm, item);
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

            System.out.println(pstm);
            if (pstm.executeUpdate() == 0) {
                System.out.println("Not updated");
            }
        } finally {
            this.close(pstm,rs);
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
            this.close(pstm, rs);
        }

    }

    protected <V> void deleteByMultFields(Connection con, String sql, V... value) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        V[] values = value;

        try {
            pstm = con.prepareStatement(sql);
            int i;
            label96:
            switch (value.getClass().getSimpleName()) {
                case "String":
                    i = 0;

                    while (true) {
                        if (i >= values.length) {
                            break label96;
                        }

                        pstm.setString(i + 1, (String) values[i]);
                        ++i;
                    }
                case "Integer":
                    i = 0;

                    while (true) {
                        if (i >= values.length) {
                            break label96;
                        }

                        pstm.setInt(i + 1, (Integer) values[i]);
                        ++i;
                    }
                default:
                    throw new IllegalArgumentException("Can't find by field");
            }

            if (pstm.executeUpdate() == 0) {
                System.out.println("Not deleted");
            }
        } finally {
            this.close(pstm,rs);
        }

    }

    private void close(PreparedStatement pstm, ResultSet rs) {
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException ex) {
                System.out.println("Cant close!!!");
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println("Cant close!!!");
            }
        }

    }

    protected abstract void mapFromEntity(PreparedStatement var1, T var2) throws SQLException;

    protected abstract T mapToEntity(ResultSet var1) throws SQLException;
}
