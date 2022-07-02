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

    <form action="controller" method="post">
        <input type="hidden" name = "command" value="registration"><br>
        <input name="login" placeholder="login" value="${login}" required><br>
        <input name="name" placeholder="name"value="${name}" required><br>
        <input name="surname" placeholder="surname"value="${surname}" required><br>
        <input type="number" name="phoneNumber" placeholder="phoneNumber"value="${phoneNumber}" required><br>
        <input  name="email" placeholder="email"value="${email}" required><br>
        <input type="password" name = "password" placeholder="password" required><br>
        <select name="locale" required>
            <option value="">choose locale</option>
            <option value="en">en</option>
            <option value="uk">uk</option>
        </select>
        <input type="submit" value="Login"><br>
    </form>
    ${errorMessage}
    <c:remove var="errorMessage" scope="session"/>




</div>
</body>
</html>



