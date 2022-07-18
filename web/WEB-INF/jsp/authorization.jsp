<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 16.07.22
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="utf-8" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name="command" value="authorization"/>
    <input type="text" name="login" value=""/>
    <input type="password" name="password" value=""/>
    <input type="submit" value="sign in"/> <br/>
</form>
<a href="../../index.jsp">go to main</a>
</body>
</html>
