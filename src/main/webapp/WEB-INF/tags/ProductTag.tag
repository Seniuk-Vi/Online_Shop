<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag language="java" pageEncoding="ISO-8859-1" %>
<%@ attribute
        name="row"
        type="com.shop.models.entity.Product"
        required="true"
        rtexprvalue="true"
%>
<div class="column">
    <div class="product-card">
        <div class="product-tumb">
            <img src="${pageContext.request.contextPath}/images/${row.imageUrl}" alt="">
        </div>
        <div class="product-details">
            <span class="product-catagory">${row.category}</span>
            <form method="post" action="controller">
                <input type="hidden" name="command" value="showProduct"/>
                <input type="hidden" name="product_id" value="${row.id}"/>
                <a class="ttl-btn" onclick="this.parentNode.submit();"><h4>${row.title}</h4></a>
            </form>
            <p id="desc">${row.description}</p>
            <div class="product-bottom-details">
                <div class="product-price">${row.price}</div>
                <div class="product-links">

                    <form method="post" action="controller">
                        <input type="hidden" name="command" value="addToCart"/>
                        <input type="hidden" name="product_id" value="${row.id}"/>
                        <a onclick="this.parentNode.submit();">buy</a>
                    </form>
                    <c:if test="${currentUser.role == 1}">
                        <span>|</span>
                        <form method="post" action="controller">
                            <input type="hidden" name="command" value="editProductShow"/>
                            <input type="hidden" name="product_id" value="${row.id}"/>
                            <a onclick="this.parentNode.submit();">edit</a>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>