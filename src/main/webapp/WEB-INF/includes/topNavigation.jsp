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
    <a class="navbar-brand mx-3 px-2" href="${rootLink}">
        <c:url var="iconLink" value="/img/spidey.png"/>
        <img src="${iconLink}" alt="icon">
        <spring:message code="label.appName"/>
    </a>

    <c:url var="bnLink" value="?locale=bn"/>
    <a href="${bnLink}" class="nav-item mx-1">
        <c:url var="bdFlagLink" value="/img/bd-flag.png"/>
        <img src="${bdFlagLink}" class="" alt="Bengali">
    </a>

    <c:url var="enLink" value="?locale=en"/>
    <a href="${enLink}" class="nav-item mx-1">
        <c:url var="usFlagLink" value="/img/us-flag.png"/>
        <img src="${usFlagLink}" class="" alt="English">
    </a>

    <c:url var="searchLink" value="/search"/>
    <form:form action="${searchLink}" method="get" cssClass="form-inline w-100 mx-3 my-1" modelAttribute="searchQuery">
        <form:input path="query" cssClass="form-control rounded form-control-dark w-75" type="text"
                    placeholder="Search" aria-label="Search" required="required"/>
        <button type="submit" class="btn btn-light mx-2">
            <spring:message code="label.submit"/>
        </button>
    </form:form>


    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <c:url var="logoutLink" value="/logout"/>
            <a class="nav-link" href="${logoutLink}">
                <spring:message code="label.logoutTxt"/>
            </a>
        </li>
    </ul>
</nav>