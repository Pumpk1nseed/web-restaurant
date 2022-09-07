<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 31.08.22
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.basket" var="basketFmt"/>
<fmt:message bundle="${loc}" key="localization.label.yourOrder" var="yourOrder_lbl"/>
<fmt:message bundle="${loc}" key="localization.button.checkout" var="checkout_btn"/>
<fmt:message bundle="${loc}" key="localization.button.cleanCurrentOrder" var="cleanOrder_btn"/>
<fmt:message bundle="${loc}" key="localization.button.orderIsEmpty" var="orderIsEmptyFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.removeDish" var="deletefmt"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.link.Account" var="account_link"/>
<fmt:message bundle="${loc}" key="localization.link.SignIn" var="signIn_link"/>


<html>
<head>
    <meta charset="utf-8">
    <title>${basketFmt}</title>
    <link href="css/basket.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="titleBasket"><h1>${basketFmt}</h1></div>

<c:if test="${order == null}">
    <h3 style="margin-top: 6%">${orderIsEmptyFmt}</h3>
</c:if>

<c:if test="${order != null}">
    <form action="controller" method="post" id="placeOrderForm">
        <input type="hidden" name="command" value="checkout">

        <h1 style="margin-top: 6%; color: darkorange">${yourOrder_lbl}</h1>

        <c:forEach items="${order.getOrderList().keySet()}" var="orderedDish">
            <table>
                <tr>
                    <td colspan="4">
                        <h3 class="DishName">
                            <li/>
                                ${orderedDish.name}
                        </h3>
                    </td>
                </tr>
                <tr>
                    <td>
                            ${orderedDish.description}
                    </td>
                    <td>
                            ${orderedDish.price} BYN
                    </td>
                    <td id="tdWithForm" class="counter">
                        <input type="hidden" name="idDish" value="${orderedDish.idDish}">

                        <button onclick="reduceOne(event)">-</button>
                        <input type="text" name="quantity" class="quantity"
                               value="${order.getOrderList().get(orderedDish)}" required>
                        <button onclick="addOne(event)">+</button>
                    </td>
                    <td>
                        <a href="/web_restaurant_war/controller?command=remove_dish_from_order&&idDish=${orderedDish.idDish}">
                            <img src="images/delete.png" alt="${deletefmt}" class="delete">
                        </a>
                    </td>
                </tr>
            </table>
            <br>
        </c:forEach>

        <br>
        <input type="submit" value="${cleanOrder_btn}" form="cleanOrderForm"
               id="cleanOrderBtn" class="orderBtn">
        <br>
        <hr>
        <input type="submit" value="${checkout_btn}" form="placeOrderForm"
               id="placeOrderBtn" class="orderBtn">
    </form>

    <form action="controller" method="get" id="cleanOrderForm">
        <input type="hidden" name="command" value="delete_order">
    </form>

</c:if>

<c:if test="${order == null}">
    <h1>${orderIsEmptyFm}</h1>
</c:if>

<c:if test="${sessionScope.user != null}">
    <a href="account">
        <p class="signIn">${sessionScope.user.login}</p>
        <img src="images/account.jpeg" аlt="empty account" titlе="user is null" class="account"/>
    </a>
</c:if>

<c:if test="${sessionScope.user == null}">
    <a href="authorization">
        <p class="signIn">${signIn_link}</p>
        <img src="images/account.jpeg" аlt="empty account" titlе="user is null" class="account"/>
    </a>
</c:if>

<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>

</body>

<script src="js/Order/AddRemoveBtn.js"></script>

</html>
