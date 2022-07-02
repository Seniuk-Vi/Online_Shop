<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="addProduct"><br>
    <input name="title" placeholder="title" value="title" required><br>
    <input name="description" placeholder="description" value="description" required><br>
    <input type="number" name="price" placeholder="price" value="333" required><br>
    <input type="file" name="image_url" placeholder="image" accept=".jpg, .jpeg, .png" required><br>
    <input type="number" name="model_year" placeholder="model year" value="2" required><br>
    <input type="number" min="0" name="in_stock" placeholder="in stock" value="4" required><br>
    <select name="category" required>
        <option value="">select category</option>
        <c:forEach items="${categories}" var="row">
            <option value="${row.category}">${row.category}</option>
        </c:forEach>
    </select><br>
    <input type="number" name="state" min="1" max="10" placeholder="state(1-10)" value="3" required><br>
    <input type="submit" value="Add"><br>
</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>
