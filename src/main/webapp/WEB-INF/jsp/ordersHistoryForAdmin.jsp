<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 6.09.22
  Time: 23:07
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
<fmt:message bundle="${loc}" key="localization.txt.OrderHist" var="orderHistFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.OrderStatus" var="orderStatusFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.MailPh" var="mailFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>

<!DOCTYPE html>
<html>
<head>
    <title>${orderHistFmt}</title>
    <link rel="stylesheet" href="css/confirmationOfOrders.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<%--<c:if test="${orders == null}">
    <jsp:forward page="controller">
        <jsp:param name="command" value="get_orders"/>
    </jsp:forward>
</c:if>--%>

<div class="title"><h1>${orderHistFmt}</h1></div>

<div class="wrapper">
    <main class="main">
        <table>
                <th>№</th>
                <th>${orderPriceFmt}</th>
                <th>${orderDateFmt}</th>
                <th>${orderStatusFmt}</th>
                <th>${nameFmt}</th>
                <th>${surnameFmt}</th>
                <th>${addressFmt}</th>
                <th>${phoneFmt}</th>

            <c:forEach items="${orders.keySet()}" var="order">
                <tr>
                    <td>${order.idOrder}</td>
                    <td>${order.price} BYN</td>
                    <td>${order.dateTime}</td>
                    <td>${order.status}</td>
                    <td>${orders.get(order).name}</td>
                    <td>${orders.get(order).surname}</td>
                    <td>${orders.get(order).address}</td>
                    <td>${orders.get(order).telephoneNumber}</td>
                </tr>

            </c:forEach>
        </table>
    </main>
</div>

<a href="account" class="backBtn">${backFmt}</a>
</body>
</html>
