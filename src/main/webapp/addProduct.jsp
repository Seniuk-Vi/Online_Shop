<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="controller" method="post">
        <input type="hidden" name = "command" value="addProduct"><br>
        <input name="title" placeholder="title"><br>
        <input name = "description" placeholder="description"><br>
        <input type="number" name = "price" placeholder="price"><br>
        <input  name = "image_url" placeholder="image"><br>
        <input type="number"  name = "model_year" placeholder="model year"><br>
        <input type="number"  name = "in_stock" placeholder="in stock"><br>
        <input name = "category" placeholder="category"><br>
        <input  name = "state" placeholder="state"><br>
        <input type="submit" value="Add"><br>

    </form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
