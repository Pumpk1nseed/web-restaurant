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
<fmt:message bundle="${loc}" key="localization.txt.BackPh" var="backFmt"/>

<html>
<head>
    <title>${listUsersFmt}</title>
    <link rel="stylesheet" href="css/confirmationOfOrders.css">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/header.jsp"/>
<div class="title"><h1>List of Users</h1></div>
<%--<c:if test="${users == null}">
    <jsp:forward page="controller">
        <jsp:param name="command" value="get_user"/>
    </jsp:forward>
</c:if>--%>
<c:if test="${users.size() > 0}">
    <div class="wrapper">
        <main class="main">
            <a href="addNewUser" class="addNewUser"> <img src="images/add.png" style="width: 50px; margin-left: 0px"></a>
            <br>
            <table>
                <th>№</th>
                <th>login</th>
                <th>${nameFmt}</th>
                <th>${surnameFmt}</th>
                <th>${lastNameFmt}</th>
                <th>${addressFmt}</th>
                <th>${phoneFmt}</th>
                <th>${mailFmt}</th>
                <th>role</th>
                <th>Edit user</th>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td>${user.idUser}</td>
                        <td>${user.login}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.lastName}</td>
                        <td>${user.address}</td>
                        <td>${user.telephoneNumber}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                        <td>
                            <a href="editUser?idEditedUser=${user.idUser}&name=${user.name}&surname=${user.surname}&last_name=${user.lastName}&address=${user.address}&telephone_number=${user.telephoneNumber}&email=${user.email}&role=${user.role}&login=${user.login}&password=${user.password}"
                               class="editUser"> <img src="images/edit.png" style="width: 20px"
                                                      class="imgInTd"></a>
                            <a href="/web_restaurant_war/controller?command=remove_user&&idUserToRemove=${user.idUser}">
                                <img src="images/delete.png" alt="${deletefmt}" style="width: 20px">
                            </a>

                        </td>
                    </tr>
                </c:forEach>
            </table>
        </main>
    </div>

</c:if>
<a href="account" class="backBtn">${backFmt}</a>
</body>
</html>
