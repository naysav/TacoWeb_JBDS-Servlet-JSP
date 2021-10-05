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
                Реквизиты банковской карты
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/payment">
                <div class="pl-2">
                    <div class="form-group pt-3">
                        <div class="pl-3 pr-4">
                            <input type="text" class="form-control" name="number" id="nbr" placeholder="Number of Bank Card"/>
                        </div>
                    </div>
                    <div class="row ml-2 pb-2">
                        <div class="ml-2 pr-4" style="width: 15rem;">
                            <input type="text" class="form-control" name="name" id="name" placeholder="Name" />
                        </div>
                        <div class="pr-4" style="width: 10rem;">
                            <input type="text" class="form-control" name="data" id="data" placeholder="Data"/>
                        </div>
                    </div>
                </div>

                <div class="container text-center pb-3 pt-2">
                    <button type="submit" class="btn btn-outline-success pb-2" style="width: 10rem;">Подтвердить</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
