<%@ include file="jspf/header.jspf"%>

<fmt:setLocale value="${param.locale}" scope="session"/>

<fmt:setBundle basename="resources"/>

<c:remove var = "userLocale" scope="session"/>
<c:set var = "currentLocale" value="${param.locale}" scope="session"/>

<c:redirect url="${param.prevPage}"></c:redirect>