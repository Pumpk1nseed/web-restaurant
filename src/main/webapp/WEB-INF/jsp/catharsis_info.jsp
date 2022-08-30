<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 8.08.22
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.RestaurantName" var="restNameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Info" var="infoFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.About" var="aboutFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.About2" var="about2Fmt"/>
<fmt:message bundle="${loc}" key="localization.link.Main" var="main_link"/>

<html>
<head>
    <title>${infoFmt}</title>
</head>
<body>
<h1>${restNameFmt}</h1>
<p><strong>${restNameFmt} - </strong>${aboutFmt}</p>
<br/>
<p>${about2Fmt}</p>

<a href="home">${main_link}</a>
</body>
</html>
