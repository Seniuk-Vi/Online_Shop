
package com.shop.command;

import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationCommand implements Command {

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showHomePage";
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("currentUser");
        System.out.println("session user ==> " + user);
        if (user != null) {
            return "homePage.jsp";
        } else {
            String login = req.getParameter("login");
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String phoneNumber = req.getParameter("phoneNumber");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String locale = req.getParameter("locale");
            Map<String, String> registrationAttributes = new HashMap();
            registrationAttributes.put("login", login);
            registrationAttributes.put("name", name);
            registrationAttributes.put("surname", surname);
            registrationAttributes.put("password", password);
            registrationAttributes.put("phoneNumber", phoneNumber);
            registrationAttributes.put("email", email);
            registrationAttributes.put("locale", locale);
            System.out.println(registrationAttributes);
            if (!Validation.isLoginValid(login)) {
                registrationAttributes.put("loginMessage", "2-20 length, only characters and digits");
                System.out.println("password don't valid");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            } else if (!Validation.isNameValid(name)) {
                registrationAttributes.put("nameMessage", "2-10 length, only characters");
                System.out.println("name is required");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            } else if (!Validation.isSurnameValid(surname)) {
                registrationAttributes.put("surnameMessage", "2-10 length, only characters");
                System.out.println("surname must contain only characters (2-10)");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            } else if (!Validation.isPhoneValid(phoneNumber)) {
                registrationAttributes.put("phoneNumberMessage", "8-10 length, only digits");
                System.out.println("8-12 length, only digits");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            } else if (!Validation.isEmailValid(email)) {
                registrationAttributes.put("emailMessage", "email don't valid");
                System.out.println("email don't valid");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            } else if (!Validation.isPasswordCorrect(password)) {
                registrationAttributes.put("passwordMessage", "8-25 length, at least one letter and one number");
                System.out.println("password don't valid");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            } else if (!locale.equals("uk") && !locale.equals("en")) {
                registrationAttributes.put("localeMessage", "locale 'uk' or 'en'");
                System.out.println("locale 'uk' or 'en'");
                return this.passAttributesToSession(req, resp, registrationAttributes);
            } else {
                Connection con = DbHelper.getInstance().getConnection();
                UserDao userDao = new UserDao();

                try {
                    if (userDao.findByLogin(con, login) != null) {
                        registrationAttributes.put("loginMessage", "login isn't available");
                        return passAttributesToSession(req, resp, registrationAttributes);
                    }
                } catch (DbException ex) {
                    System.out.println(ex.getMessage());
                }

                try {
                    if (userDao.findByEmail(con, email) != null) {
                        registrationAttributes.put("emailMessage", "email isn't available");
                        return this.passAttributesToSession(req, resp, registrationAttributes);
                    }
                } catch (DbException ex) {
                    System.out.println(ex.getMessage());
                }

                try {
                    User newUser = new User();
                    newUser.setLogin(login);
                    newUser.setName(name);
                    newUser.setSurname(surname);
                    newUser.setPhoneNumber(Integer.parseInt(phoneNumber));
                    newUser.setEmail(email);
                    newUser.setPassword(password);
                    newUser.setLocale(locale);
                    newUser.setRole(0);
                    userDao.add(con, newUser);
                    System.out.println("user was added");
                } catch (DbException var19) {
                    registrationAttributes.put("LoginError", "can't add user");
                    System.out.println("can't add user");
                    return this.passAttributesToSession(req, resp, registrationAttributes);
                }

                try {
                    user = userDao.findByLogin(con, login);
                } catch (DbException ex) {
                    req.getSession().setAttribute("fatalError", "Fatal login error");
                    return "error.jsp";
                }

                try {
                    con.close();
                } catch (SQLException ex) {
                }
                session = req.getSession(true);
                session.setAttribute("currentUser", user);
                session.setAttribute("userLocale", user.getLocale());

                return address;
            }
        }
    }

    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) {
        Iterator iterator = viewAttributes.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
        return "registration.jsp";
    }
}
