<%@ page import="by.gaponenko.restaurant.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 17.07.22
  Time: 03:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello</h1>
//заменить на jstl
<% by.gaponenko.restaurant.bean.User user;

    user = (User) request.getAttribute("user");

    out.print("Наташ...");
%>
</body>
</html>
