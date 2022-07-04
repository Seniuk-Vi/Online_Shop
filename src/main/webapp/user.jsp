<%@include file="/jspf/header.jspf" %>
<html>
<head>
    <title>User</title>
    <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">
<div class="container">
    <c:if test="${currentUser.role == 1}">
        <%@include file="/jspf/adminNavbar.jspf" %>
        <br>
    </c:if>
    <c:if test="${currentUser.role != 1}">
        <%@include file="/jspf/navbar.jspf" %>
        <br>
    </c:if>
    <form action="controller" method="post">
        <input type="hidden" name = "command" value="updateUserData"><br>
        <input name="login" placeholder="login" value="${currentUser.login}"><span>${loginMessage}</span><br>
        <input name="name" placeholder="name" value="${currentUser.name}"><span>${nameMessage}</span><br>
        <input name="surname" placeholder="surname" value="${currentUser.surname}"><span>${surnameMessage}</span><br>
        <input  name="email" placeholder="email" value="${currentUser.email}"><span>${emailMessage}</span><br>
        <select name="locale" >
            <option selected value="${currentUser.locale}">${currentUser.locale}</option>
            <option value="en">en</option>
            <option value="uk">uk</option>
        </select><span>${localeMessage}</span>
        <input type="submit" value="change user data"><br>
    </form>

    <a href="controller?command=showUserOrders">Your Orders</a><br>
    <a href="controller?command=logout">Logout</a>
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





