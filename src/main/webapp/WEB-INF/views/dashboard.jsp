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
    <meta name="description" content="<spring:message code="label.appDescription"/>">
    <meta name="author" content="<spring:message code="label.authorName"/>">

    <c:url var="iconLink" value="/img/spidey.png"/>
    <link rel="icon" href="${iconLink}">

    <title><spring:message code="label.dashboard"/></title>

    <!-- Custom styles for this template -->
    <c:url var="dashboardCssLink" value="/css/dashboard.css"/>
    <link href="${dashboardCssLink}" rel="stylesheet">
</head>

<body>

<div class="d-flex flex-column flex-shrink-0 mh-100 overflow-auto">

    <%@ include file="/WEB-INF/includes/topNavigation.jsp" %>

    <div class="row">
        <%@ include file="/WEB-INF/includes/sidebarNavigation.jsp" %>

        <c:choose>
            <c:when test="${isDashboardPage}">

            </c:when>
            <c:when test="${isMessagesPage}">
                <%@ include file="/WEB-INF/includes/sections/messagesMainSection.jsp" %>
            </c:when>
            <c:when test="${isNotesPage}">
                <%@ include file="/WEB-INF/includes/sections/NotesMainSection.jsp" %>
            </c:when>
            <c:when test="${isTasksPage}">
                <%@ include file="/WEB-INF/includes/sections/tasksMainSection.jsp" %>
            </c:when>
            <c:when test="${isReportsPage}">

            </c:when>
            <c:when test="${isProfilePage}">
                <%@ include file="/WEB-INF/includes/sections/profileMainSection.jsp" %>
            </c:when>
            <c:when test="${isSearchPage}">
                <%@ include file="/WEB-INF/includes/sections/searchResultMainSection.jsp" %>
            </c:when>
            <c:otherwise>
                <%@ include file="/WEB-INF/includes/sections/defaultMainSection.jsp" %>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
