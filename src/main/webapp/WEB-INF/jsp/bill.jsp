<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 14.11.22
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Bill</title>
</head>
<body>

<c:if test="${orderForBill == null}">
    <jsp:forward page="controller">
        <jsp:param name="command" value="create_bill" />
    </jsp:forward>
</c:if>

<c:forEach items="${orderForBill.keySet()}" var="order">
    <table>
        <td>
            <tr>счет № ${idBill}</tr>
            <tr>price: ${order.price}</tr>
            <tr>
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="pay_bill">
                    <input type="hidden" name="idBill" value="${idBill}">
                    <input type="submit" value="Pay &#10004;" id="payBtn">
                </form>
            </tr>
        </td>
    </table>
</c:forEach>
</body>
</html>
