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
<fmt:message bundle="${loc}" key="localization.txt.EntName" var="entNameFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.AddDish" var="addDishFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.EntDescr" var="entDescrFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.EntPrice" var="entPriceFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.EntUrl" var="entUrlFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Dish" var="dishFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Description" var="weightFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Price" var="priceFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Photo" var="photoFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.Save" var="saveFmt"/>
<fmt:message bundle="${loc}" key="localization.link.MenuLower" var="menuLower_link"/>
<fmt:message bundle="${loc}" key="localization.link.Basket" var="basket_link"/>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<html>

<head>
    <title>${addDishFmt}</title>
  <link href="css/editInfo.css" rel="stylesheet">
</head>
<body>

<div class="title"><h1>${addDishFmt}</h1></div>

<div class="entity">
  <div class="form-container">
    <form action="controller" method="post">
      <input type="hidden" name="command" value="add_dish_to_menu">
      <input id="idCategory" class="inputData" type="hidden" name="idCategory" value="${param.idCategory}">
      <div class="form-field">
        <label for="dishName">${dishFmt}</label>
        <input autofocus id="dishName" class="inputData" type="text" name="dishName" value="${entNameFmt}">
      </div>
      <div class="form-field">
        <label for="dishDescription">${weightFmt}</label>
        <input id="dishDescription" type="text" class="inputData" name="dishDescription" value="${entDescrFmt}">
      </div>
      <div class="form-field">
        <label for="dishPrice">${priceFmt}</label>
        <input id="dishPrice" type="text" class="inputData" name="dishPrice" value="${entPriceFmt}">
      </div>
      <div class="form-field">
        <label for="photoUrl">${photoFmt}</label>
        <input id="photoUrl" type="text" class="inputData" name="photoUrl" value="${entUrlFmtFmt}">
      </div>
      <input id="login-submit" type="submit" class="submit" value="${saveFmt}">
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
