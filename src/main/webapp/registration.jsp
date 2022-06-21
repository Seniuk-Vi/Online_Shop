<%--
  Created by IntelliJ IDEA.
  User: lepre
  Date: 18.05.2022
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name = "command" value="registration"><br>
    <input name="login" placeholder="login"><br>
    <input name="name" placeholder="name"><br>
    <input name="surname" placeholder="surname"><br>
    <input type="number" name="phoneNumber" placeholder="phoneNumber"><br>
    <input  name="email" placeholder="email"><br>
    <input type="password" name = "password" placeholder="password"><br>
    <input name = "locale" placeholder="locale"><br>
    <input type="submit" value="Login"><br>
</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
