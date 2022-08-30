<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.Login" var="loginFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Name" var="nameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Surname" var="surnameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.LastName" var="lastNameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Phone" var="phoneFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.DateBirthPh" var="dateBirthFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Mail" var="mailFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Address" var="addressFmt"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.txt.Welcome" var="welcomeFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.PersonalInfoTitle" var="persInfoFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.PersonalAccountTitle" var="persAccountFmt"/>
<fmt:message bundle="${loc}" key="localization.link.Basket" var="basket_link"/>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>${persInfoFmt}</title>
    <link href="css/account.css" rel="stylesheet">
</head>
<body>

<div class="titleAccount"><h1>${persAccountFmt}</h1></div>

<h1 style="margin-top: 6%">${welcomeFmt}, ${sessionScope.user_info.name}!</h1>

<div class="infocategory"><label for="check_label"><strong>${persInfoFmt}</strong></label></div>
<input type="checkbox" name="personal_info" id="check_label">
<div class="block">
    <p>${loginFmt} : ${sessionScope.user.login}</p>
    <p>${nameFmt} : ${sessionScope.user_info.name}</p>
    <p>${surnameFmt} : ${sessionScope.user_info.surname}</p>
    <p>${lastNameFmt} : ${sessionScope.user_info.lastName}</p>
    <p>${dateBirthFmt} : ${sessionScope.user_info.DateOfBirth}</p>
    <p>${mailFmt}: ${sessionScope.user_info.email}</p>
    <p>${addressFmt} : ${sessionScope.user_info.address}</p>
    <p>${phoneFmt} : ${sessionScope.user_info.telephoneNumber}</p>
</div>

<div class="basket"><span>${basket_link}</span></div>
<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>

</body>
</html>
