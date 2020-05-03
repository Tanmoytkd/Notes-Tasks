<%--
  @author tanmoy.das
  @since 4/29/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0" aria-label="navbar">
    <c:url var="rootLink" value="/"/>
    <a class="navbar-brand mx-3" href="${rootLink}">
        <img src="${iconLink}" alt="icon">
        <spring:message code="label.appName"/>
    </a>
    <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">
    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <c:url var="logoutLink" value="/logout"/>
            <a class="nav-link" href="${logoutLink}">
                <spring:message code="label.logoutTxt"/>
            </a>
        </li>
    </ul>
</nav>