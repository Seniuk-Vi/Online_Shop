<%@include file="/jspf/header.jspf" %>

<html>
<head>
    <title>Login</title>
    <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">
<div class="container">

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <%@include file="/jspf/navbar.jspf" %>

    <form action="controller" method="post" onsubmit="return validate()">
        <input type="hidden" name="command" value="login">
        <input id="login" type="text" placeholder="Enter Login" value="login" name="login" required>
        <input id="password" type="password" placeholder="Enter Password" value="loginlogin" name="password" required>
        <button type="submit" class="btn">Login</button>
    </form>

    <form action="controller" method="post">
        <input type="hidden" name="command" value="login">
        <input name="login" placeholder="password" value="loginlogin">
        <input type="password" name="password" placeholder="password" value="loginlogin">
        <input type="submit" value="Login">
    </form>

    ${errorMessage}
    <c:remove var="errorMessage" scope="session"/>


</div>
</body>
</html>


