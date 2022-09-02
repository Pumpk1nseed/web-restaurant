<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 1.09.22
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.Name" var="nameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Surname" var="surnameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Address" var="addressFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Phone" var="phoneFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderPrice" var="orderPriceFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderDate" var="orderDateFmt"/>

<!DOCTYPE html>
<html>
<head>
    <title>Подтверждение заказов</title>
</head>
<body>

<table>
    <caption>
        Confirmation of orders
    </caption>
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
            <td>${ordersForConfirmation.get(order).telephoneNumber}</td>
            <td>${ordersForConfirmation.get(order).email}</td>
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

</body>
</html>
