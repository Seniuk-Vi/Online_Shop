<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<table>
  <c:forEach items="${cart}" var="row">
    <tr>
      <td>${row.orderId}</td>
      <td>${row.productId}</td>
      <td>${row.quantity}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
