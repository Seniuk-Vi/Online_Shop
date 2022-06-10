<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tables</title>
</head>
<body>
<H1>Tables</H1><br>

<table>
    <c:forEach items="${products}" var="row">
        <tr>
            <td>${row.title}</td>
            <td>${row.price}</td>
            <td>${row.inStock}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
