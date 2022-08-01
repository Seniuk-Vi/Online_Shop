<%@include file="/jspf/header.jspf" %>
<html>
<head>
    <title>Category</title>
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
    <form action="controller" method="post">
        <input type="hidden" name = "command" value="addCategory"><br>
        <input name="category" maxlength="10" minlength="2" pattern="[a-zA-Za-ö-w-я]{2,10}$" placeholder="name of category"><br>
        <input type="submit" value="Add category"><br>
    </form>
    ${categoryMessage}
    <c:remove var="categoryMessage" scope="session"/>
</div>
</body>
</html>


