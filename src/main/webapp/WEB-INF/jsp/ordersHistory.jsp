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

<html>
<head>
    <meta charset="UTF-8"/>
    <title>${persInfoFmt}</title>
    <%--    <link href="css/ordersHistory.css" rel="stylesheet">--%>
</head>
<body>

<div class="entry">
    <form id="makeOrder" action="controller" method="get">
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

    </form>
</div>
</body>
</html>

