<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
    <title>Home page</title>
    <%@include file="/jspf/head.jspf" %>
    <c:if test="${empty products}">
        <c:redirect url="controller?command=showHomePage"></c:redirect>
    </c:if>
</head>

<body class="main">

<%@include file="/jspf/navbar.jspf" %>
<br>


<%--todo--%>
<form action="controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name = "command" value="EditProduct" ><br>
    title:
    <input name="title" placeholder="title" value="${product.title}" ><br>
    description:
    <input name = "description" placeholder="description" value="${product.description}"><br>
    price:
    <input type="number" name = "price" placeholder="price" value="${product.price}"><br>
    model year:
    <input type="number"  name = "model_year" placeholder="model year" value="${product.modelYear}"><br>
    in stock:
    <input type="number"  name = "in_stock" placeholder="in stock" value="${product.inStock}"><br>
    category:
    <input name = "category" placeholder="category" value="${product.category}"><br>
    state:
    <input  name = "state" placeholder="state" value="3"><br>
    <input type="submit" value="change"><br>
</form>

<form action="controller" method="post">
    <input type="hidden" name="command" value="deleteProduct">
    <input type="hidden" name="product_id" value="${product.id}">
    <input type="submit" value="Delete product">

</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>

</body>
</html>


