<%--
  @author tanmoy.das
  @since 4/29/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="jqueryLink" value="/js/jquery-3.4.1.min.js"/>
<script src="${jqueryLink}"></script>

<c:url var="bootstrapJsLink" value="/js/bootstrap.min.js"/>
<script src="${bootstrapJsLink}"></script>

<c:url var="fontAwesomeJsLink" value="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js"/>
<script src="${fontAwesomeJsLink}" integrity="sha256-KzZiKy0DWYsnwMF+X1DvQngQ2/FxF7MF3Ff72XcpuPs="
        crossorigin="anonymous"></script>

<c:url var="holderJsLink" value="/js/holder.min.js"/>
<script src="${holderJsLink}"></script>
