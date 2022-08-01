package com.shop.db;

import java.sql.SQLException;

public class DbException extends Exception {

    public DbException(Exception e){
        super(e);
    }
    public DbException(){
        super();
    }
    public DbException(String s,Exception e){
        super(s,e);
    }
    public DbException(String e){
        super(e);
    }
}
