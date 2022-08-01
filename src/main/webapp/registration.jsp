<%@include file="/jspf/header.jspf" %>

<html>
<head>
    <title>Sign up</title>
    <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">
<div class="container">

    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <%@include file="/jspf/navbar.jspf" %>

    <form action="controller" method="post">
        <input type="hidden" name="command" value="registration">
        <br>
        <input name="login" placeholder="login" value="${login}" pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d]{2,20}$" required> <span>${loginMessage}</span><br>
        <input name="name" placeholder="name" value="${name}"pattern="[a-zA-Za-ö-w-я]{2,10}$" required><span>${nameMessage}</span><br>
        <input name="surname" placeholder="surname" value="${surname}"pattern="[a-zA-Za-ö-w-я]{2,10}$" required><span>${surnameMessage}</span><br>
        <input type="number" name="phoneNumber" placeholder="phoneNumber" pattern="^[0-9]{8,10}$"value="${phoneNumber}"
               required><span>${phoneNumberMessage}</span><br>
        <input name="email" placeholder="email" value="${email}"pattern="^[\w\.]+@([\w]+\.)+[\w-]{2,3}$" required><span>${emailMessage}</span><br>
        <input type="password" name="password" placeholder="password"pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,25}$" required><span>${passwordMessage}</span><br>
        <select name="locale" required>
            <option value="">choose locale</option>
            <option value="en">en</option>
            <option value="uk">uk</option>
        </select><span>${localeMessage}</span><br>
        <input type="submit" value="sign up"><br>
    </form>
    ${errorMessage}
    <c:remove var="errorMessage" scope="session"/>
    <c:remove var="loginMessage" scope="session"/>
    <c:remove var="nameMessage" scope="session"/>
    <c:remove var="surnameMessage" scope="session"/>
    <c:remove var="phoneNumberMessage" scope="session"/>
    <c:remove var="emailMessage" scope="session"/>
    <c:remove var="passwordMessage" scope="session"/>
    <c:remove var="localeMessage" scope="session"/>
</div>
</body>
</html>



