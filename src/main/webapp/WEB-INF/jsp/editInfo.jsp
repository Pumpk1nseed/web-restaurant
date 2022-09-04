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
<html>
<head>
    <title>Updating personal info</title>
</head>
<body>
<form action="controller" method="post">
    <fieldset>
        <input type="hidden" name="command" value="update_personal_info">
        <legend>Updating personal info</legend>
        <p>
            <label for="name">${nameFmt} </label>
            <input autofocus id="name" type="text" name="name" value="${sessionScope.user_info.name}">
        </p>
        <p>
            <label for="surname">${surnameFmt} </label>
            <input id="surname" type="text" name="surname" value="${sessionScope.user_info.surname}">
        </p>
        <p>
            <label for="email">${emailFmt}<span class="required">*</span> </label>
            <input required id="email" type="email" name="email" value="${sessionScope.user_info.email}"
                   placeholder="example@gmail.com">
        </p>
        <p>
            <label for="address">${addressFmt} </label>
            <input id="address" type="text" name="address" value="${sessionScope.user_info.address}">
        </p>
        <p>
            <label for="phonenumber">${phoneFmt} </label>
            <input id="phonenumber" type="tel" name="telephone_number" value="${sessionScope.user_info.telephoneNumber}"
                   placeholder="+375123456789">
        </p>

        <p>
            <label for="password">New password</label>
            <input id="password" type="password" name="password" value=""
                   autocomplete="new-password">

        </p>
        <p>
            <label for="confirm-password">Подтвердить</label>
            <input id="confirm-password" type="password"
                   name="passwordConfirm" value="">
        </p>
        <p style="display: flex;justify-content: flex-start;">
            <span class="required">*</span>&nbsp- required fields
        </p>
        <input id="login-submit" type="submit" value="Change information">
    </fieldset>
</form>
</div>
</body>
</html>
