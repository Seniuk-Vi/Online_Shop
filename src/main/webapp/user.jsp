
<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
    <title>Home page</title>
    <%@include file="/jspf/head.jspf" %>
    <c:if test="${empty currentUser}">
        <c:redirect url="login.jsp"></c:redirect>
    </c:if>
</head>

<body class="main">

<%@include file="/jspf/navbar.jspf" %>
<br>
<div class="container">
<%--todo--%>
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




<a href="userOrders.jsp">Your Orders</a><br>
<a href="controller?command=logout">Logout</a>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</div>
</body>
</html>


