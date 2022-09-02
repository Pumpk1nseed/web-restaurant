<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 28.08.22
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.link.Account" var="account_link"/>
<fmt:message bundle="${loc}" key="localization.link.Main" var="main_link"/>
<fmt:message bundle="${loc}" key="localization.link.Menu" var="menu_link"/>
<fmt:message bundle="${loc}" key="localization.button.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="localization.button.en" var="en_button"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>${Catharsis}</title>
    <link href="css/header.css" rel="stylesheet">
</head>
<body>

<div class="lng">
    <form action="controller" method="get">
        <input type="hidden" name="command" value="change_localization">
        <input type="hidden" name="localization" value="en">
        <input type="submit" value="${en_button}" class="localeBtnEn">
    </form>
    <form action="controller" method="get">
        <input type="hidden" name="command" value="change_localization">
        <input type="hidden" name="localization" value="ru">
        <input type="submit" value="${ru_button}" class="localeBtnRu">
    </form>
</div>

<div class="topline">
    <a href="home"><img src="images/catharsisblack.png"
                        аlt="Логотип ресторана черный"
                        titlе="Наименование catharsis"/></a>
</div>
<c:if test="${user.idRole != 2}">
    <div class="main"><a href="home"><span>${main_link}</span></a></div>
</c:if>
</body>
</html>

