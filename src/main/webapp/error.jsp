<%--
  Created by IntelliJ IDEA.
  User: lepre
  Date: 09.06.2022
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    ${errorCode}
    ${errorMessage}
    ${fatalError}
    <c:remove var="errorCode" scope="session"/>
    <c:remove var="errorMessage" scope="session"/>
    <c:remove var="fatalError" scope="session"/>
</body>
</html>
