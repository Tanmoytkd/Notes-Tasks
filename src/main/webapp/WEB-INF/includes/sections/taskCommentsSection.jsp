<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.Constants" %>

<div class="d-flex flex-column">
    <form:errors cssClass="alert-danger" element="div" path="taskCommentCommand.*"/>
    <c:url var="createTaskCommentLink" value="${Constants.CREATE_TASK_COMMENT_PATH}"/>
    <form:form modelAttribute="taskCommentCommand" cssClass="form-inline flex-fill d-flex px-1"
               action="${createTaskCommentLink}" method="post">
        <form:hidden path="writer" value="${currentUser.id}"/>
        <form:hidden path="task" value="${taskCommand.id}"/>
        <form:input path="content.text" cssClass="form-control form-control-lg flex-fill"/>
        <button class="btn btn-lg btn-info" type="submit">
            <spring:message code="label.comment"/>
        </button>
    </form:form>

    <div class="d-flex flex-column">
        <c:forEach var="taskComment" items="${taskComments}">
            <div class="card flex-fill p-1 m-1 mt-2 bg-light">
                <div class="card-body">
                    <div class="d-flex align-content-center">
                        <c:set var="emailHash" value="${HashingUtil.md5Hex(taskComment.writer.email)}"/>
                        <c:url var="profilePictureUrl"
                               value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=64"/>
                        <div class="mx-3">
                            <img class="img-thumbnail" src="${profilePictureUrl}"
                                 alt="Avatar from Gravatar">
                        </div>
                        <div class="flex-fill">
                            <c:url var="noteCommentWriterLink"
                                   value="${Constants.PROFILE_BASE_PATH}/${taskComment.writer.id}"/>
                            <div class="card-title">
                                <a href="${noteCommentWriterLink}">
                                    <h5 class="mb-0 pb-0">
                                        <c:out value="${taskComment.writer.name}"/>
                                    </h5>
                                </a>
                                <small class="text-muted">
                                    <c:out value="${taskComment.updatedOn}"/>
                                </small>
                            </div>

                            <p class="card-text">
                                <c:out value="${taskComment.content.text}"/>
                            </p>
                        </div>
                        <c:if test="${taskService.canDeleteTaskComment(currentUser, taskComment)}">
                            <div>
                                <c:url var="deleteNoteCommentLink"
                                       value="${Constants.DELETE_TASK_COMMENT_PATH}/${taskComment.id}"/>
                                <a href="${deleteNoteCommentLink}">
                                    <em class="fa fa-trash fa-2x text-muted"></em>
                                </a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>