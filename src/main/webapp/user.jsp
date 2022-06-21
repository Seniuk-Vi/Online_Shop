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


<%--todo--%>
<div> Your login: ${currentUser.login} </div>
<br>

<div> Your name: ${currentUser.name} </div>
<div><a href="changeField.jsp">Change name ></a></div>
<br>
<div> Your surname: ${currentUser.surname} </div>
<div><a href="changeField.jsp">Change surname ></a></div>
<br>
<div> Your password: ${currentUser.password} </div>
<div><a href="changeField.jsp">Change password ></a></div>
<br>
<div> Your email: ${currentUser.email} </div>
<div><a href="changeField.jsp">Change email ></a></div>
<br>
<div> Your phone number: ${currentUser.phoneNumber} </div>
<div><a href="changeField.jsp">Change phoneNumber ></a></div>
<br>
<a href="userOrders.jsp">Your Orders</a><br>
<a href="controller?command=logout">Logout</a>
${errorMessage}
<c:remove var="errorMessage" scope="session"/>
</body>
</html>


