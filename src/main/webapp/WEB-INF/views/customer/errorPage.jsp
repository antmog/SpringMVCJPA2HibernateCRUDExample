<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <jsp:include page="../header.jsp"/>
</head>
<body class="font-4">
<jsp:include page="navBar.jsp"/>
<div class="alert alert-danger lead content">
    ${error}
    <span class="well floatRight">
        Go to <a href="/customerPanel">user panel</a>.
    </span>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>