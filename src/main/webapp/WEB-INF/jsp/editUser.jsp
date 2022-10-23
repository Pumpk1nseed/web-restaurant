<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 4.09.22
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.Name" var="nameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Surname" var="surnameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.LastName" var="lastNameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Phone" var="phoneFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Mail" var="emailFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Address" var="addressFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Change" var="changeFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.ChangePersInfo" var="changePersInfoFmt"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.link.Basket" var="basket_link"/>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<html>
<head>
    <title>Edit info</title>
    <link href="css/editInfo.css" rel="stylesheet">
</head>
<body>

<div class="title"><h1>Edit user</h1></div>

<div class="entity">
    <div class="form-container">
        <form action="controller" method="post">
            <input type="hidden" name="command" value="edit_user">
            <div class="form-field">
                <label for="idEditedUser">№</label>
                <input autofocus id="idEditedUser" class="inputData" type="text" name="idEditedUser"
                       value="${param.idEditedUser}">
            </div>
            <div class="form-field">
                <label for="login">Login</label>
                <input autofocus id="login" class="inputData" type="text" name="login" value="${param.login}">
            </div>
            <div class="form-field">
                <label for="name">${nameFmt} </label>
                <input autofocus id="name" class="inputData" type="text" name="name" value="${param.name}">
            </div>
            <div class="form-field">
                <label for="surname">${surnameFmt} </label>
                <input id="surname" type="text" class="inputData" name="surname" value="${param.surname}">
            </div>
            <div class="form-field">
                <label for="lastname">${lastNameFmt} </label>
                <input id="lastname" type="text" class="inputData" name="last_name" value="${param.last_name}">
            </div>
            <div class="form-field">
                <label for="email">${emailFmt}<span class="required">*</span> </label>
                <input required id="email" type="email" class="inputData" name="email" value="${param.email}"
                       placeholder="example@gmail.com">
            </div>
            <div class="form-field">
                <label for="address">${addressFmt} </label>
                <input id="address" type="text" name="address" class="inputData" value="${param.address}">
            </div>
            <div class="form-field">
                <label for="phonenumber">${phoneFmt}</label>
                <input id="phonenumber" type="tel" class="inputData" name="telephone_number"
                       value="${param.telephone_number}">
            </div>
            <div class="form-field">
                <label for="password">New password</label>
                <input id="password" type="text" class="inputData" name="password" value="${param.password}">
            </div>
            <div class="form-field">
                <label for="role">Role </label>
                <input id="role" type="text" name="role" class="inputData" value="${param.role}">
            </div>
            <input id="login-submit" type="submit" class="submit" value="${changeFmt}">
        </form>
    </div>
</div>

<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

</body>
</html>
