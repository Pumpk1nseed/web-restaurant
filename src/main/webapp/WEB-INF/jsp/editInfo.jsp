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
    <title>${changePersInfoFmt}</title>
    <link href="css/editInfo.css" rel="stylesheet">
</head>
<body>

<div class="title"><h1>${changePersInfoFmt}</h1></div>

<div class="entity">
    <div class="register-form-container">
        <form action="controller" method="post">
                <input type="hidden" name="command" value="update_personal_info">
                <div class="form-field">
                    <label for="name">${nameFmt} </label>
                    <input autofocus id="name" class="inputUserData" type="text" name="name" value="${sessionScope.user_info.name}">
                </div>
                <div class="form-field">
                    <label for="surname">${surnameFmt} </label>
                    <input id="surname" type="text" class="inputUserData" name="surname" value="${sessionScope.user_info.surname}">
                </div>
                <div class="form-field">
                    <label for="email">${emailFmt}<span class="required">*</span> </label>
                    <input required id="email" type="email" class="inputUserData" name="email" value="${sessionScope.user_info.email}"
                           placeholder="example@gmail.com">
                </div>
                <div class="form-field">
                    <label for="address">${addressFmt} </label>
                    <input id="address" type="text" name="address" class="inputUserData" value="${sessionScope.user_info.address}">
                </div>
                <div class="form-field">
                    <label for="phonenumber">${phoneFmt} </label>
                    <input id="phonenumber" type="tel" class="inputUserData" name="telephone_number"
                           value="${sessionScope.user_info.telephoneNumber}"
                           placeholder="+375123456789">
                </div>
                <div class="form-field">
                    <label for="password">New password</label>
                    <input id="password" type="password" class="inputUserData" name="password" value=""
                           autocomplete="new-password">

                </div>
                <%--        <p>
                            <label for="confirm-password">Подтвердить</label>
                            <input id="confirm-password" type="password"
                                   name="passwordConfirm" value="">
                        </p>--%>
                <%--        <p style="display: flex;justify-content: flex-start;">
                            <span class="required">*</span>&nbsp- required fields
                        </p>--%>
                <input id="login-submit" type="submit" class="submit" value="${changeFmt}">
        </form>
    </div>
</div>


<c:if test="${user.idRole != 2}">
    <div class="basket"><a href="basket"><span>${basket_link}</span></a></div>
</c:if>
<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

</body>
</html>
