package com.shop.db;

public class ConException extends RuntimeException {

    public ConException(Exception e) {
        super(e);
    }

    public ConException() {
        super();
    }

    public ConException(String s, Exception e) {
        super(s, e);
    }

    public ConException(String e) {
        super(e);
    }
}
