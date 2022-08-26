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
  <input type="text" name="login" placeholder="введите логин (никнейм)" value=""/>
  <br>
  <input type="text" name="name" placeholder="введите имя" value=""/>
  <br>
  <input type="text" name="surname" placeholder="введите фамилию" value=""/>
  <br>
  <input type="text" name="last_name" placeholder="введите отчество" value=""/>
  <br>
  <input type="date" name="date_of_birth" placeholder="укажите дату рождения" value=""/>
  <br>
  <input type="text" name="telephone_number" placeholder="введите номер телефона" value=""/>
  <br>
  <input type="text" name="email" placeholder="введите почту" value=""/>
  <br>
  <input type="text" name="address" placeholder="введите адрес" value=""/>
  <br>
  <input type="text" name="role" placeholder="введите роль" value=""/>
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