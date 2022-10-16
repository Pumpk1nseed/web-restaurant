<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 11.10.22
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.txt.Change" var="changeFmt"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.link.Basket" var="basket_link"/>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<html>

<head>
    <title>Add new dish</title>
  <link href="css/editInfo.css" rel="stylesheet">
</head>
<body>

<div class="title"><h1>Add new dish</h1></div>

<div class="entity">
  <div class="form-container">
    <form action="controller" method="post">
      <input type="hidden" name="command" value="add_dish_to_menu">
      <input id="idCategory" class="inputData" type="hidden" name="idCategory" value="${param.idCategory}">
      <div class="form-field">
        <label for="dishName">Блюдо</label>
        <input autofocus id="dishName" class="inputData" type="text" name="dishName" value="введите название">
      </div>
      <div class="form-field">
        <label for="dishDescription">Описание</label>
        <input id="dishDescription" type="text" class="inputData" name="dishDescription" value="введите вес в граммах">
      </div>
      <div class="form-field">
        <label for="dishPrice">Цена</label>
        <input id="dishPrice" type="text" class="inputData" name="dishPrice" value="введите цену">
      </div>
      <div class="form-field">
        <label for="photoUrl">Фото</label>
        <input id="photoUrl" type="text" class="inputData" name="photoUrl" value="путь к фото">
      </div>
      <input id="login-submit" type="submit" class="submit" value="сохранить">
    </form>
  </div>
</div>


<c:if test="${user.idRole != 2}">
  <div class="basket"><a href="basket"><span>${basket_link}</span></a></div>
</c:if>
<div class="menu"><a href="menu"><span>${menuLower_link}</span></a></div>
<jsp:include page="/WEB-INF/jsp/footer.jsp"/>

</body>
</html>
