<%--
  @author tanmoy.das
  @since 4/12/20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="<spring:message code="label.appName"/>">
    <meta name="author" content="<spring:message code="label.authorName"/>">

    <c:url var="iconLink" value="/img/spidey.png"/>
    <link rel="icon" href="${iconLink}">

    <title><spring:message code="label.welcome"/></title>
</head>

<body>

<header>
    <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-md-7 py-4">
                    <h4 class="text-white"><spring:message code="label.appName"/></h4>
                    <p class="text-muted"><spring:message code="label.appDescription"/></p>
                </div>
                <div class="col-sm-4 offset-md-1 py-4 justify-content-end">
                    <div>
                        <c:url var="bnLink" value="?locale=bn"/>
                        <a href="${bnLink}">
                            <c:url var="bdFlagLink" value="/img/bd-flag.png"/>
                            <img src="${bdFlagLink}" class="" alt="Bengali">
                        </a>

                        <c:url var="enLink" value="?locale=en"/>
                        <a href="${enLink}">
                            <c:url var="usFlagLink" value="/img/us-flag.png"/>
                            <img src="${usFlagLink}" class="" alt="English">
                        </a>
                    </div>
                    <ul class="list-unstyled justify-content-end">
                        <li>
                            <a href="#" class="text-white" data-toggle="modal" data-target="#loginModal">
                                <spring:message code="label.loginTxt"/>
                            </a>
                        </li>


                        <li>
                            <a href="#" class="text-white" data-toggle="modal" data-target="#registerModal">
                                <spring:message code="label.registerTxt"/>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
            <c:url var="rootLink" value="/"/>
            <a href="${rootLink}" class="navbar-brand d-flex align-items-center">
                <strong><em class="fas fa-home fa-2x"></em></strong>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader"
                    aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </div>
    </div>
</header>

<main role="main">

    <section class="jumbotron text-center">
        <div class="container">
            <h1 class="jumbotron-heading"><spring:message code="label.appName"/></h1>
            <p class="lead text-muted">
                <spring:message code="label.appDescription"/>
            </p>
            <p>
                <a href="#" class="btn btn-primary my-2" data-toggle="modal" data-target="#loginModal">
                    <spring:message code="label.loginTxt"/>
                </a>
                <a href="#" class="btn btn-secondary my-2" data-toggle="modal" data-target="#registerModal">
                    <spring:message code="label.registerTxt"/>
                </a>
            </p>
        </div>
    </section>

    <div class="album py-5 bg-light">
        <div class="container">

            <div class="row">
                <div class="col-md-4">
                    <div class="card mb-4 box-shadow">
                        <img class="card-img-top"
                             data-src="holder.js/100px225?theme=thumb&bg=55595c&fg=eceeef&text=Notes"
                             alt="Card image cap">
                        <div class="card-body">
                            <p class="card-text"><spring:message code="text.notesDescription"/></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                </div>
                                <small class="text-muted"></small>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card mb-4 box-shadow">
                        <img class="card-img-top"
                             data-src="holder.js/100px225?theme=thumb&bg=55595c&fg=eceeef&text=Tasks"
                             alt="Card image cap">
                        <div class="card-body">
                            <p class="card-text"><spring:message code="text.tasksDescription"/></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                </div>
                                <small class="text-muted"></small>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card mb-4 box-shadow">
                        <img class="card-img-top"
                             data-src="holder.js/100px225?theme=thumb&bg=55595c&fg=eceeef&text=Connect"
                             alt="Card image cap">
                        <div class="card-body">
                            <p class="card-text"><spring:message code="text.connectionDescription"/></p>
                            <div class="d-flex justify-content-between align-items-center">
                                <div class="btn-group">
                                </div>
                                <small class="text-muted"></small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/includes/loginForm.jsp" %>
    <%@ include file="/WEB-INF/includes/registerForm.jsp" %>

</main>

<footer class="text-muted">
    <div class="container">
        <p class="float-right">
            <a href="#"><spring:message code="label.backToTop"/></a>
        </p>
        <p>&copy;<spring:message code="label.authorName"/></p>
    </div>
</footer>
</body>
</html>
