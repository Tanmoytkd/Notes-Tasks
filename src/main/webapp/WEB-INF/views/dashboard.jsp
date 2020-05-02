<%--
  @author tanmoy.das
  @since 5/2/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><spring:message code="label.dashboard"/></title>
</head>
<body>
<div class="text-center">
    <p><spring:message code="label.dashboard"/></p>
</div>

<div class="text-center">
    <c:url var="logoutLink" value="/logout"/>
    <a href="${logoutLink}"><spring:message code="label.logoutTxt"/></a>
</div>

</body>
</html>
