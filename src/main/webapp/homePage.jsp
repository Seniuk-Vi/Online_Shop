<%@include file="/jspf/header.jspf" %>

<html>
<head>
    <title>Home page</title>
    <%@include file="/jspf/head.jspf" %>

</head>

<body class="main">
<div class="container">

    <script src="${pageContext.request.contextPath}/js/script.js"></script>

    <c:if test="${currentUser.role == 1}">
        <%@include file="/jspf/adminNavbar.jspf" %>

        <br>
        <%@include file="/jspf/sort.jspf" %>
        <br>
        <%@include file="/jspf/products.jspf" %>
    </c:if>

    <c:if test="${currentUser.role != 1}">
        <%@include file="/jspf/navbar.jspf" %>
        <br>

        <%@include file="/jspf/sort.jspf" %>
        <br>
        <%@include file="/jspf/products.jspf" %>
    </c:if>

    <nav class="center" aria-label="Navigation for countries">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="showHomePage">
                        <input type="hidden" name="sort" value=${sort}>
                        <input type="hidden" name="category" value=${category}>
                        <input type="hidden" name="priceMin" value="${priceMinS}">
                        <input type="hidden" name="search" value="${search}">
                        <input type="hidden" name="priceMax" value="${priceMaxS}">
                        <input type="hidden" name="tPage" value="${currentPage-1}">
                        <input type="submit" value="Previous">
                    </form>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li>
                            <form>
                                <input type="hidden" name="sort" value=${sort}>
                                <input type="hidden" name="category" value= ${category}>
                                <input type="hidden" name="priceMin" value="${priceMinS}">
                                <input type="hidden" name="priceMax" value="${priceMaxS}">
                                <input type="hidden" name="search" value="${search}">
                                <input type="submit" value=" ${i} (current)">
                            </form>

                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <form action="controller" method="post">
                                <input type="hidden" name="command" value="showHomePage">
                                <input type="hidden" name="sort" value=${sort}>
                                <input type="hidden" name="category" value= ${category}>
                                <input type="hidden" name="priceMin" value="${priceMinS}">
                                <input type="hidden" name="priceMax" value="${priceMaxS}">
                                <input type="hidden" name="search" value="${search}">
                                <input type="hidden" name="tPage" value="${i}">
                                <input type="submit" value="${i}">
                            </form>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item">
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="showHomePage">
                        <input type="hidden" name="sort" value=${sort}>
                        <input type="hidden" name="category" value= ${category}>
                        <input type="hidden" name="priceMin" value="${priceMinS}">
                        <input type="hidden" name="search" value="${search}">
                        <input type="hidden" name="priceMax" value="${priceMaxS}">
                        <input type="hidden" name="tPage" value="${currentPage+1}">
                        <input type="submit" value="Next">
                    </form>
                </li>
            </c:if>
        </ul>
    </nav>

</div>
</body>
</html>


