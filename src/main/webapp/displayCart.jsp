<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
  <title>Home page</title>
  <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">
  <c:if test="${empty currentUser}">
    <c:redirect url="login.jsp">
    </c:redirect>
  </c:if>
<%@include file="/jspf/navbar.jspf" %><br>
<%@include file="/jspf/carts.jspf" %>

</body>
</html>


