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
<fmt:message bundle="${loc}" key="localization.link.SignIn" var="signIn_link"/>
<fmt:message bundle="${loc}" key="localization.button.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="localization.button.en" var="en_button"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>

<html>
<head>
    <title>${infoFmt}</title>
    <link href="css/catharsisInfo.css" rel="stylesheet">
</head>
<body>


<%--<h1>${restNameFmt}</h1>--%>
<div class="entity">
<p><strong>${restNameFmt} - </strong>${aboutFmt}</p>
<br/><br/>
<p>${about2Fmt}</p>
</div>

<div class="margo">
<img src="images/chef.png" аlt="Margo" titlе="chef"/>
</div>

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
    <a href="home"><img src="images/CATHARSIS.png"
                        аlt="Логотип ресторана черный"
                        titlе="Наименование catharsis"/></a>
</div>

<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>

<c:if test="${sessionScope.user != null}">
    <a href="account">
        <p class="signIn">${sessionScope.user.login}</p>
    </a>
</c:if>

<c:if test="${sessionScope.user == null}">
    <a href="authorization">
        <p class="signIn">${signIn_link}</p>
    </a>
</c:if>
</body>
</html>
