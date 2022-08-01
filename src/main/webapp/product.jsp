<%@include file="/jspf/header.jspf" %>
<html>
<head>
    <title>${showProduct.title}</title>
    <%@include file="/jspf/head.jspf" %>
    <c:if test="${empty products}">
        <c:redirect url="controller?command=showHomePage"></c:redirect>
    </c:if>
</head>
<body class="main">
<div class="container">
    <c:if test="${empty showProduct}">
        <c:redirect url="controller?command=showHomePage"></c:redirect>
    </c:if>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <c:if test="${currentUser.role == 1}">
        <%@include file="/jspf/adminNavbar.jspf" %>
    </c:if>
    <c:if test="${currentUser.role != 1}">
        <%@include file="/jspf/navbar.jspf" %>
    </c:if>
    <div class="containerP">
        <!-- Left Column / Headphones Image -->
        <div class="left-column">
            <img data-image="black" src="${pageContext.request.contextPath}/images/${showProduct.imageUrl}" alt="">
        </div>

        <!-- Right Column -->
        <div class="right-column">

            <!-- Product Description -->
            <div class="product-description">
                <span>${showProduct.category}</span>
                <h1>${showProduct.title}</h1>
                <span>Condition: ${showProduct.condition}</span>
                <span> | </span>
                <span>Model year: ${showProduct.modelYear}</span><br>
                <span>In stock: ${showProduct.inStock}</span>
                <p>${showProduct.description}</p>
            </div>
            <!-- Product Pricing -->
            <div class="product-price">
                <span>${showProduct.price}</span>
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="addToCart"/>
                    <input type="hidden" name="product_id" value="${showProduct.id}"/>
                    <a class="cart-btn" onclick="this.parentNode.submit();">add to cart</a>
                </form>
            </div>
        </div>


    </div>
</div>
</body>
</html>


