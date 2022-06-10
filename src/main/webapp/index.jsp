<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
    <a href="login.jsp">Login</a><br>
    <a href="homePage.jsp">Home Page</a><br>
    <a href="error.jsp">Error Page</a><br>
    <a href="registration.jsp">Registration Page</a><br>
    <a href="addProduct.jsp">Add Product Page</a><br>
    <a href="addCategory.jsp">Add Category Page</a><br>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="showProducts"><br>
        <input type="submit" value="Show tables">
    </form>

</body>
</html>


