<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 7.09.22
  Time: 02:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>

<html>
<head>
    <title>Footer</title>
    <link rel="stylesheet" href="css/footer.css">
</head>
<body>
<input class="backBtn" type="button" onclick="history.back();" value="${backFmt}"/>
</body>
</html>
