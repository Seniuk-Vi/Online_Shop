package com.shop;

import org.apache.commons.validator.routines.EmailValidator;

public class Validation {
    static final String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,25}$";
    static final String phoneRegex = "^[0-9]{8,10}$";
    static final String priceRegex = "^[0-9]{1,5}$";
    static final String loginRegex = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d]{2,20}$";
    static final String titleRegex = "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d]{2,20}$";
    static final String textRegex = "[a-zA-Za-ö-w-я]{60,500}$";
    static final String nameRegex = "[a-zA-Za-ö-w-я]{2,10}$";

    public Validation() {
    }

    public static boolean isPasswordCorrect(String password) {
        return password != null && password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,25}$");
    }

    public static boolean isEmailValid(String email) {
        return email != null && EmailValidator.getInstance().isValid(email);
    }

    public static boolean isLoginValid(String login) {
        return login != null && login.matches("^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d]{2,20}$");
    }

    public static boolean isNameValid(String name) {
        return name != null && name.matches("[a-zA-Za-ö-w-я]{2,10}$");
    }

    public static boolean isSurnameValid(String surname) {
        return surname != null && surname.matches("[a-zA-Za-ö-w-я]{2,10}$");
    }

    public static boolean isTextValid(String title) {
        return title != null && title.matches("^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d]{2,20}$");
    }

    public static boolean isPhoneValid(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^[0-9]{8,10}$");
    }

    public static boolean isTitleValid(String name) {
        return name != null && name.matches("[a-zA-Za-ö-w-я]{2,10}$");
    }

    public static boolean isPriceValid(int price) {
        return price > 0 && price < 99999;
    }

    public static boolean isDescValid(String desc) {
        return desc != null && desc.length() >= 60 && desc.length() <= 500;
    }

    public static boolean isYearValid(String year) {
        return year != null && year.length() == 4;
    }

    public static boolean isStockValid(int stock) {
        return stock >= 0;
    }

    public static boolean isStateValid(int state) {
        return state > 0 && state < 11;
    }
}
