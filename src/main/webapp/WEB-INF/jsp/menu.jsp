<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.link.Menu" var="menu_link"/>

<link href="css/menu.css" rel="stylesheet">

<h1>${menu_link}</h1>

<c:if test="${menu.dishes == null}">
    <jsp:forward page="/controller">
        <jsp:param name="command" value="get_menu"/>
    </jsp:forward>
</c:if>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>Header</title>
    <link href="css/header.css" rel="stylesheet">
</head>
<body>
<div class="topline">
    <table>
        <tr>
            <td><a href="index.jsp"><img src="images/catharsisblack.png"
                                         аlt="Логотип ресторана черный"
                                         titlе="Наименование catharsis"/></a></td>
            <td><h1>MENU</h1></td>
        </tr>
    </table>
</div>
<div class="account"><span>account</span></div>
<div class="basket"><span>basket</span></div>
<div class="main"><a href="index.jsp"><span>go to main</span></a></div>
</body>
</html>

<div class="entity">

    <c:forEach items="${dish_categories}" var="category">
        <hr/>
        <h1 id="category${category.idCategory}">
                ${category.name}
        </h1>
        <hr/>

        <div class="menu">
            <table>

                <tr id="imagesRow">
                    <c:forEach items="${menu.getDishes()}" var="dish">
                        <c:if test="${dish.idCategory == category.idCategory}">
                            <td>
                                <img id="photoUrl${dish.idDish}_${category.idCategory}" src="${dish.photoUrl}"
                                     alt="photo of ${dish.name}" class="dish">
                            </td>
                        </c:if>
                    </c:forEach>
                </tr>

                <tr id="namesRow">
                    <c:forEach items="${menu.getDishes()}" var="dish">
                        <c:if test="${dish.idCategory == category.idCategory}">
                            <td><strong id="name${dish.idDish}_${category.idCategory}">${dish.name}</strong></td>
                        </c:if>
                    </c:forEach>
                </tr>

                <tr id="descriptionsRow">
                    <c:forEach items="${menu.getDishes()}" var="dish">
                        <c:if test="${dish.idCategory == category.idCategory}">
                            <td id="description${dish.idDish}_${category.idCategory}">${dish.description}</td>
                        </c:if>
                    </c:forEach>
                </tr>

                <tr id="pricesRow">
                    <c:forEach items="${menu.getDishes()}" var="dish">
                        <c:if test="${dish.idCategory == category.idCategory}">
                            <td><br><strong id="price${dish.idDish}_${category.idCategory}"><p>${dish.price} BYN</p>
                            </strong></td>
                        </c:if>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </c:forEach>
</div>
