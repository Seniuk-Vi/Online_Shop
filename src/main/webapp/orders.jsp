<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
  <title>All orders</title>
  <%@include file="/jspf/head.jspf" %>
</head>
<c:if test="${empty orders}">
  <c:redirect url="controller?command=showOrders"></c:redirect>
</c:if>
<body class="main">

<%@include file="/jspf/navbar.jspf" %>
<br>
<%@include file="/jspf/allOrders.jspf" %>

</body>
</html>


