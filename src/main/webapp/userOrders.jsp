<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
    <title>Home page</title>
    <%@include file="/jspf/head.jspf" %>
</head>
<c:if test="${empty orders}">
    <c:redirect url="controller?command=showUserOrders"></c:redirect>
</c:if>
<body class="main">

<%@include file="/jspf/navbar.jspf" %>
<br>
<%@include file="/jspf/orders.jspf" %>

</body>
</html>


