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
    <link href="css/menu.css" rel="stylesheet">
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
<div class="menu">
    <h1 style="margin-top: 5%">Starters</h1>
    <table>
        <tr>
            <td><img src="images/dish/tartar.png" class="dish"
                     titlе="тартар из говядины"/></td>
            <td><img src="images/dish/tartar_buratta.png" class="dish"
                     titlе="тартар из говядины с бураттой"/></td>
            <td><img src="images/dish/hummus.png" class="dish"
                     titlе="хумус"/></td>
            <td><img src="images/dish/green_salad.png" class="dish"
                     titlе="зеленый салат"/></td>
            <td><img src="images/dish/shrimp.png" class="dish"
                     titlе="креветка темпура"/></td>
        </tr>
        <tr>
            <td><strong>Tar tar their beef with chanterelles</strong></td>
            <td><strong>Tar tar their beef with buratta</strong></td>
            <td><strong>Hummus with olive salsa</strong></td>
            <td><strong>Green salad with passionfruit sauce</strong></td>
            <td><strong>Shrimp tempura</strong></td>

        </tr>
        <tr>
            <td>150 gram</td>
            <td>140 gram</td>
            <td>170 gram</td>
            <td>170 gram</td>
            <td>110 gram</td>
        </tr>
        <tr>
            <td><strong><br>14 BLR</strong></td>
            <td><strong><br>12 BLR</strong></td>
            <td><strong><br>17 BLR</strong></td>
            <td><strong><br>15 BLR</strong></td>
            <td><strong><br>19 BLR</strong></td>
        </tr>
    </table>
    <hr/>

    <h1>Main course</h1>
    <hr/>
    <h2>Pasta</h2>
    <table>
        <tr>
            <td><img src="images/dish/pasta_salmon.png" class="dish"
                     titlе="паста с лососем"/></td>
            <td><img src="images/dish/pasta_ham.png" class="dish"
                     titlе="паста с ветчиной и сыром"/></td>
            <td><img src="images/dish/carbonara.png" class="dish"
                     titlе="карбонара"/></td>
            <td><img src="images/dish/pasta_chicken.png" class="dish"
                     titlе="паста с курицей и грибами"/></td>
            <td><img src="images/dish/pasta_feta.png" class="dish"
                     titlе="паста с шампиньонами и фетой"/></td>
        </tr>
        <tr>
            <td><strong>Pasta with salmon</strong></td>
            <td><strong>Pasta with ham and cheese</strong></td>
            <td><strong>Carbonara</strong></td>
            <td><strong>Pasta with chicken and mushrooms</strong></td>
            <td><strong>Pasta with mushrooms and feta</strong></td>

        </tr>
        <tr>
            <td>350 gram</td>
            <td>340 gram</td>
            <td>370 gram</td>
            <td>370 gram</td>
            <td>310 gram</td>
        </tr>
        <tr>
            <td><strong><br>19 BLR</strong></td>
            <td><strong><br>15 BLR</strong></td>
            <td><strong><br>15 BLR</strong></td>
            <td><strong><br>15 BLR</strong></td>
            <td><strong><br>15 BLR</strong></td>
        </tr>
    </table>
    <hr/>
    <h2>Fish and seafood</h2>
    <table>
        <tr>
            <td><img src="images/dish/oyster.png" class="dish"
                     titlе="устрицы с розмарином"/></td>
            <td><img src="images/dish/tataki.png" class="dish"
                     titlе="Татаки с тунцом"/></td>
            <td><img src="images/dish/black_pearl.png" class="dish"
                     titlе="Черная жемчужина"/></td>
            <td><img src="images/dish/palm_beach.png" class="dish"
                     titlе="Суши пляж"/></td>
            <td><img src="images/dish/summer_sea.png" class="dish"
                     titlе="Суши море"/></td>
        </tr>
        <tr>
            <td><strong>Oysters with rosemary </strong></td>
            <td><strong>Tataki with tuna</strong></td>
            <td><strong>Black pearl</strong></td>
            <td><strong>Palm beach</strong></td>
            <td><strong>Summer sea</strong></td>

        </tr>
        <tr>
            <td>110 gram</td>
            <td>270 gram</td>
            <td>280 gram</td>
            <td>270 gram</td>
            <td>390 gram</td>
        </tr>
        <tr>
            <td><strong><br>50 BLR</strong></td>
            <td><strong><br>20 BLR</strong></td>
            <td><strong><br>30 BLR</strong></td>
            <td><strong><br>19 BLR</strong></td>
            <td><strong><br>21 BLR</strong></td>
        </tr>
    </table>
    <hr/>
    <br/>
    <h2>Meat</h2>
    <table>
        <tr>
            <td><img src="images/dish/wellington.png" class="dish"
                     titlе="Веллингтон"/></td>
            <td><img src="images/dish/beef.png" class="dish"
                     titlе="Говядина в кленовом сиропе"/></td>
            <td><img src="images/dish/steak_cherry.png" class="dish"
                     titlе="Стейк с грибами и черри"/></td>
            <td><img src="images/dish/pepper_steak.png" class="dish"
                     titlе="Пеппер-стейк"/></td>
            <td><img src="images/dish/ribai_steak.png" class="dish"
                     titlе="Рибай-стейк"/></td>
        </tr>
        <tr>
            <td><strong>Wellington</strong></td>
            <td><strong>Beef in maple syrup</strong></td>
            <td><strong>Steak with mushrooms and cherry</strong></td>
            <td><strong>Pepper steak</strong></td>
            <td><strong>Rib eye steak</strong></td>

        </tr>
        <tr>
            <td>420 gram</td>
            <td>370 gram</td>
            <td>400 gram</td>
            <td>300 gram</td>
            <td>300 gram</td>
        </tr>
        <tr>
            <td><strong><br>33 BLR</strong></td>
            <td><strong><br>32 BLR</strong></td>
            <td><strong><br>41 BLR</strong></td>
            <td><strong><br>41 BLR</strong></td>
            <td><strong><br>88 BLR</strong></td>
        </tr>
    </table>
    <br/>
    <hr/>
    <h2>Soups</h2>
    <table>
        <tr>
            <td><img src="images/dish/tom_yam.png" class="dish"
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
            <td><strong><br>24 BLR</strong></td>
            <td><strong><br>22 BLR</strong></td>
            <td><strong><br>23 BLR</strong></td>
            <td><strong><br>19 BLR</strong></td>
            <td><strong><br>21 BLR</strong></td>
        </tr>
    </table>
    <br/>
    <hr/>
    <h1>Desserts</h1>
    <table>
        <tr>
            <td><img src="images/dish/lingonberry_cake.png" class="dish"
                     titlе="Брусничный торт"/></td>
            <td><img src="images/dish/napoleon.png" class="dish"
                     titlе="Наполеон"/></td>
            <td><img src="images/dish/mandarine_waffle.png" class="dish"
                     titlе="Вафли с мандарином"/></td>
            <td><img src="images/dish/cherry_waffle.png" class="dish"
                     titlе="Вафли с вишней"/></td>
            <td><img src="images/dish/banana_waffle.png" class="dish"
                     titlе="Вафли с бананом"/></td>
        </tr>
        <tr>
            <td><strong>Lingonberry cake with white chocolate</strong></td>
            <td><strong>Napoleon</strong></td>
            <td><strong>Mandarine waffles</strong></td>
            <td><strong>Cherry waffles</strong></td>
            <td><strong>Banan waffles</strong></td>

        </tr>
        <tr>
            <td>100 gram</td>
            <td>150 gram</td>
            <td>120 gram</td>
            <td>170 gram</td>
            <td>150 gram</td>
        </tr>
        <tr>
            <td><strong><br>14 BLR</strong></td>
            <td><strong><br>9 BLR</strong></td>
            <td><strong><br>13 BLR</strong></td>
            <td><strong><br>12 BLR</strong></td>
            <td><strong><br>11 BLR</strong></td>
        </tr>
    </table>
    <hr/>
    <h1>Drinks</h1>
    <table>
        <tr>
            <td><img src="images/dish/lemonade.png" class="dish"
                     titlе="Лимонад классический"/></td>
            <td><img src="images/dish/tarhun.png" class="dish"
                     titlе="Тархун"/></td>
            <td><img src="images/dish/lemonde_basil.png" class="dish"
                     titlе="Лимонад базилик клубника"/></td>
            <td><img src="images/dish/lemonade_orange.png" class="dish"
                     titlе="Лимонад апельсин розмарин"/></td>
            <td><img src="images/dish/milk_cold.png" class="dish"
                     titlе="Кофе холодного заваривания"/></td>
        </tr>
        <tr>
            <td><strong>Lemonade classic</strong></td>
            <td><strong>Tarhun</strong></td>
            <td><strong>Lemonade with basil and strawberry</strong></td>
            <td><strong>Lemonade with orange and rosemary</strong></td>
            <td><strong>Milk cold drew</strong></td>

        </tr>
        <tr>
            <td>500 gram</td>
            <td>500 gram</td>
            <td>500 gram</td>
            <td>500 gram</td>
            <td>500 gram</td>
        </tr>
        <tr>
            <td><strong><br>10 BLR</strong></td>
            <td><strong><br>10 BLR</strong></td>
            <td><strong><br>10 BLR</strong></td>
            <td><strong><br>10 BLR</strong></td>
            <td><strong><br>10 BLR</strong></td>
        </tr>
    </table>
</div>
<hr/>
</body>
</html>
