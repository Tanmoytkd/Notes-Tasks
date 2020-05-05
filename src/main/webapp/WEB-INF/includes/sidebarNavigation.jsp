<%--
  @author tanmoy.das
  @since 5/3/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="col-md-2 col-xl-1 d-none d-md-block bg-light sidebar p-0">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item justify-content-center text-center">
                <c:url var="rootLink" value="/"/>
                <a class="nav-link <c:if test="${isDashboardPage}">active</c:if>" href="${rootLink}">
                    <em class="fa fa-home fa-3x circle-icon"></em>
                    <spring:message code="label.dashboard"/>
                    <c:if test="${isDashboardPage}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>

            <li class="nav-item justify-content-center text-center">
                <c:url var="messagesLink" value="/messages"/>
                <a class="nav-link <c:if test="${isMessagesPage}">active</c:if>" href="${messagesLink}">
                    <em class="fa fa-envelope fa-3x circle-icon"></em>
                    <spring:message code="label.messages"/>
                    <c:if test="${isMessagesPage}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>

            <li class="nav-item justify-content-center text-center">
                <c:url var="notesLink" value="/notes"/>
                <a class="nav-link <c:if test="${isNotesPage}">active</c:if>" href="${notesLink}">
                    <em class="fa fa-book fa-3x circle-icon"></em>
                    <spring:message code="label.notes"/>
                    <c:if test="${isNotesPage}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>

            <li class="nav-item justify-content-center text-center">
                <c:url var="tasksLink" value="/tasks"/>
                <a class="nav-link <c:if test="${isTasksPage}">active</c:if>" href="${tasksLink}">
                    <em class="fa fa-tasks fa-3x circle-icon"></em>
                    <spring:message code="label.tasks"/>
                    <c:if test="${isTasksPage}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>

            <li class="nav-item justify-content-center text-center">
                <c:url var="reportsLink" value="/reports"/>
                <a class="nav-link <c:if test="${isReportsPage}">active</c:if>" href="${reportsLink}">
                    <em class="fa fa-flag fa-3x circle-icon"></em>
                    <spring:message code="label.reports"/>
                    <c:if test="${isReportsPage}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>

            <li class="nav-item justify-content-center text-center">
                <c:url var="profileLink" value="/profile"/>
                <a class="nav-link <c:if test="${isProfilePage}">active</c:if>" href="${profileLink}">
                    <em class="fa fa-user fa-3x circle-icon"></em>
                    <spring:message code="label.profile"/>
                    <c:if test="${isProfilePage}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>
        </ul>
    </div>
</nav>