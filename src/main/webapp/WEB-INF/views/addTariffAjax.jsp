<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <!-- ... -->
    <link rel="stylesheet" href="/static/vendors/bootstrap-4.1.0/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/index.css">


    <title>Info-System</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/"><i class="fas fa-phone-square"></i> Info-System</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item mr-4">
                <a class="nav-link" href="#">
                    Busket
                    <i class="fas fa-cart-arrow-down"></i>
                </a>
            </li>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item">
                    <a class="nav-link" href="/logout">
                        <strong>${loggedinuser}</strong>, Log out
                    </a>
                </li>
            </sec:authorize>
        </ul>
    </div>
</nav>

<main class="mt-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4">
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable">name</label>
                            <div class="col-md-7">
                                <input type="text" id="name" class="form-control input-sm"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-3 control-lable">price</label>
                            <div class="col-md-7">
                                <input type="text"  id="price" class="form-control input-sm"/>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="addTariff">Add Tariff</button>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Added options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="addedOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="delOption">Delete selected option</button>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Available options</strong>
                    </h5>
                    <div class="card-body">
                        <div class="container">
                            <table class="table" id="availableOptions">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>costofadd</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${options}" var="option">
                                    <tr class="move-row">
                                        <td>${option.id}</td>
                                        <td>${option.name}</td>
                                        <td>${option.price}</td>
                                        <td>${option.costOfAdd}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <button type="button" class="btn btn-success" id="addOption">Add selected option</button>
                </div>
            </div>

        </div>
    </div>

</main>

<script>

</script>
<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/vendors/jquery/jquery.tabletojson.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>