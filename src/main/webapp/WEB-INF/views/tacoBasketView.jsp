<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Taco Basket</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link href="../assets/css/st.css" rel="stylesheet">
</head>
<body>

<jsp:include page="_navbar_logOUT.jsp"/>

<div class="container">
    <h3 class="pt-4">Список выбранных тако</h3>

    <p style="color: red;">${errorString}</p>
    <c:forEach items="${tacoList}" var="taco" >
        <h5>Состав вашего тако №${taco.id}:</h5>
        <p>
            ${taco.flapjack.name}<c:if test="${taco.chicken.name != ''}">,
                    ${taco.chicken.name}</c:if><c:if test="${taco.garlic.name != ''}">,
                    ${taco.garlic.name}</c:if><c:if test="${taco.onion.name != ''}">,
                    ${taco.onion.name}</c:if><c:if test="${taco.tomato.name != ''}">,
                    ${taco.tomato.name}</c:if><c:if test="${taco.haricot.name != ''}">,
                    ${taco.haricot.name}</c:if><c:if test="${taco.cheese.name != ''}">,
                    ${taco.cheese.name}</c:if><c:if test="${taco.avocado.name != ''}">,
                    ${taco.avocado.name}</c:if>.
        </p>
        <p>Стоимость: ${taco.totalPrice} руб. <a href="deleteTaco?id=${taco.id}">Удалить</a></p>
    </c:forEach>

    <c:if test="${orderPrice > 0}">
        <div class="d-block float-right">
            <h5>Итоговая сумма заказа - ${orderPrice} руб.</h5>
            <div class="d-block float-right pt-2">
                <a href="${pageContext.request.contextPath}/payment" type="button" class="btn btn-outline-primary">Оплатить заказ</a>
            </div>
        </div>
    </c:if>
</div>

</body>
</html>