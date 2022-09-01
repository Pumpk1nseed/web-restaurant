<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 31.08.22
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.checkout" var="checkoutFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.finishOrder" var="finishOrdFmt"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.link.Account" var="account_link"/>

<html>
<head>
    <title>${checkoutFmt}</title>
    <link rel="stylesheet" href="css/orderFinish.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="titleFinishOrder"><h1>${checkoutFmt}</h1></div>

<div style="margin-top: 6%">
        ${finishOrdFmt}
</div>

<div class="account"><a href="account"><span>${account_link}</span></a></div>
<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>

</body>
</html>
