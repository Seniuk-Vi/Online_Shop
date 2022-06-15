<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Tables</title>
</head>
<body>
<H1>Tables</H1><br>

<section.product>
    <h1 class="display-1">Display 1</h1><br>

    <div class="container">
        <c:forEach items="${products}" var="row">
            <div class="row align-items-start">

                <div class="col">
                        ${row.title}
                </div>
                <div class="col">
                        ${row.price}
                </div>
                <div class="col">
                        ${row.inStock}
                </div>
                <div class="col">
                       <a href="controller?command=buy?productId=row.id">buy</a>
                </div>
            </div>
        </c:forEach>
    </div>
</section.product>

</body>
</html>
