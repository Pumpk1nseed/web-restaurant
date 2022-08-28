<%@ page import="by.gaponenko.restaurant.bean.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="local" var="loc"/>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User account</title>
    <link href="css/account.css" rel="stylesheet">
</head>
<body>
<div class="topline">
    <table>
        <tr>
            <td><a href="index.jsp"><img src="images/catharsisblack.png"
                                         аlt="Логотип ресторана черный"
                                         titlе="Наименование catharsis"/></a></td>
            <td><h1>PERSONAL ACCOUNT</h1></td>
        </tr>
    </table>
</div>
<div class="basket"><span>basket</span></div>
<div class="menu"><a href="jspmenu"><span>menu</span></a></div>
<div class="main"><a href="index.jsp"><span>go to main</span></a></div>

<h1 style="margin-top: 6%">Welcome, ${sessionScope.user_info.name}!</h1>

<div class="infocategory"><label for="check_label"><strong>Personal info</strong></label></div>
<input type="checkbox" name="personal_info" id="check_label">
<div class="block">
    <p>login : ${sessionScope.user.login}</p>
    <p>name : ${sessionScope.user_info.name}</p>
    <p>surname : ${sessionScope.user_info.surname}</p>
    <p>last name : ${sessionScope.user_info.lastName}</p>
    <p>email: ${sessionScope.user_info.email}</p>
    <p>address : ${sessionScope.user_info.address}</p>
    <p>phone : ${sessionScope.user_info.telephoneNumber}</p>
</div>

</body>
</html>
