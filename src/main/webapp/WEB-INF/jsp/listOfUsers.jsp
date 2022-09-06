<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 6.09.22
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>
<html>
<head>
    <title>List of users</title>
</head>
<body>
<c:if test="${users == null}">
    <jsp:forward page="controller">
        <jsp:param name="command" value="get_user"/>
    </jsp:forward>
</c:if>

<c:if test="${users.size() > 0}">
    <table>
        <th>№</th>
        <th>name</th>
        <th>surname</th>
        <th>lst name</th>
        <th>address</th>
        <th>telephone</th>
        <th>email</th>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.idUser}</td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.lastName}</td>
                <td>${user.address}</td>
                <td>${user.telephoneNumber}</td>
                <td>${user.email}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
