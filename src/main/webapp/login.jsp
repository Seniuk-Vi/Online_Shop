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

    <form action="controller" method="post" onsubmit="return validate()">
        <h1>Login</h1>
        <input type="hidden" name="command" value="login">
        <input id="login" type="text" placeholder="Enter Login" value="login" name="login" required>
        <input id="password" type="password" placeholder="Enter Password"  value="loginlogin" name="password" required>
        <button type="submit" class="btn">Login</button>
    </form>

<form action="controller" method="post">
    <input type="hidden" name="command" value="login">
    <input name="login" placeholder="password" value="loginlogin">
    <input type="password" name="password" placeholder="password" value="loginlogin">
    <input type="submit" value="Login">
</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
