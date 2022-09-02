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
<fmt:message bundle="${loc}" key="localization.txt.OrderDate" var="orderDateFmt"/>

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

    <h1 style="margin-top: 6%">${welcomeFmt}, ${sessionScope.user_info.name}!</h1>


    <table>
        <tr>
            <td>
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
            </td>

            <c:if test="${user.idRole == 1}">
                <td>
                    <div class="historyCategory"><label for="checkHist_label"><strong>History of orders</strong></label>
                    </div>
                    <input type="checkbox" name="orderHist" id="checkHist_label">
                    <div class="blockHist">
                        <form id="getOrderHistory" action="controller" method="get">
                            <input type="hidden" name="command" value="get_history_of_orders">

                            <c:if test="${ordersHistory.size() == 0}">
                                <h1>${orderIsEmptyFmt}</h1>
                            </c:if>

                            <c:if test="${ordersHistory.size() > 0}">
                                <table>
                                    <th>№</th>
                                    <th>${orderPriceFmt}</th>
                                    <th>${orderDateFmt}</th>
                                    <th>${orderStatusFmt}</th>

                                    <c:forEach items="${ordersHistory}" var="order">
                                        <tr>
                                            <td>${order.idOrder}</td>
                                            <td>${order.price} BYN</td>
                                            <td>${order.dateTime}</td>
                                            <td>${order.status}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:if>
                            <input id="getHistBtn" type="submit" value="обновить">
                        </form>
                    </div>
                </td>
            </c:if>
            <c:if test="${user.idRole == 2}">
                <td>


                    <div class="confirmCategory"><label for="checkInP_label"><strong>Confirmation of orders</strong></label>
                    </div>
                    <input type="checkbox" name="orderConfirm" id="checkInP_label">
                    <div class="blockConfirm">
                        <form id="goToConfirm" action="controller" method="get">
                            <input type="hidden" name="command" value="go_to_order_confirmation">
                            <table>
                                <th>№</th>
                                <th>${orderPriceFmt}</th>
                                <th>${orderDateFmt}</th>
                                <th>${nameFmt}</th>
                                <th>${surnameFmt}</th>
                                <th>${addressFmt}</th>
                                <th>${phoneFmt}</th>
                                <th>Confirmation of order</th>

                                <c:forEach items="${ordersForConfirmation.keySet()}" var="order">
                                    <tr>
                                        <td>${order.idOrder}</td>
                                        <td>${order.price} BYN</td>
                                        <td>${order.dateTime}</td>
                                        <td>${ordersForConfirmation.get(order).name}</td>
                                        <td>${ordersForConfirmation.get(order).surname}</td>
                                        <td>${ordersForConfirmation.get(order).address}</td>
                                        <td>${ordersForConfirmation.get(order).telephoneNumber}</td>
                                        <td>
                                            <form action="controller" method="post">
                                                <input type="hidden" name="command" value="confirm_order">
                                                <input type="hidden" name="confirmedOrderId" value="${order.idOrder}">
                                                <input type="submit" value="Confirm &#10004;" id="confirmBtn">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <input id="getInProcessBtn" type="submit" value="обновить">
                        </form>
                    </div>
                </td>
            </c:if>
        </tr>
    </table>


    <div class="basket"><a href="basket"><span>${basket_link}</span></a></div>
    <div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>

    <div>
        <a href="/web_restaurant_war/controller?command=sign_out">
            <img src="images/signOut.png" alt="${signOutFmt}" class="signOut">
        </a>
    </div>

    </body>
    </html>

</c:if>



