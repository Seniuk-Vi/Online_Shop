<%@include file="/jspf/header.jspf" %>

<html>
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
            <h1 class="intro_title">Discover products!</h1>
            <a class="intro_btn" href="controller?command=showHomePage"><span class="flip">go</span></a>
        </div>
    </div>
    <p><fmt:message key="index_jsp.link.settings"/> </p>


    ${userLocale}

</div>
</div>
</body>
</html>
