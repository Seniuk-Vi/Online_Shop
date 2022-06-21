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
<div> title: ${product.title} </div>
<div><a href="changeField.jsp">Change title ></a></div>
<br>
<div> description: ${product.description} </div>
<div><a href="changeField.jsp">Change description ></a></div>
<br>
<div> price: ${product.price} </div>
<div><a href="changeField.jsp">Change price ></a></div>
<br>
<div> model_year: ${product.modelYear} </div>
<div><a href="changeField.jsp">Change model_year ></a></div>
<br>
<div> in_stock: ${product.inStock} </div>
<div><a href="changeField.jsp">Change in_stock ></a></div>
<br>
<div> category: ${product.category} </div>
<div><a href="changeField.jsp">Change category ></a></div>
<br>
<div> state: ${product.condition} </div>
<div><a href="changeField.jsp">Change state ></a></div>
<br>
<form action="controller" method="post">
    <input type="hidden" name="command" value="deleteProduct">
    <input type="hidden" name="product_id" value="${product.id}">
    <input type="submit" value="Delete product">

</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>

</body>
</html>


