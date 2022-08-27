<%@ page import="by.gaponenko.restaurant.bean.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 17.07.22
  Time: 03:11
  To change this template use File | Settings | File Templates.
--%>
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

<h1 style="margin-top: 6%">Hello</h1>

<% by.gaponenko.restaurant.bean.User user;

    user = (User) request.getAttribute("user");

    out.print("Наташ...");
%>

<c: out value="Hello with jstl"/>

</body>
</html>
