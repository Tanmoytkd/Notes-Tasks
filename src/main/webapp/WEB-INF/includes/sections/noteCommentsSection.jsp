<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="d-flex flex-column">
    <form:errors cssClass="alert-danger" element="div" path="noteCommentCommand.*"/>
    <c:url var="noteCommentLink" value="/noteComment"/>
    <form:form modelAttribute="noteCommentCommand" cssClass="form-inline flex-fill d-flex px-1"
               action="${noteCommentLink}" method="post">
        <form:hidden path="writer" value="${currentUserCommand.id}"/>
        <form:hidden path="note" value="${noteCommand.id}"/>
        <form:input path="content.text" cssClass="form-control form-control-lg flex-fill"/>
        <button class="btn btn-lg btn-info" type="submit">
            <spring:message code="label.comment"/>
        </button>
    </form:form>

    <div class="d-flex flex-column">
        <c:forEach var="noteComment" items="${noteComments}">
            <div class="card flex-fill p-1 m-1 mt-2 bg-light">
                <div class="card-body">
                    <div class="d-flex align-content-center">
                        <c:set var="emailHash" value="${HashingUtil.md5Hex(noteComment.writer.email)}"/>
                        <c:url var="profilePictureUrl"
                               value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=64"/>
                        <div class="mx-3">
                            <img class="img-thumbnail" src="${profilePictureUrl}"
                                 alt="Avatar from Gravatar">
                        </div>
                        <div class="flex-fill">
                            <c:url var="noteCommentWriterLink" value="/user/${notecomment.writer.id}"/>
                            <div class="card-title">
                                <a href="${noteCommentWriterLink}">

                                    <h5 class="mb-0 pb-0">${noteComment.writer.name}</h5>

                                </a>
                                <small class="text-muted">${noteComment.updatedOn}</small>
                            </div>

                            <p class="card-text">${noteComment.content.text}</p>
                        </div>
                        <c:if test="${noteService.hasCommentDeleteAccess(currentUserCommand, noteComment)}">
                            <div>
                                <c:url var="deleteNoteCommentLink" value="/noteComment/delete/${noteComment.id}"/>
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

