<%@include file="/jspf/header.jspf" %>

<html>
<head>
  <title>Home page</title>
  <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">
<div class="container">

  <c:if test="${empty orders}">
    <c:if test="${errorMessage != 'No orders yet'}">
      <c:redirect url="controller?command=showOrders"></c:redirect>
    </c:if>
    <c:if test="${errorMessage == 'No orders yet'}">
      ${errorMessage}
    </c:if>
  </c:if>
  <script src="${pageContext.request.contextPath}/js/script.js"></script>

  <c:if test="${currentUser.role == 1}">
    <%@include file="/jspf/adminNavbar.jspf" %>
  </c:if>
  <c:if test="${currentUser.role != 1}">
    <%@include file="/jspf/navbar.jspf" %>
    <br>
  </c:if>
  <br>
  <%@include file="/jspf/allOrders.jspf" %>
  <c:remove var="errorMessage"></c:remove>
</div>
</body>
</html>





