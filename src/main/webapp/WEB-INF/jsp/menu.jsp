<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.localization}"/>
<fmt:setBundle basename="localization" var="loc"/>

<fmt:message bundle="${loc}" key="localization.link.Menu" var="menu_link"/>
<fmt:message bundle="${loc}" key="localization.button.add" var="btnAddFmt"/>
<fmt:message bundle="${loc}" key="localization.txt.dishAddedMsg" var="dishAddedMsgFmt"/>
<fmt:message bundle="${loc}" key="localization.link.Account" var="account_link"/>
<fmt:message bundle="${loc}" key="localization.link.Basket" var="basket_link"/>
<fmt:message bundle="${loc}" key="localization.link.SignIn" var="signIn_link"/>

<link href="css/menu.css" rel="stylesheet">

<c:if test="${menu.dishes == null}">
    <jsp:forward page="/controller">
        <jsp:param name="command" value="get_menu"/>
    </jsp:forward>
</c:if>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="title"><h1>${menu_link}</h1></div>

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

                <tr id="orderRow">
                    <c:forEach items="${menu.getDishes()}" var="dish">
                        <c:if test="${dish.idCategory == category.idCategory}">
                            <td>
                                <button onclick="reduceOne(event, `${category.idCategory}`, `${dish.idDish}`)">-
                                </button>
                                <input id="quantityOf${dish.idDish}_${category.idCategory}" type="text" name="quantity"
                                       value="1" class="quantity">
                                <button onclick="addOne(event,`${category.idCategory}`, `${dish.idDish}`)">+</button>

                                <input type="submit" value="${btnAddFmt}" class="addDishToOrderBtn"
                                       id="addDishToOrder${dish.idDish}_${category.idCategory}"
                                       onclick="addDishToOrder(this,`${category.idCategory}`, `${dish.idDish}`)">
                            </td>
                        </c:if>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </c:forEach>
</div>

<div class="basket"><a href="basket"><span>${basket_link}</span></a></div>
<c:if test="${sessionScope.user != null}">
    <a href="account">
        <p class="signIn">${sessionScope.user.login}</p>
        <img src="images/account.jpeg" аlt="empty account" titlе="user is null" class="account"/>
    </a>
</c:if>

<c:if test="${sessionScope.user == null}">
    <a href="authorization">
        <p class="signIn">${signIn_link}</p>
        <img src="images/account.jpeg" аlt="empty account" titlе="user is null" class="account"/>
    </a>
</c:if>

<script src="js/xhr.js"></script>
<script src="js/Menu/AddReduceBtn.js"></script>
<script src="js/Menu/AddDishToOrder.js"></script>