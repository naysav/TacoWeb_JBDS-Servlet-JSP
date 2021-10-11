<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<c:if test="${errorString != null}">
    <div class="shadow text-light p-3 ml-4 mr-4 mt-3 mb-6 bg-danger rounded">${errorString}</div>
</c:if>

<div class="container h-100">
    <div class="row h-100 w-100 justify-content-center align-items-center mt-4">
        <div class="card" style="width: 30rem;">
            <div class="card-header font-weight-bold text-center">
                Регистрация
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/TacoBoom?rule=registrationProcess">
                <div class="pl-2">
                    <div class="form-group pt-2">
                        <label for="lgn" class="col-sm-4 col-form-label"><sup>*</sup>Логин:</label>
                        <div class="pl-3 pr-4">
                            <input type="text" class="form-control" name="login" id="lgn">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="psswd1" class="col-sm-4 col-form-label"><sup>*</sup>Пароль:</label>
                        <div class="pl-3 pr-4">
                            <input type="text" class="form-control" name="password1" id="psswd1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="psswd2" class="col-sm-7 col-form-label"><sup>*</sup>Подтверждение пароля:</label>
                        <div class="pl-3 pr-4">
                            <input type="text" class="form-control" name="password2" id="psswd2">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="gen" class="col-sm-7 col-form-label"><sup>*</sup>Гендер:</label>
                        <div class="pl-3 pr-4">
                            <select class="custom-select" name="gender" id="gen">
                                <option selected>Выбрать</option>
                                <option value="M">Мужчина</option>
                                <option value="F">Женщина</option>
                                <option value="O">Другое</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="container text-center pb-3">
                    <button type="submit" class="btn btn-outline-primary" style="width: 12rem;">Зарегистрироваться</button>
                </div>
                <div class="container text-center pb-3">
                    <a href="${pageContext.request.contextPath}/TacoBoom?rule=homePage" type="button" class="card-link">Вернуться на главную страницу</a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>