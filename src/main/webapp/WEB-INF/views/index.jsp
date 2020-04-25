<%--
  @author tanmoy.das
  @since 4/12/20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="label.title"/></title>
</head>
<body>
<center>
    <h1><spring:message code="label.appName"/></h1>
</center>
<div>
    <c:forEach items="${users}" var="user">
        <center>${user.name}</center>
        <br>
        <c:forEach items="${user.ownNotes}" var="note">
            ${note.title} <br> ${note.content}
        </c:forEach>
        <br><br>
    </c:forEach>
</div>

</body>
</html>
