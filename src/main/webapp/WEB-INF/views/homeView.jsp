<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>TacoBoom</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <link href="WEB-INF/assets/css/st.css" rel="stylesheet">
    <style>
        p { text-align: justify; }
    </style>
</head>
<body>

<c:choose>
    <c:when test="${user == null}">
        <jsp:include page="_navbar_logIN.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="_navbar_logOUT.jsp"/>
    </c:otherwise>
</c:choose>

    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner" role="listbox">
            <div class="carousel-item active">
                <img class="d-block w-100" src="taco1.png" alt="First slide">
                <div class="container">
                    <div class="carousel-caption text-right h-75">
                        <table>
                            <tr>
                                <td style="width: 55%"></td>
                                <td style="width: 45%;">
                                    <h1>Голоден?<br/>
                                        Заказывай сочнейший тако прямо сейчас!
                                    </h1>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <img class="d-block w-100" src="taco2.png" alt="Second slide">
                <div class="container">
                    <div class="carousel-caption text-center h-100">
                        <h1 class="pt-5">100% ингредиентов от российских поставщиков!</h1>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <img class="d-block w-100" src="taco3.png" alt="Third slide">
                <div class="container">
                    <div class="carousel-caption text-left h-75">
                        <table>
                            <tr>
                                <td style="width: 45%;">
                                    <h1>
                                        Мы за безопасность!<br/>
                                        Cоблюдаем социальную дистанцию, носим маски и перчатки и Вам советуем.
                                    </h1>
                                </td>
                                <td style="width: 55%"></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <div class="container">
        <table class="m-3">
            <tr>
                <td style="width: 30%"></td>
                <td class="text-right" style="width: 70%">
                    <h3>Наша кухня</h3>
                    <p>
                        Тако собираются вручную мексиканскими поварами под руководством Диего Ривера.
                        Родом из Гуанахуато, кухня которого впитала кулинарные традиции многих народов,
                        Ривера работал в лучших местных ресторанах. Тако в ресторане «TacoBoom» адаптированы
                        к европейским вкусам. Правильный баланс между аутентичностью и доступностью
                        поддерживают опытные профессионалы своего дела. Например, на приготовлении
                        соуса «Сальса» - неотъемлемой части любого тако - специализируется особый
                        повар мексиканской кухни.
                    </p>
                </td>
            </tr>
        </table>
        <table class="m-3">
            <tr>
                <td style="width: 70%">
                    <h3>Ингредиенты блюд</h3>
                    <p>
                        Российские региональные продукты в сочетании с мексиканскими техниками
                        приготовления легли в основу нового стиля MexRussian cuisine. Натуральные
                        и свежие ингредиенты, поставляемые с фермерств Центральной России, в
                        руках наших поваров превращаются в главное наследие мексиканской кухни.
                    </p>
                </td>
                <td style="width: 30%"></td>
            </tr>
        </table>
        <table class="m-3">
            <tr>
                <td style="width: 30%"></td>
                <td class="text-right" style="width: 70%">
                    <h3>Самая быстрая доставка от партнера</h3>
                    <p>
                        Самокат – партнер программы лояльности TacoBoom.
                        Лучший способ комфортно, безопасно и бесконтактно передать Ваш Тако – Самокат.
                        В рамках программы лояльности клиентам, делающим заказ впервые,
                        тако доставляется бесплатно. На следующие заказы – кэшбэк 5%.
                    </p>
                </td>
            </tr>
        </table>
    </div>

    <jsp:include page="_footer.jsp"/>
</body>
</html>
