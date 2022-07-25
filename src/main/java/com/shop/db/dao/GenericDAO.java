//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.controller.Controller;
import com.shop.db.DbException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {
    final static Logger logger = Logger.getLogger(GenericDAO.class);

    /**
     *
     * @param con Connection
     * @param sql
     * @return List of Objects
     * @throws DbException if the request failed
     */
    protected List<T> findAll(Connection con, String sql) throws DbException {
        List<T> list = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(this.mapToEntity(rs));
            }
        } catch (SQLException ex) {
            logger.error("Can't findAll, SQL ==> " + pstm, ex);
            throw new DbException("Can't findAll", ex);
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

//    protected List<T> findAllPagination(Connection con, String sql, int limit, int offset) throws DbException {
//        List<T> list = new ArrayList<>();
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        try {
//            pstm = con.prepareStatement(sql);
//            pstm.setInt(1, limit);
//            pstm.setInt(2, offset);
//            System.out.println(pstm);
//            rs = pstm.executeQuery();
//            while (rs.next()) {
//                list.add(this.mapToEntity(rs));
//            }
//        } catch (SQLException ex) {
//            logger.error("Can't findAllPagination, SQL ==> " + pstm, ex);
//            throw new DbException("Can't findAllPagination", ex);
//        } finally {
//            this.close(pstm, rs);
//        }
//        return list;
//    }

    /**
     *
     * @param con Connection
     * @param sql SQL query
     * @param value Search field
     * @return List
     * @param <V> Class of searching field
     * @throws DbException if the request failed, or if search field class isn't String or Integer
     */
    protected <V> List<T> findByField(Connection con, String sql, V value) throws DbException {
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
                    String clazz = value.getClass().getSimpleName();
                    logger.error("Can't find by this class" + clazz);
                    throw new DbException("Can't find by this class" + clazz);
            }
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(this.mapToEntity(rs));
            }
        } catch (SQLException ex) {
            logger.error("Can't findByField, SQL ==> " + pstm, ex);
            throw new DbException("Can't findByField", ex);
        } finally {
            this.close(pstm, rs);
        }
        return list;
    }

    /**
     *
     * @param con Connection
     * @param sql SQL query
     * @param item Object to add
     * @return id if object was added, -1 if not added
     * @throws DbException if the request failed
     */
    protected int add(Connection con, String sql, T item) throws DbException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int id = -1;
        try {
            pstm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.mapFromEntity(pstm, item);
            if (pstm.executeUpdate() == 0) {
                throw new SQLException();
            }
            ResultSet generatedKeys = pstm.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        } catch (SQLException ex) {
            logger.error("Can't add, SQL ==> " + pstm, ex);
            throw new DbException("Can't add", ex);
        } finally {
            close(pstm, rs);
        }
        return id;
    }

    /**
     *
     * @param con Connection
     * @param sql SQL query
     * @param item Object to change
     * @param parameterIndex the index into which the field is inserted in the PreparedStatement
     * @param value new value
     * @param <V> field class
     * @throws DbException if the request failed, or if field class isn't String or Integer
     */
    protected <V> void updateByField(Connection con, String sql, T item, int parameterIndex, V value) throws DbException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = con.prepareStatement(sql);
            mapFromEntity(pstm, item);
            String clazz = value.getClass().getSimpleName();
            switch (clazz) {
                case "String":
                    pstm.setString(parameterIndex, (String) value);
                    break;
                case "Integer":
                    pstm.setInt(parameterIndex, (Integer) value);
                    break;
                default:
                    logger.error("Can't update by this class" + clazz);
                    throw new DbException("Can't update by this class" + clazz);
            }
            if (pstm.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            logger.error("Can't updateByField, SQL => " + pstm);
            throw new DbException("Can't updateByField");
        } finally {
            this.close(pstm, rs);
        }

    }

    /**
     *
     * @param con Connection
     * @param sql SQL query
     * @param value deleting by this field
     * @param <V> field class
     * @throws DbException if the request failed, or if field class isn't String or Integer
     */
    protected <V> void deleteByField(Connection con, String sql, V value) throws DbException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = con.prepareStatement(sql);
            String clazz = value.getClass().getSimpleName();
            switch (clazz) {
                case "String":
                    pstm.setString(1, (String) value);
                    break;
                case "Integer":
                    pstm.setInt(1, (Integer) value);
                    break;
                default:
                    logger.error("Can't delete by this class" + clazz);
                    throw new DbException("Can't delete by this class" + clazz);
            }
            if (pstm.executeUpdate() == 0) {
                throw new SQLException();
            }
        } catch (SQLException ex) {
            logger.error("Can't deleteByField" + pstm);
            throw new DbException("Can't deleteByField");
        } finally {
            this.close(pstm, rs);
        }

    }

    /**
     *
     * @param con Connection
     * @param sql SQL query
     * @param values deleting by this fields
     * @param <V> fields class
     * @throws DbException if the request failed, or if fields class isn't String or Integer
     */
    protected <V> void deleteByMultFields(Connection con, String sql, V... values) throws DbException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = con.prepareStatement(sql);
            String clazz = values.getClass().getSimpleName();
            int i;
            switch (clazz) {
                case "String":
                    i = 0;
                    while (i < values.length) {
                        pstm.setString(++i, (String) values[i]);
                    }
                    break;
                case "Integer":
                    i = 0;
                    while (i < values.length) {
                        pstm.setInt(++i, (Integer) values[i]);
                    }
                    break;
                default:

                    logger.error("Can't delete by this class" + clazz);
                    throw new IllegalArgumentException("Can't find by field");
            }
            if (pstm.executeUpdate() == 0) {
                throw new SQLException();
            }
        }catch (SQLException ex){
            logger.error("Can't deleteByField" + pstm);
            throw new DbException("Can't deleteByField");
        }finally {
            this.close(pstm, rs);
        }

    }

    /**
     *
     * @param con Connection to be closed
     */
    protected void close(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            logger.error("Can't close Connection");
        }
    }

    /**
     *
     * @param pstm PreparedStatement to be closed
     * @param rs ResultSet to be closed
     */
    protected void close(PreparedStatement pstm, ResultSet rs) {
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException ex) {
                logger.error("Can't close PreparedStatement");
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.error("Can't close ResultSet");
            }
        }

    }

    /**
     * Abstract function that put Object into PreparedStatement
     * @param preparedStatement PreparedStatement
     * @param obj inserting Object
     * @throws SQLException
     */
    protected abstract void mapFromEntity(PreparedStatement preparedStatement, T obj) throws SQLException;

    /**
     *  Abstract function that gets Object from ResultSet
     * @param resultSet
     * @return Object in ResultSet
     * @throws SQLException
     */
    protected abstract T mapToEntity(ResultSet resultSet) throws SQLException;
}
