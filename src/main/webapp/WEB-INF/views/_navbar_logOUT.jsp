<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand pb-2" href="${pageContext.request.contextPath}/TacoBoom?rule=homePage">TacoBoom</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/TacoBoom?rule=homePage">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/TacoBoom?rule=createTacoPage">Собрать тако</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/TacoBoom?rule=tacoBasketPage">Корзина</a>
                </li>

            </ul>

            <form class="form-inline">
                <div class="row">
                    <div class="col-sm-4 pr-4 pt-2 pb-2">
                        <ul class="navbar-nav mr-auto">
                            <li class="nav-item active">
                                <a class="nav-link font-weight-bold" href="#">${loginedUser.userName}</a>
                            </li>
                       </ul>
                    </div>
                    <div class="col-sm-4 pr-4 pt-2 pb-2">
                        <a href="${pageContext.request.contextPath}/TacoBoom?rule=logoutProcess" type="button" class="btn btn-outline-danger">Выйти</a>
                    </div>
                    <div class="col-sm-1 pr-4"></div>
                </div>
            </form>
        </div>
    </div>
</nav>



