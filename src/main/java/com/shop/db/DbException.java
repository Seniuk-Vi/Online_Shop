package com.shop.db;

import java.sql.SQLException;

public class DbException extends Exception {

    public DbException(Exception e){
        super(e);
    }
    public DbException(String e){
        super(e);
    }
}
