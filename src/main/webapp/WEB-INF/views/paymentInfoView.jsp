<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Оплата заказа</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
    <div class="container h-100">
        <c:if test="${errorString != null}">
            <div class="shadow text-light p-3 ml-4 mr-4 mt-3 mb-6 bg-danger rounded">${errorString}</div>
        </c:if>
        <div class="row h-100 w-100 mt-5 justify-content-center align-items-center">
            <div class="card" style="width: 30rem;">
                <div class="card-header font-weight-bold text-center">
                    Информация об оплате
                </div>
                    <div class="p-4">
                        <p class="text-success text-center">Оплата прошла успешно!</p>
                        <p>Номер карты: ${bankCard.number}</p>
                        <p>Имя покупателя: ${bankCard.name}</p>
                        <p>Срок действия карты: ${bankCard.data}</p>
                        <p>Оплачено ${orderPrice} руб.</p>
                    </div>

                    <div class="container text-center pb-4">
                        <a href="${pageContext.request.contextPath}/TacoBoom?rule=homePage"
                           class="btn btn-outline-primary pb-2" style="width: 15rem;">На главную страницу</a>
                    </div>
            </div>
        </div>
    </div>
</body>
</html>
