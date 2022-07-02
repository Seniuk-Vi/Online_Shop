<%@include file="/jspf/header.jspf" %>

<html lang="en">
<head>
    <title>Main</title>
    <%@include file="/jspf/head.jspf" %>

</head>
<body class="main">
<div class="container">
    <c:if test="${currentUser.role == 1}">
        <%@include file="/jspf/adminNavbar.jspf" %>

    </c:if>

    <c:if test="${currentUser.role != 1}">
        <%@include file="/jspf/navbar.jspf" %>
    </c:if>



<div class="intro">
    <div class="container">
        <div class="intro_inner">
            <h1 class="intro_title">Let's see cats!</h1>
            <a class="intro_btn" href="controller?command=showHomePage"><span class="flip">go home page</span></a>
        </div>
    </div>
</div>



</div>


</body>
</html>
