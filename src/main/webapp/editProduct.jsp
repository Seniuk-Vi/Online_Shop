<%@include file="/jspf/header.jspf" %>
<html lang="en">
<head>
    <title>Product</title>
    <%@include file="/jspf/head.jspf" %>
</head>
<body class="main">
<c:if test="${currentUser.role == 1}">
    <%@include file="/jspf/adminNavbar.jspf" %>
</c:if>
<c:if test="${currentUser.role != 1}">
    <c:redirect url="homePage.jsp"/>
</c:if>
<br>

<form action="controller" method="post" id="form" enctype="multipart/form-data">
    <input type="hidden" name = "command" value="editProduct" ><br>
    <input type="hidden" name = "product_id" value="${product.id}" ><br>
    title:
    <input name="title" placeholder="title" value="${product.title}"pattern="[a-zA-Za-ö-w-я]{2,10}$"  required ><span>${titleMessage}</span><br>
    description:
    <textarea rows="4" cols="50" form="form" name="description" placeholder="description" minlength="60" maxlength="500" required>${product.description} </textarea><span>${descriptionMessage}</span><br>
    price:
    <input type="number" name = "price" placeholder="price" value="${product.price}"min="1" max="99999" pattern="^[0-9]{1,5}$"  required><span>${priceMessage}</span><br>
    model year:
    <input type="number" min = "1000" max = "9999" name="model_year" placeholder="model year" value="${product.modelYear}" required><span>${modelYearMessage}</span><br>
    in stock:
    <input type="number" min="0" name="in_stock" placeholder="in stock" value="${product.inStock}"  required><span>${inStockMessage}</span><br>
    category:
    <select name="category">
        <option selected value="${product.category}">${product.category}</option>
        <c:forEach items="${categories}" var="row">
            <option value="${row.category}">${row.category}</option>
        </c:forEach>
    </select><span>${categoryMessage}</span><br>
    state:
    <input type="number" name="state" min="1" max="10" placeholder="state(1-10)" value="${product.condition}" required><span>${stateMessage}</span><br>
    <input type="submit" value="change"><br>
</form>

<form action="controller" method="post">
    <input type="hidden" name="command" value="deleteProduct">
    <input type="hidden" name="product_id" value="${product.id}">
    <input type="submit" value="Delete product">

</form>
${errorMessage}
${info}
<c:remove var="info" scope="session"/>
<c:remove var="errorMessage" scope="session"/>
<c:remove var="titleMessage" scope="session"/>
<c:remove var="descriptionMessage" scope="session"/>
<c:remove var="priceMessage" scope="session"/>
<c:remove var="modelYearMessage" scope="session"/>
<c:remove var="inStockMessage" scope="session"/>
<c:remove var="categoryMessage" scope="session"/>
<c:remove var="stateMessage" scope="session"/>
</body>
</html>


