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

        <%--    <h1 style="margin-top: 6%">${welcomeFmt}, ${sessionScope.user_info.name}!</h1>--%>
    <table>
        <tr>
            <td>
                <div class="dashboard_cards">
                    <div class="personal_info">
                        <div class="header">${persInfoFmt}</div>
                        <div class="personal_card">
                            <div class="form-field">
                                <p>${loginFmt} : ${sessionScope.user.login}</p>
                            </div>
                            <div class="form-field">
                                <p id="name">${nameFmt} : ${sessionScope.user_info.name}</p>
                            </div>
                            <div class="form-field">
                                <p>${surnameFmt} : ${sessionScope.user_info.surname}</p>
                            </div>
                            <div class="form-field">
                                <p>${lastNameFmt} : ${sessionScope.user_info.lastName}</p>
                            </div>
                            <div class="form-field">
                                <p>${dateBirthFmt} : ${sessionScope.user_info.dateOfBirth}</p>
                            </div>
                            <div class="form-field">
                                <p>${mailFmt}: ${sessionScope.user_info.email}</p>
                            </div>
                            <div class="form-field">
                                <p>${addressFmt} : ${sessionScope.user_info.address}</p>
                            </div>
                            <div class="form-field">
                                <p>${phoneFmt} : ${sessionScope.user_info.telephoneNumber}</p>
                            </div>
                            <a href="editInfo">Update info</a>
                        </div>
                    </div>
                </div>
            </td>


                <%--            <td>
                                <div class="infoCategory"><label for="check_label"><strong>${persInfoFmt}</strong></label></div>
                                <input type="checkbox" name="personal_info" id="check_label">
                                <div class="blockCat">
                                    <p>${loginFmt} : ${sessionScope.user.login}</p>
                                    <p>${nameFmt} : ${sessionScope.user_info.name}</p>
                                    <p>${surnameFmt} : ${sessionScope.user_info.surname}</p>
                                    <p>${lastNameFmt} : ${sessionScope.user_info.lastName}</p>
                                    <p>${dateBirthFmt} : ${sessionScope.user_info.dateOfBirth}</p>
                                    <p>${mailFmt}: ${sessionScope.user_info.email}</p>
                                    <p>${addressFmt} : ${sessionScope.user_info.address}</p>
                                    <p>${phoneFmt} : ${sessionScope.user_info.telephoneNumber}</p>
                                </div>
                            </td>--%>

                <%--            <c:if test="${user.idRole == 1}">--%>
            <td>
                <div class="historyCategory"><label for="checkHist_label"><strong>${histTitleFmt}</strong></label>
                </div>
                <input type="checkbox" name="orderHist" id="checkHist_label">
                <div class="blockHist">
                    <c:if test="${user.idRole == 2}">
                        <a href="ordersHistoryForAdmin">${histTitleFmt}</a>
                        <br>
                        <a href="listOfUsers">Users</a>
                    </c:if>
                    <c:if test="${user.idRole != 2}">
                        <a href="ordersHistory">${histTitleFmt}</a>>
                    </c:if>
                </div>
            </td>
                <%--            </c:if>--%>
            <c:if test="${user.idRole == 2}">
                <td>


                    <div class="confirmCategory"><label for="checkInP_label"><strong>Confirmation of
                        orders</strong></label>
                    </div>
                    <input type="checkbox" name="orderConfirm" id="checkInP_label">
                    <div class="blockConfirm">
                        <a href="/web_restaurant_war/controller?command=go_to_order_confirmation">Orders for
                            confirmation</a>
                    </div>
                </td>
            </c:if>
        </tr>
    </table>

    <c:if test="${user.idRole != 2}">
        <div class="basket"><a href="basket"><span>${basket_link}</span></a></div>
    </c:if>
    <div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>

    <div>
        <a href="/web_restaurant_war/controller?command=sign_out">
            <img src="images/signOut.png" alt="${signOutFmt}" class="signOut">
        </a>
    </div>

    </body>
    </html>

</c:if>



