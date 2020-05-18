<%--
  @author tanmoy.das
  @since 5/2/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="<spring:message code="label.app.description"/>">
    <meta name="author" content="<spring:message code="label.author.name"/>">

    <c:url var="iconLink" value="${Constants.NOTES_TASKS_FAVICON_LINK}"/>
    <link rel="icon" href="${iconLink}">

    <title><spring:message code="label.dashboard"/></title>

    <!-- Custom styles for this template -->
    <c:url var="dashboardCssLink" value="/css/dashboard.css"/>
    <link href="${dashboardCssLink}" rel="stylesheet">
</head>

<body>

<div class="d-flex flex-column flex-shrink-0 mh-100 overflow-auto">

    <%@ include file="/WEB-INF/includes/topNavigation.jsp" %>

    <div class="flex-fill d-flex">
        <%@ include file="/WEB-INF/includes/sidebarNavigation.jsp" %>

        <%@ include file="/WEB-INF/includes/sections/tasksMainSection.jsp" %>
    </div>
</div>
</body>
</html>
