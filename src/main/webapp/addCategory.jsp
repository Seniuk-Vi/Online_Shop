<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name = "command" value="addCategory"><br>
    <input name="category" placeholder="name of category"><br>
    <input type="submit" value="Add category"><br>

</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
