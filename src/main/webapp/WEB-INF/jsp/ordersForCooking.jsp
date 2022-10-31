<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 22.10.22
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.OrdersForCook" var="ordersForCookFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.paymentMethod" var="paymentMethodFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderStatus" var="orderStatusFmt"/>
<fmt:message bundle="${loc}" key="localization.legend.order" var="orderFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Quantity" var="quantityFmt"/>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${ordersForCookFmt}</title>
    <link rel="stylesheet" href="css/confirmationOfOrders.css">
</head>
<body>
<div class="title"><h1>${ordersForCookFmt}</h1></div>

<div class="wrapper">
    <main class="main">

        <table>
            <div class="thread">
                <th>№</th>
                <th>${orderFmt}</th>
                <th>${paymentMethodFmt}</th>
                <th>${quantityFmt}</th>
                <th>${orderStatusFmt}</th>
            </div>

            <c:forEach items="${ordersForCooking}" var="order">
                <tr>
                    <td>${order.idOrder}</td>
                    <td>${order.dishName}</td>
                    <td>${order.paymentMethod}</td>
                    <td>${order.quantity}</td>
                    <td>
                        <form action="controller" method="post">
                            <input type="hidden" name="command" value="cook_order">
                            <input type="hidden" name="cookedOrderId" value="${order.idOrder}">
                            <input type="submit" value="Cooked &#10004;" id="cookBtn">
                        </form>
                    </td>
                </tr>

            </c:forEach>
        </table>
    </main>
</div>

<a href="account" class="backBtn">${backFmt}</a>
</body>
</html>
