<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 1.09.22
  Time: 00:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.checkout" var="checkoutFmt"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.link.Account" var="account_link"/>
<fmt:message bundle="${loc}" key="localization.label.yourOrder" var="yourOrder_lbl"/>
<fmt:message bundle="${loc}" key="localization.txt.paymentMethod" var="paymentMethodFmt"/>
<fmt:message bundle="${loc}" key="localization.submit.makeOrder" var="makeOrderFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.needToSign" var="needToSignFmt"/>
<fmt:message bundle="${loc}" key="localization.link.SignIn" var="signIn_link"/>
<fmt:message bundle="${loc}" key="localization.legend.order" var="legOrderFmt"/>
<fmt:message bundle="${loc}" key="localization.label.totalPrice" var="totalPriceFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.AddressPh" var="addressFmt"/>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<c:if test="${user == null}">
    <h3 style="margin-top: 6%">${needToSignFmt}</h3>
    <a href="authorization">${signIn_link}</a>
</c:if>

<html>
<head>
    <meta charset="utf-8">
    <title>${checkoutFmt}</title>
    <link rel="stylesheet" href="css/checkout.css">
</head>
<body>

<div class="titleCheckout"><h1>${yourOrder_lbl}</h1></div>

<c:if test="${user != null}">

    <div style="margin-top: 6%">
        <form id="checkout" action="controller" method="get">
            <input type="hidden" name="command" value="checkout">

            <h2>${paymentMethodFmt}</h2>
            <c:forEach items="${paymentMethods}" var="paymentMethod">
                <div>
                    <label for="${paymentMethod.getName()}">
                            ${paymentMethod.getName()}
                    </label>
                </div>
            </c:forEach>
            <h2>Адрес доставки</h2>
            доставить на адрес: ${sessionScope.user_info.address}
            <br>
            иной адрес:
            <div class="form-field">
                <input type="text" name="address" placeholder="${addressFmt}" value=""/>
            </div>
            <br>

        </form>
    </div>

    <fieldset>
        <legend>${legOrderFmt}</legend>
        <c:forEach items="${order.getOrderList().keySet()}" var="orderedDish">
            <label class="dishName">${orderedDish.name}
                x${order.getOrderList().get(orderedDish)}</label>
            <label class="dishPrice">${orderedDish.price}</label>
            <br>
        </c:forEach>
        <hr>
        <label>${totalPriceFmt}: ${order.getPrice()}</label>
    </fieldset>

    <input id="makeOrderBtn" type="submit" value="${makeOrderFmt}">

</c:if>

<div class="account"><a href="account"><span>${account_link}</span></a></div>
<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>

</body>
</html>
