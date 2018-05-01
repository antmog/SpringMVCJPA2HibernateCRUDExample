<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

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
                <div class="card mb-4 border-dark">
                    <h5 class="card-header">
                        <strong>${loggedinuser}</strong>
                    </h5>
                    <div class="card-body">
                        <br/>
                        <br/>
                        <br/>
                        <br/>
                    </div>
                </div>
                <br/>
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong>Tariffs</strong>
                    </h5>
                    <div class="card-body">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>id</th>
                                    <th>name</th>
                                    <th>price</th>
                                    <th>available options id</th>
                                    <th width="100"></th>
                                    <th width="100"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tariffs}" var="tariff">
                                    <tr>
                                        <td>${tariff.id}</td>
                                        <td>${tariff.name}</td>
                                        <td>${tariff.price}</td>
                                        <td><c:forEach items="${tariff.availableOptions}" var="option">
                                            ${option.id};
                                        </c:forEach></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </li>
                    </div>
                    <a href="/adminPanel/allTariffs" class="card-link">All tariffs</a>
                    <button type="button" class="btn btn-success" id="addTariffButton">Add tariff</button>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-4">

                    <h5 class="card-header">
                        Users list
                    </h5>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>firstName</th>
                                        <th>lastName</th>
                                        <th>address</th>
                                        <th>birthDate</th>
                                        <th>login</th>
                                        <th>passport</th>
                                        <th>mail</th>
                                        <th>contracts</th>
                                        <th width="100"></th>
                                        <th width="100"></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${users}" var="user">
                                        <tr>
                                            <td>${user.id}</td>
                                            <td>${user.firstName}</td>
                                            <td>${user.lastName}</td>
                                            <td>${user.address}</td>
                                            <td>${user.birthDate}</td>
                                            <td>${user.login}</td>
                                            <td>${user.passport}</td>
                                            <td>${user.mail}</td>
                                            <td><c:forEach items="${user.userContracts}" var="contract">
                                                ${contract.id};
                                            </c:forEach></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </li>
                        </ul>
                    </div>
                    <a href="/adminPanel/allUsers" class="card-link">All Users</a>
                    <a href="/adminPanel/allContracts" class="card-link">All Contracts</a>
                    <button type="button" class="btn btn-success" id="addUserButton">Add user</button>
                    <button type="button" class="btn btn-success" id="addContractButton">Add contract</button>
                </div>
            </div>

        </div>
    </div>

</main>


<script src="/static/vendors/jquery/jquery-3.3.1.min.js" defer></script>
<script src="/static/js/main.js" defer></script>
</body>
</html>