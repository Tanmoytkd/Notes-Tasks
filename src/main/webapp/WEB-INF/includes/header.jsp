<%--
  @author tanmoy.das
  @since 4/29/20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="bootstrapCssLink" value="/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="${bootstrapCssLink}">

<c:url var="fontAwesomeCssLink" value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"/>
<link rel="stylesheet" href="${fontAwesomeCssLink}" integrity="sha256-h20CPZ0QyXlBuAw7A+KluUYx/3pK+c7lYEpqLTlxjYQ="
      crossorigin="anonymous"/>

<c:url var="styleCssLink" value="/css/style.css"/>
<link rel="stylesheet" type="text/css" href="${styleCssLink}">

<c:url var="simplebarCssLink" value="https://unpkg.com/simplebar@latest/dist/simplebar.css"/>
<link rel="stylesheet" href="${simplebarCssLink}">



