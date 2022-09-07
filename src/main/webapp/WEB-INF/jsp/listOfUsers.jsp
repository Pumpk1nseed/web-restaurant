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

<fmt:message bundle="${loc}" key="localization.txt.Name" var="nameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Surname" var="surnameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Address" var="addressFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Phone" var="phoneFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.MailPh" var="mailFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.LastName" var="lastNameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Role" var="roleFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.DateBirthPh" var="dateBirthFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.ListUsers" var="listUsersFmt"/>

<html>
<head>
    <title>${listUsersFmt}</title>
    <link rel="stylesheet" href="css/confirmationOfOrders.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<c:if test="${users == null}">
    <jsp:forward page="controller">
        <jsp:param name="command" value="get_user"/>
    </jsp:forward>
</c:if>

<c:if test="${users.size() > 0}">
    <div class="wrapper">
        <main class="main">
            <table>
                <th>№</th>
                <th>${nameFmt}</th>
                <th>${surnameFmt}</th>
                <th>${lastNameFmt}</th>
                <th>${addressFmt}</th>
                <th>${phoneFmt}</th>
                <th>${mailFmt}</th>
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
        </main>
    </div>

</c:if>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
