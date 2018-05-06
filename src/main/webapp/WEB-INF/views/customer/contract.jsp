<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>

<main class="mt-4">
    <div class="container">
        <c:if test="${contract.status == 'BLOCKED'}">
            <div class="alert alert-danger" role="alert">
                Contract is blocked!
            </div>
        </c:if>
        <c:if test="${contract.status == 'INACTIVE'}">
            <div class="alert alert-warning" role="alert">
                Contract is inactive.
            </div>
        </c:if>
        <div class="row">
            <div class="col-md-4">
                <div class="card mb-4">
                    <h5 class="card-header">
                        <strong> ${contract.id} : ${contract.phoneNumber}</strong>
                    </h5>
                    <div class="card-body">
                        <table class="table table-hover">
                            <tbody>
                            <tr class="contract-row">
                                <td>owner id</td>
                                <td>${contract.user.id}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>tariff_id</td>
                                <td>${contract.tariff.id}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>price</td>
                                <td>${contract.price}</td>
                            </tr>
                            <tr class="contract-row">
                                <td>active options</td>
                                <td><c:forEach items="${contract.activeOptions}" var="option">
                                    ${option.id};
                                </c:forEach></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <c:choose>
                        <c:when test="${contract.status == 'INACTIVE'}">
                            <label value="Inactive">Inactive (deactivated)
                            </label>
                            <button type="button" class="btn btn-success" id="unBlockContractButton">Activate contract
                            </button>
                        </c:when>
                        <c:when test="${contract.status == 'ACTIVE'}">
                            <label value="Inactive">Active
                            </label>
                            <button type="button" class="btn btn-warning" id="deactivateContractButton">Deactivate contract
                            </button>
                        </c:when>
                    </c:choose>
                </div>
                <br/>
            </div>
        </div>
    </div>

</main>
<script>
    var contract_id = ${contract.id}
</script>
<jsp:include page="../footer.jsp"/>
</body>
</html>