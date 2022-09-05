<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 28.08.22
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.ErrorTxt" var="errorTxtFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Error" var="errorFmt"/>

<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>${errorFmt}</h1>
${errorFmt}${errorMsg}
<br>
<br>
<a href="home">на главную</a>
</body>
</html>
