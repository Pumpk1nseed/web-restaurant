<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 3.07.22
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.link.Menu" var="menu_link"/>
<fmt:message bundle="${loc}" key="localization.link.SignIn" var="signIn_link"/>
<fmt:message bundle="${loc}" key="localization.txt.RestaurantName" var="restNameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.WorkingMode1" var="workingMode1Fmt"/>
<fmt:message bundle="${loc}" key="localization.txt.WorkingMode2" var="workingMode2Fmt"/>
<fmt:message bundle="${loc}" key="localization.link.About" var="about_link"/>
<fmt:message bundle="${loc}" key="localization.button.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="localization.button.en" var="en_button"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>${restNameFmt}</title>
    <link href="css/main.css" rel="stylesheet">
</head>
<body>

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

<div class="logo">
    <img src="images/CATHARSIS.png" аlt="Логотип ресторана" titlе="Наименование catharsis"/>
    <table>
        <tr>
            <td style="width: 270px"><span>
                ${workingMode1Fmt} 12:00-00:00
            </span></td>
            <td><span>
                +375(29)314-66-52
            </span></td>
        </tr>
    </table>
    <p><span>
        &nbsp${workingMode2Fmt} 12:00-02:00
    </span></p>
</div>
<div class="linkauth">
    <a href="authorization">
        <span style="font-size: medium ">
            ${signIn_link}
        </span>
    </a>
</div>
<div class="linkmenu">
    <a href="menu">
        <span style="font-size: medium ">
            ${menu_link}
        </span>
    </a>
</div>
<div class="linkabout">
    <a href="catharsisinfo">
        <span style="font-size: medium ">
            ${about_link}
        </span>
    </a>
</div>
<hr/>
<a href="https://www.instagram.com/natashagaponenko/">
    <img height="60px" src="images/instagram.webp" аlt="Инстаграм" titlе="Instagram"/></a>
<a href="https://https://t.me/gap0sha">
    <img height="50px" src="images/telegramorange.png" аlt="Телеграм" titlе="telegram"/></a>
<a href="https://www.facebook.com/natasha.gaponenko">
    <img height="60px" src="images/facebook-logo-white.png" аlt="Фейсбук" titlе="facebook"/></a>
<a href="https://https://twitter.com/nataska6980t100">
    <img height="50px" src="images/twitter.png" аlt="Твиттер" titlе="twitter"/></a>
</body>
</html>
