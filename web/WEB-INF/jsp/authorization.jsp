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
    <link href="css/authorization.css" rel="stylesheet">
</head>
<body>
<div class="logo">
    <a href="index.jsp"><img src="images/CATHARSIS.png"
                             аlt="Логотип ресторана белый"
                             titlе="Наименование catharsis"/></a>
</div>
<div class="auth">
    <p><strong>WELCOME</strong></p>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="authorization"/>
        <input type="text" name="login" placeholder="введите логин" value=""/>
        <br>
        <br>
        <input type="password" size="35" name="password" placeholder="введите пароль" value=""/>
        <br>
        <br>
        <input type="submit" value="sign in"/> <br/>
    </form>
</div>
<div class="registration">
    <p>Еще не зарегистрированы?
        <br>
        <a href="jspregistration">registration</a></p>
</div>
</body>
</html>
