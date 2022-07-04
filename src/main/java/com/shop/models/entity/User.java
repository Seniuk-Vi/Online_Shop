//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.models.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String login;
    private String name;
    private String surname;
    private int phoneNumber;
    private String email;
    private int role = 2;
    private String password;
    private String locale;

    public User() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return this.role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String toString() {
        return "User{id=" + this.id + ", login='" + this.login + "', name='" + this.name + "', surname='" + this.surname + "', phoneNumber=" + this.phoneNumber + ", email='" + this.email + "', role=" + this.role + ", password='" + this.password + "', locale='" + this.locale + "'}";
    }
}
