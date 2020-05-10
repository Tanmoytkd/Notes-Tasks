<%--
  @author tanmoy.das
  @since 5/1/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header border-bottom-0">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-title text-center">
                    <h4><spring:message code="label.registerTxt"/></h4>
                </div>
                <div>
                    <form:errors path="registerUserCommand.*" cssClass="alert-danger text-center my-3"
                                 element="div"/><br>
                </div>
                <div class="d-flex flex-column text-center">
                    <c:url value="/register" var="registerLink"/>
                    <form:form method="post" action="${registerLink}" modelAttribute="registerUserCommand">
                        <div class="form-group">
                            <form:input type="text" path="name" class="form-control" id="name"
                                        placeholder="Your Full Name..."/>
                        </div>
                        <div class="form-group">
                            <form:input type="email" path="email" class="form-control" id="email1"
                                        placeholder="Your email address..."/>
                        </div>
                        <div class="form-group">
                            <form:password path="password" class="form-control"
                                           placeholder="Your password..."/>
                        </div>
                        <button type="submit" class="btn btn-info btn-block btn-round">
                            <spring:message code="label.registerTxt"/>
                        </button>
                    </form:form>
                </div>
            </div>
            <div class="modal-footer d-flex justify-content-center">
                <div class="signup-section">
                    <spring:message code="label.alreadyHaveAnAccount"/>
                    <a href="#" class="text-info" data-dismiss="modal" data-toggle="modal" data-target="#loginModal">
                        <spring:message code="label.loginHere"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        // $('#loginModal').modal('show');
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    });
</script>

