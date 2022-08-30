<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 16.07.22
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.nameAuth" var="nameAuthFmt" />
<fmt:message bundle="${loc}" key="localization.txt.Welcome" var="welcomeFmt" />
<fmt:message bundle="${loc}" key="localization.txt.LoginPh" var="loginPhFmt" />
<fmt:message bundle="${loc}" key="localization.txt.PasswordPh" var="passwordFmt" />
<fmt:message bundle="${loc}" key="localization.button.SignIn" var="btnSignInFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.NotRegister" var="notRegisterFmt" />
<fmt:message bundle="${loc}" key="localization.link.Registration" var="registration_link"/>


<html>
<head>
    <title>${nameAuthFmt}</title>
    <link href="css/authorization.css" rel="stylesheet">
</head>
<body>
<div class="logo">
    <a href="index.jsp"><img src="images/CATHARSIS.png"
                             аlt="Логотип ресторана белый"
                             titlе="Наименование catharsis"/></a>
</div>
<div class="auth">
    <p><strong>${welcomeFmt}</strong></p>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="authorization"/>
        <input type="text" name="login" placeholder="${loginPhFmt}" value=""/>
        <br>
        <br>
        <input type="password" size="35" name="password" placeholder="${passwordFmt}" value=""/>
        <br>
        <br>
        <input type="submit" value="${btnSignInFmt}"/> <br/>
    </form>
</div>
<div class="registration">
    <p>${notRegisterFmt}
        <br>
        <a href="jspregistration">${registration_link}</a></p>
</div>
</body>
</html>
