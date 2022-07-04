<%@include file="/jspf/header.jspf" %>
<html>
<head>
    <title>Home page</title>
    <%@include file="/jspf/head.jspf" %>
    <c:if test="${empty products}">
        <c:redirect url="controller?command=showHomePage"></c:redirect>
    </c:if>
</head>
<body class="main">
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<c:if test="${currentUser.role == 1}">
    <%@include file="/jspf/adminNavbar.jspf" %>
</c:if>
<c:if test="${currentUser.role != 1}">
    <%@include file="/jspf/navbar.jspf" %>
</c:if>
<div class="column">
    <div class="product-card">
        <div class="product-tumb">
            <img src="${pageContext.request.contextPath}/images/${row.imageUrl}" alt="">
        </div>
        <div class="product-details">
            <span class="product-catagory">${row.category}</span>
            <h4><a href="">${row.title}</a></h4>
            <p>${row.description}</p>
            <div class="product-bottom-details">
                <div class="product-price">${row.price}</div>
                <div class="product-links">
                    <c:if test="${empty currentUser}">
                        <form method="post" action="controller">
                            <input type="hidden" name="command" value="addToCart"/>
                            <input type="hidden" name="product_id" value="${row.id}"/>
                            <a onclick="this.parentNode.submit();">add to cart</a>
                        </form>
                    </c:if>
                    <c:if test="${currentUser.role == 1}">
                        <form method="post" action="controller">
                            <input type="hidden" name="command" value="editProductShow"/>
                            <input type="hidden" name="product_id" value="${row.id}"/>
                            <a onclick="this.parentNode.submit();">edit</a>
                        </form>
                    </c:if>
                    <c:if test="${currentUser.role == 0}">
                        <form method="post" action="controller">
                            <input type="hidden" name="command" value="addToCart"/>
                            <input type="hidden" name="product_id" value="${row.id}"/>
                            <a onclick="this.parentNode.submit();">add to cart</a>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


