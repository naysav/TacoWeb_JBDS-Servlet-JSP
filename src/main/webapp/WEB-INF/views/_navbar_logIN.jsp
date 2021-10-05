<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page import="com.naysav.jsp.*" %>--%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand pb-2" href="${pageContext.request.contextPath}/">TacoBoom</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">Главная</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/createTaco">Собрать тако</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/tacoBasket">Корзина заказа</a>
                </li>
            </ul>

            <form class="form-inline my-2 my-lg-0">
                <div class="row">
                    <div class="col-sm-2 pt-2 pb-2">
                        <a href="${pageContext.request.contextPath}/login" type="button" class="btn btn-outline-success">Вход</a>
                    </div>

                    <div class="col-sm-1"></div>

                    <div class="col-sm-1 pt-2">
                        <a href="${pageContext.request.contextPath}/registration" type="button" class="btn btn-outline-primary">Регистрация</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</nav>



