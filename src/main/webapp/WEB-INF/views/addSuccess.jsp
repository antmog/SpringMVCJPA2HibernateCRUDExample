<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

<jsp:include page="navBar.jsp"/>


<div class="alert alert-success lead">
    ${success}
</div>

<span class="well floatRight">
			Go to <a href="<c:url value='/adminPanel' />">Admin Panel</a>
		</span>

</body>

</html>