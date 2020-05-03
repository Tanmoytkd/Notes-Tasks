<%--
  @author tanmoy.das
  @since 5/3/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="col-md-2 col-lg-1 d-none d-md-block bg-light sidebar">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item justify-content-center text-center">
                <c:url var="rootLink" value="/"/>
                <a class="nav-link <c:if test="${isDashboard}">active</c:if>" href="${rootLink}">
                    <em class="fa fa-home fa-3x circle-icon"></em>
                    <spring:message code="label.dashboard"/>
                    <c:if test="${isDashboard}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>

            <li class="nav-item justify-content-center text-center">
                <c:url var="messagesLink" value="/messages"/>
                <a class="nav-link <c:if test="${isMessage}">active</c:if>" href="${messagesLink}">
                    <em class="fa fa-envelope fa-3x circle-icon"></em>
                    <spring:message code="label.messages"/>
                    <c:if test="${isMessage}">
                        <span class="sr-only">(current)</span>
                    </c:if>
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="shopping-cart"></span>
                    Products
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="users"></span>
                    Customers
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="bar-chart-2"></span>
                    Reports
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="layers"></span>
                    Integrations
                </a>
            </li>
        </ul>

        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
            <span>Saved reports</span>
            <a class="d-flex align-items-center text-muted" href="#">
                <span data-feather="plus-circle"></span>
            </a>
        </h6>
        <ul class="nav flex-column mb-2">
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="file-text"></span>
                    Current month
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="file-text"></span>
                    Last quarter
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="file-text"></span>
                    Social engagement
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">
                    <span data-feather="file-text"></span>
                    Year-end sale
                </a>
            </li>
        </ul>
    </div>
</nav>