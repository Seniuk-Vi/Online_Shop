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
        <input name="login" placeholder="login" value="${currentUser.login}"><br>
        <input name="name" placeholder="name" value="${currentUser.name}"><br>
        <input name="surname" placeholder="surname" value="${currentUser.surname}"><br>
        <input  name="email" placeholder="email" value="${currentUser.email}"><br>
        <select name="locale" >
            <option selected value="${currentUser.locale}">${currentUser.locale}</option>
            <option value="en">en</option>
            <option value="uk">uk</option>
        </select>
        <input type="submit" value="change user data"><br>
    </form>

    <a href="controller?command=showUserOrders">Your Orders</a><br>
    <a href="controller?command=logout">Logout</a>
    ${errorMessage}
    <c:remove var="errorMessage" scope="session"/>
</div>
</body>
</html>





