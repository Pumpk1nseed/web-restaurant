<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 1.09.22
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.OrderIsEmpty" var="orderIsEmptyFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderPrice" var="orderPriceFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderStatus" var="orderStatusFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderDate" var="orderDateFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderHist" var="orderHistFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>
<fmt:message bundle="${loc}" key="localization.label.yourOrder" var="yourOrder_lbl"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>${orderHistFmt}</title>
    <link href="css/ordersHistory.css" rel="stylesheet">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="title"><h1>${orderHistFmt}</h1></div>

<div class="wrapper">
    <main class="main">

        <c:if test="${ordersHistory.size() == 0}">
        <h1>${orderIsEmptyFmt}</h1>
        </c:if>

        <c:if test="${ordersHistory.size() > 0}">

        <table>
            <tr class="thread">
                <strong>
                <td>№</td>
                <td>${orderDateFmt}</td>
                <td>${orderStatusFmt}</td>
                <td colspan="4">${yourOrder_lbl}</td>
                <td>${orderPriceFmt}</td>
                </strong>
            </tr>
            <c:forEach items="${ordersHistory}" var="order">
                <tr>
                    <td>${order.idOrder}</td>
                    <td>${order.dateTime}</td>
                    <td>${order.status}</td>
                    <td colspan="4">
                        <table>
                            <c:forEach items="${order.orderList.keySet()}" var="dish">
                                <tr class="dish">
                                    <td class="dish" style="width: 300px">${dish.name}</td>
                                    <td class="dish">${dish.description}</td>
                                    <td class="dish">x ${order.orderList.get(dish)} x</td>
                                    <td class="dish">${dish.price} BYN</td>
                                </tr>
                            </c:forEach>
                        </table>
                    </td>
                    <td>${order.price} BYN</td>
                </tr>
            </c:forEach>
        </table>
        </c:if>
</div>
</main>
</div>

<a href="account" class="backBtn">${backFmt}</a>
</body>
</html>

