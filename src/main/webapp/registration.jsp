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
    <input name="login"><br>
    <input name="name"><br>
    <input name="surname"><br>
    <input type="number" name="phoneNumber"><br>
    <input  name="email"><br>
    <input type="password" name = "password"><br>
    <input name = "locale"><br>
    <input type="submit" value="Login"><br>
</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
