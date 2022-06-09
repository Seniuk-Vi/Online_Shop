package com.shop;

import com.shop.db.DbHelper;
import com.shop.models.entity.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        User u = new User();

        System.out.println(Double.MAX_VALUE);

    }
}
