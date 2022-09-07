<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 4.09.22
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.SuccessEditInfo" var="successEditFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.ChangePersInfo" var="changePersInfoFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>

<html>
<head>
    <title>${changePersInfoFmt}</title>
    <link href="css/error.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="container">
    <h1 style="color: darkorange">${successEditFmt}</h1>
</div>
<a href="account" class="backBtn">${backFmt}</a>
</body>
</html>
