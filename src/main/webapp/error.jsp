<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>Error</h1>
<c:if test="${empty errorCode}">
    404
</c:if>
${errorCode}
${errorMessage}
${fatalError}

<c:remove var="errorCode" scope="session"/>
<c:remove var="errorMessage" scope="session"/>
<c:remove var="fatalError" scope="session"/>
</body>
</html>
