<%--
  Created by IntelliJ IDEA.
  User: natalagaponenko
  Date: 8.08.22
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Menu</title>
    <link rel="stylesheet" href="css/Menu.css">
</head>
<br>
<div class="topline">
    <table>
        <tr>
            <td style="width: 800px"><a href="index.jsp"><img src="images/catharsisblack.png"
                                                              аlt="Логотип ресторана черный"
                                                              titlе="Наименование catharsis"/></a></td>
            <td style="width: 800px"><h1>MENU</h1></td>
        </tr>
    </table>
</div>
<div class="account"><span>account</span></div>
<div class="basket"><span>basket</span></div>
<div class="main"><a href="index.jsp"><span>go to main</span></a></div>
<div class="menu">
    <h1 style="margin-top: 5%">Starters</h1>
    <table>
        <tr>
            <td><img src="images/dish/bread.jpeg" class="dish"
                     titlе="хлебная корзина"/></td>
            <td><img src="images/dish/poultry_pate_kiwi.jpeg" class="dish"
                     titlе="паштет с киви"/></td>
            <td><img src="images/dish/hummus_tuna.png" class="dish"
                     titlе="хуммус с тунцом"/></td>
            <td><img src="images/dish/sweet_potato_hummus.png" class="dish"
                     titlе="хуммус с бататом"/></td>
        </tr>
        <tr>
            <td><strong>Bread basket </strong></td>
            <td><strong>Poultry pate with kiwi and apricot</strong></td>
            <td><strong>Hummus with tuna</strong></td>
            <td><strong>Sweet potato hummus</strong></td>

        </tr>
        <tr>
            <td>150 gram</td>
            <td>140 gram</td>
            <td>170 gram</td>
            <td>170 gram</td>
        </tr>
        <tr>
            <div class="price">
                <td><strong><br>14 BLR</strong></td>
                <td><strong><br>12 BLR</strong></td>
                <td><strong><br>17 BLR</strong></td>
                <td><strong><br>15 BLR</strong></td>
            </div>
        </tr>
    </table>
    <hr/>

    <h1>Main course</h1>
    <hr/>
    <h2>Pasta</h2>

    <h2>Poultry meat</h2>
    <br/>
    <h2>Vegetables</h2>
    <br/>
    <h2>Fish and seafood</h2>
    <br/>
    <h2>Meat</h2>
    <br/>
    <hr/>
    <h2>Soups</h2>
    <table>
        <tr>
            <td><img src="images/dish/tom_yam.png"
                     titlе="Том ям"/></td>
            <td><img src="images/dish/fo.png" class="dish"
                     titlе="Фо"/></td>
            <td><img src="images/dish/ramen.png" class="dish"
                     titlе="Рамен"/></td>
            <td><img src="images/dish/borsch.png" class="dish"
                     titlе="Борщ"/></td>
            <td><img src="images/dish/gaspacho.png" class="dish"
                     titlе="Гаспачо"/></td>
        </tr>
        <tr>
            <td><strong>Tom yam with tiger prawns </strong></td>
            <td><strong>Fo</strong></td>
            <td><strong>Ramen with pork</strong></td>
            <td><strong>Borscht with dried beets</strong></td>
            <td><strong>Farm tomato gazpacho</strong></td>

        </tr>
        <tr>
            <td>400 gram</td>
            <td>370 gram</td>
            <td>380 gram</td>
            <td>400 gram</td>
            <td>390 gram</td>
        </tr>
        <tr>
            <div class="price">
                <td><strong><br>24 BLR</strong></td>
                <td><strong><br>22 BLR</strong></td>
                <td><strong><br>23 BLR</strong></td>
                <td><strong><br>19 BLR</strong></td>
                <td><strong><br>21 BLR</strong></td>
            </div>
        </tr>
    </table>
    <br/>
    <h1>Desserts</h1>
</div>
<hr/>
</body>
</html>
