<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
    <title>Home page</title>
    <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">

<%@include file="/jspf/navbar.jspf" %>
<br>


<%--todo--%>
<div> order id: ${order.id} </div>
<br>
<div> status: ${order.status} </div>
<div><a href="changeField.jsp">Change status ></a></div>
<br>
<div> order date: ${order.orderDate} </div>
<div><a href="changeField.jsp">Change date ></a></div>
<br>

<form action="controller" method="post">
    <input type="hidden" name="command" value="deleteOrder">
    <input type="hidden" name="order_id" value="${order.id}">
    <input type="submit" value="Delete order">

</form>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>

</body>
</html>


