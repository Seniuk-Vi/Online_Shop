<%@include file="/jspf/header.jspf" %>

<html >
<head>
  <title>Home page</title>
  <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">
<div class="container">


  <c:if test="${empty currentUser}">
    <c:redirect url="login.jsp">
    </c:redirect>
  </c:if>

  <c:if test="${currentUser.role == 1}">
    <%@include file="/jspf/adminNavbar.jspf" %>
  </c:if>
  <c:if test="${currentUser.role != 1}">
    <%@include file="/jspf/navbar.jspf" %>
  </c:if>

  ${errorMessage}

<%@include file="/jspf/carts.jspf" %>

  <c:remove var="errorMessage"></c:remove>
</div>
</body>
</html>


