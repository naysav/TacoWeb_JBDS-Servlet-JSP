<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Создание Тако</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>

<jsp:include page="_navbar_logOUT.jsp"/>

<div class="container pb-3">
    <h3 class="pt-4">Список ингредиентов</h3>

    <p style="color: red;">${errorString}</p>
    <p style="color: green;">${successMessage}</p>

    <form method="POST" action="${pageContext.request.contextPath}/TacoBoom?rule=createTacoProcess">
        <div class="pl-2">
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="flapjack" id="cb1" value="P001" required>
                <label class="custom-control-label font-weight-bold" for="cb1"><h5>Свежеиспеченная кукурузная тортилья (+23 руб.)</h5></label>
                <p class="text-secondary">Подходит и веганам, и любителям мяса. Обязательный ингредиент!</p>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="chicken" id="cb2" value="P002">
                <label class="custom-control-label" for="cb2"><h5>Курица с хрустящей корочкой (+41 руб.)</h5></label>
                <p class="text-secondary">Прямиком из рук тамбовских фермеров.</p>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="garlic" id="cb3" value="P003">
                <label class="custom-control-label" for="cb3"><h5>Ароматный чеснок (+12 руб.)</h5></label>
                <p class="text-secondary">Обжарен на оливковом масле.</p>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="onion" id="cb4" value="P004">
                <label class="custom-control-label" for="cb4"><h5>Мелко пассерованный лук (+14 руб.)</h5></label>
                <p class="text-secondary">Приготовлен на открытом огне.</p>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="tomato" id="cb5" value="P005">
                <label class="custom-control-label" for="cb5"><h5>Томаты (+27 руб.)</h5></label>
                <p class="text-secondary">Тушенные на кокосовом масле в соусе «Матбуха».</p>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="haricot" id="cb6" value="P006">
                <label class="custom-control-label" for="cb6"><h5>Ростки фасоли МАШ (+21 руб.)</h5></label>
                <p class="text-secondary">Только-только проросли.</p>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="cheese" id="cb7" value="P007">
                <label class="custom-control-label" for="cb7"><h5>Сыр творожный (+11 руб.)</h5></label>
                <p class="text-secondary">Изготовлен московскими фермерами с итальянским происхождением.</p>
            </div>
            <div class="custom-control custom-checkbox">
                <input type="checkbox" class="custom-control-input" name="avocado" id="cb8" value="P008">
                <label class="custom-control-label" for="cb8"><h5>Авокадо (+23 руб.)</h5></label>
                <p class="text-secondary">Авокадо, он и в африке авокадо.</p>
            </div>


        </div>

        <%--    REMEMBER ME--%>
        <div class="container text-center pb-3 pt-2">
            <button type="submit" class="btn btn-outline-warning pb-2" style="width: 10rem;">Добавить в заказ</button>
        </div>
    </form>
</div>

<jsp:include page="_footer.jsp"/>
</body>
</html>
