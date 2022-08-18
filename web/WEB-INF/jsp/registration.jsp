<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 18.08.22
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="controller" method="post">
  <input type="hidden" name="command" value="registration"/>
  <input type="text" name="name" placeholder="введите имя" value=""/>
  <br>
  <input type="text" name="login" placeholder="введите логин" value=""/>
  <br>
  <input type="password" name="password" placeholder="введите пароль" value=""/>
  <br>
  <input type="submit" value="register"/> <br/>
</form>
<a href="jspauthorization">back</a>
<br>
<a href="index.jsp">go to main</a>
</body>
</html>
