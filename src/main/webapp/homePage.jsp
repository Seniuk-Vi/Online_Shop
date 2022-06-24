<%@include file="/jspf/header.jspf" %>

<html>
<head>
    <title>Home page</title>
    <%@include file="/jspf/head.jspf" %>
<c:if test="${empty products}">
    <c:redirect url="controller?command=showHomePage"></c:redirect>
</c:if>

</head>

<body class="main">

<c:if test="${currentUser.role == 1}">
    <%@include file="/jspf/adminNavbar.jspf" %><br>
    <%@include file="/jspf/products.jspf" %>
</c:if>

<c:if test="${currentUser.role != 1}">
    <%@include file="/jspf/navbar.jspf" %><br>
    <%@include file="/jspf/products.jspf" %>
</c:if>

</body>
</html>


