package com.shop.models.entity;


import java.io.Serializable;

/**
 * Describes User's entity
 */

public class User implements Serializable {

    private long id;
    private String login;
    private String name;
    private String surname;
    private int phoneNumber;
    private String email;
    private int role=2;
    private String password;
    private String locale;

    public User() {
    }

//    public User(long id, String login, String name, String surname, int phoneNumber, String email, Role role, String password, String locale) {
//        this.id = id;
//        this.login = login;
//        this.name = name;
//        this.surname = surname;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.role = role;
//        this.password = password;
//        this.locale = locale;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

//    public enum Role {
//        ADMIN, USER;
//
//        public boolean isUser() {
//            return this == USER;
//        }
//
//        public boolean isAdmin() {
//            return this == ADMIN;
//        }
//
//        @Override
//        public String toString() {
//            return super.toString().toLowerCase();
//        }
//    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
