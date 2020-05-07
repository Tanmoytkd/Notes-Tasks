<%--
  @author tanmoy.das
  @since 5/1/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="modal fade" id="taskAssignmentModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
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
                    <h4><spring:message code="label.assignTaskTxt"/></h4>
                </div>
                <div>
                    <form:errors path="taskAssignmentCommand.*" cssClass="alert-danger text-center my-3" element="div"/>
                </div>
                <div class="d-flex flex-column text-center">
                    <c:url var="taskAssignmentLink" value="/taskAssignment"/>
                    <form:form method="post" action="${taskAssignmentLink}" modelAttribute="taskAssignmentCommand">
                        <form:hidden path="task" value="${taskAssignmentCommand.task.id}"/>

                        <div class="form-group">
                            <form:select path="user" cssClass="form-control form-control-lg">
                                <c:forEach items="${connectedUsers}" var="connectedUser">
                                    <form:option value="${connectedUser.id}">${connectedUser.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <button type="submit" class="btn btn-info btn-block btn-round">
                            <spring:message code="label.assignTaskTxt"/>
                        </button>
                    </form:form>
                </div>
            </div>
            <div class="modal-footer d-flex justify-content-center">
                <div>Assigned to:</div>
                <c:forEach items="${taskAssignments}" var="taskAssignment">
                    <span class="btn btn-round btn-xs">
                        <c:url var="taskAssignmentUserLink" value="/user/${taskAssignment.user.id}"/>
                        <a href="${taskAssignmentUserLink}">${taskAssignment.user.name}</a>

                        <c:choose>
                            <c:when test="${taskAssignment.completed}">
                                <span class="text-muted">(Complete)</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-muted">(Incomplete)</span>
                            </c:otherwise>
                        </c:choose>


                        <c:url var="taskAssignmentDeleteLink" value="/taskAssignment/delete/${taskAssignment.id}"/>
                        <a href="${taskAssignmentDeleteLink}">
                            <em class="fa fa-times"></em>
                        </a>
                    </span>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        // $('#noteShareModal').modal('show');
        $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        })
    });
</script>

