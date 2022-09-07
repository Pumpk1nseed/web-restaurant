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
<fmt:message bundle="${loc}" key="localization.txt.SignOut" var="signOutFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderIsEmpty" var="orderIsEmptyFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderPrice" var="orderPriceFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderStatus" var="orderStatusFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.HistTitle" var="histTitleFmt"/>
<fmt:message bundle="${loc}" key="localization.button.UpdateBtn" var="updateBtn"/>
<fmt:message bundle="${loc}" key="localization.txt.Change" var="changeFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrdersForConf" var="ordersForConfFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.UsersInfo" var="usersInfoFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.ChangePersInfo" var="changePersInfoFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>

<c:if test="${user == null}">
    <jsp:include page="/WEB-INF/jsp/authorization.jsp"/>
</c:if>

<c:if test="${user != null}">

    <jsp:include page="/WEB-INF/jsp/header.jsp"/>

    <html>
    <head>
        <meta charset="UTF-8"/>
        <title>${persInfoFmt}</title>
        <link href="css/account.css" rel="stylesheet">
    </head>
    <body>

    <div class="titleAccount"><h1>${persAccountFmt}</h1></div>

    <table style="margin-top: 7%">
        <tr>
            <td rowspan="2">
                <div class="personal_card">
                    <p style="font-size: 20px"><strong>${persInfoFmt}</strong></p>
                    <div class="form-field">
                        <p>${loginFmt}: ${sessionScope.user.login}</p>
                    </div>
                    <div class="form-field">
                        <p id="name">${nameFmt}: ${sessionScope.user_info.name}</p>
                    </div>
                    <div class="form-field">
                        <p>${surnameFmt}: ${sessionScope.user_info.surname}</p>
                    </div>
                    <div class="form-field">
                        <p>${lastNameFmt}: ${sessionScope.user_info.lastName}</p>
                    </div>
                    <div class="form-field">
                        <p>${dateBirthFmt}: ${sessionScope.user_info.dateOfBirth}</p>
                    </div>
                    <div class="form-field">
                        <p>${mailFmt}: ${sessionScope.user_info.email}</p>
                    </div>
                    <div class="form-field">
                        <p>${addressFmt}: ${sessionScope.user_info.address}</p>
                    </div>
                    <div class="form-field">
                        <p>${phoneFmt}: ${sessionScope.user_info.telephoneNumber}</p>
                    </div>
                    <c:if test="${user.idRole == 2}">
                        <a href="editInfo" class="editInfo"> ${changeFmt}</a>
                    </c:if>
                </div>
            </td>
            <td class="card">
                <c:if test="${user.idRole == 2}">
                    <a href="/web_restaurant_war/controller?command=go_to_order_confirmation"><strong>${ordersForConfFmt}</strong></a>
                </c:if>
                <c:if test="${user.idRole != 2}">
                    <a href="/web_restaurant_war/controller?command=get_history_of_orders"><strong>${histTitleFmt}</strong></a>
<%--                    <a href="ordersHistory"><strong>${histTitleFmt}</strong></a>--%>
                </c:if>

            </td>
            <td class="card">
                <p>
                    <c:if test="${user.idRole == 2}">
                        <a href="/web_restaurant_war/controller?command=get_orders"><strong>${histTitleFmt}</strong></a>
                    </c:if>
                    <c:if test="${user.idRole != 2}">
                        <a href="basket"><strong>${basket_link}</strong></a>
                    </c:if>
                </p>
            </td>
        </tr>
        <tr>
            <td class="card">
                <c:if test="${user.idRole == 2}">
                    <a href="/web_restaurant_war/controller?command=get_user"><strong>${usersInfoFmt}</strong></a>
                </c:if>
                <c:if test="${user.idRole != 2}">
                    <a href="editInfo"><strong>${changePersInfoFmt}</strong></a>
                </c:if>
            </td>
            <td class="card">
                <a href="menu"><strong>${menuLower_link}</strong></a>
            </td>
        </tr>
    </table>

    <div>
        <a href="/web_restaurant_war/controller?command=sign_out">
            <img src="images/signOut.png" alt="${signOutFmt}" class="signOut">
        </a>
    </div>

    </body>

    <a href="account" class="backBtn">${backFmt}</a>

    </html>

</c:if>



