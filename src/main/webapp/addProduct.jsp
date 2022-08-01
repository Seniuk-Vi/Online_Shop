<%@include file="/jspf/header.jspf" %>
<html>
<head>
    <title>Product</title>
    <%@include file="/jspf/head.jspf" %>
</head>
<body class="main">
<div class="container">
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <c:if test="${currentUser.role == 1}">
        <%@include file="/jspf/adminNavbar.jspf" %>
    </c:if>
    <c:if test="${currentUser.role != 1}">
        <%@include file="/jspf/navbar.jspf" %>
    </c:if>
    <br>
    <form action="controller" id="form" method="post" enctype="multipart/form-data">
        <input type="hidden" name="command" value="addProduct"  ><br>
        <input name="title" placeholder="title" value="${title}" pattern="[a-zA-Za-ö-w-я]{2,10}$" required><span>${titleMessage}</span><br>
        <textarea rows="4" cols="50" form="form" name="description" placeholder="description" minlength="60" maxlength="500" required>${description} </textarea><span>${descriptionMessage}</span><br>
        <input type="number" name="price" placeholder="price" value="${price}" min="1" max="99999" pattern="^[0-9]{1,5}$"  required><span>${priceMessage}</span><br>
        <input type="file" name="image_url" placeholder="image" accept=".jpg, .jpeg, .png" required><span>${imageMessage}</span><br>
        <input type="number" min = "1000" max = "9999" name="model_year" placeholder="model year" value="${model_year}" required><span>${modelYearMessage}</span><br>
        <input type="number" min="0" name="in_stock" placeholder="in stock" value="${in_stock}"  required><span>${inStockMessage}</span><br>
        <select name="productCategory" required>
            <option value="">select category</option>
            <c:forEach items="${categories}" var="row">
                <option value="${row.category}">${row.category}</option>
            </c:forEach>
        </select><span>${categoryMessage}</span><br>
        <input type="number" name="state" min="1" max="10" placeholder="state(1-10)" value="${state}" required><span>${stateMessage}</span><br>
        <input type="submit" value="Add"><br>
    </form>
    ${errorMessage}
    <c:remove var="errorMessage" scope="session"/>
    <c:remove var="titleMessage" scope="session"/>
    <c:remove var="descriptionMessage" scope="session"/>
    <c:remove var="priceMessage" scope="session"/>
    <c:remove var="imageMessage" scope="session"/>
    <c:remove var="modelYearMessage" scope="session"/>
    <c:remove var="inStockMessage" scope="session"/>
    <c:remove var="categoryMessage" scope="session"/>
    <c:remove var="stateMessage" scope="session"/>
</div>
</body>
</html>


