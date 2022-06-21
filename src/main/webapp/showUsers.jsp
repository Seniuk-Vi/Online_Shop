<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
    <title>Users</title>
    <%@include file="/jspf/head.jspf" %>
<%--    <c:if test="${empty users}">--%>
<%--        <c:redirect url="controller?command=showUsers"></c:redirect>--%>
<%--    </c:if>--%>
</head>

<body class="main">

<%@include file="/jspf/adminNavbar.jspf" %><br>
<c:if test="${currentUser.role == 1}">
    <%@include file="/jspf/users.jspf" %>
</c:if>

<c:if test="${currentUser.role != 1}">
    <c:redirect url="controller?command=showHomePage"></c:redirect>
</c:if>

</body>
</html>


