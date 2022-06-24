<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="addProduct"><br>
    <input name="title" placeholder="title" value="title"><br>
    <input name="description" placeholder="description" value="description"><br>
    <input type="number" name="price" placeholder="price" value="333"><br>
    <input type="file" name="image_url" placeholder="image" accept=".jpg, .jpeg, .png"><br>
    <input type="number" name="model_year" placeholder="model year" value="2"><br>
    <input type="number" name="in_stock" placeholder="in stock" value="4"><br>
    <input name="category" placeholder="category" value="engine"><br>
    <input name="state" placeholder="state" value="3"><br>
    <input type="submit" value="Add"><br>
</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
