<%--
  @author tanmoy.das
  @since 4/29/20
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:url var="iconLink" value="/img/spidey.png"/>
    <link rel="icon" href="${iconLink}">

    <title><spring:message code="label.title"/>: <sitemesh:write property='title'/></title>
    <sitemesh:write property="head"/>
    <%@ include file="/WEB-INF/includes/header.jsp" %>
</head>

<body class="border border-secondary mh-100">
<%@ include file="/WEB-INF/includes/jsScripts.jsp" %>

<sitemesh:write property="body"/>
</body>
</html>
