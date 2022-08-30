<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 18.08.22
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.LoginPh" var="loginPhFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.PasswordPh" var="passwordFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.NamePh" var="nameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.SurnamePh" var="surnameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.LastNamePh" var="lastNameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.PhonePh" var="phoneFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.DateBirthPh" var="dateBirthFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.MailPh" var="mailFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.AddressPh" var="addressFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.RolePh" var="roleFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.RegistrationTitlePh" var="regTitleFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>
<fmt:message bundle="${loc}" key="localization.link.Main" var="main_link"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.link.AboutLower" var="about_link"/>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<html>
<head>
    <title>${regTitleFmt}</title>
    <link href="css/registration.css" rel="stylesheet">
</head>
<body>

<div class="titleReg"><h1>${regTitleFmt}</h1></div>

<form style="margin-top: 6%" action="controller" method="post">
    <input type="hidden" name="command" value="registration"/>
    <input type="text" name="login" placeholder="${loginPhFmt}" value=""/>
    <br>
    <input type="text" name="name" placeholder="${nameFmt}" value=""/>
    <br>
    <input type="text" name="surname" placeholder="${surnameFmt}" value=""/>
    <br>
    <input type="text" name="last_name" placeholder="${lastNameFmt}" value=""/>
    <br>
    <input type="date" name="date_of_birth" placeholder="${dateBirthFmt}" value=""/>
    <br>
    <input type="text" name="telephone_number" placeholder="${phoneFmt}" value=""/>
    <br>
    <input type="text" name="email" placeholder="${mailFmt}" value=""/>
    <br>
    <input type="text" name="address" placeholder="${addressFmt}" value=""/>
    <br>
    <input type="text" name="role" placeholder="${roleFmt}" value=""/>
    <br>
    <input type="password" name="password" placeholder="${passwordFmt}" value=""/>
    <br>
    <input type="submit" value="register"/> <br/>
</form>

<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>
<div class="catharsisinfo"><a href="catharsisinfo"><span>${about_link}</span></a></div>

</body>
</html>