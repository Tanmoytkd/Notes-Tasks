<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="updateTaskLink" value="/task/update"/>
<div class="d-flex flex-column">
    <c:if test="${!hasWriteAccess}">
        <c:set var="inputReadOnly" value="${true}"/>
    </c:if>

    <form:form cssClass="form" action="${updateTaskLink}" method="post"
               modelAttribute="taskCommand">
        <form:hidden path="id"/>
        <form:hidden path="creator" value="${taskCommand.creator.id}"/>

        <div class="flex-item form-group d-flex flex-row align-content-center">

            <form:input path="title" readonly="${inputReadOnly}"
                        cssClass="col-6 form-control form-control-lg"/>


            <div class="col-6">
                <c:if test="${hasWriteAccess}">
                    <button type="submit" class="btn btn-lg btn-success">
                        <em class="fa fa-edit"></em>
                        <span>Update</span>
                    </button>

                    <div class="btn btn-light form-group d-inline-flex align-content-center mb-0 py-0">
                        <form:checkbox path="completed" cssClass="form-control form-check-inline"/>
                        <form:label path="completed" cssClass="form-check-label">Mark as Complete</form:label>
                    </div>
                </c:if>

                <c:if test="${hasAssignmentAccess}">
                    <button type="button" class="btn btn-lg btn-outline-light btn-outline-dark"
                            data-toggle="modal" data-target="#taskAssignmentModal">
                        <em class="fa fa-user-plus"></em>
                    </button>
                </c:if>

                <c:if test="${hasDeleteAccess}">
                    <c:url var="deleteTaskLink" value="/task/delete/${taskCommand.id}"/>
                    <a href="${deleteTaskLink}" class="btn btn-lg btn-outline-danger">
                        <em class="fa fa-trash"></em>
                    </a>
                </c:if>


            </div>
        </div>

        <c:url var="taskCommandCreatorLink" value="/profile/${taskCommand.creator.id}"/>
        <small class="text-muted">
            <span>Creator:</span>
            <a href="${taskCommandCreatorLink}">${taskCommand.creator.name}</a>
        </small>


        <div class="form-group">
            <form:textarea rows="15" readonly="${inputReadOnly}" disabled="${inputReadOnly}"
                           path="description"
                           cssClass="tinymce-editor form-control form-control-lg"/>
        </div>
    </form:form>

    <%@ include file="/WEB-INF/includes/sections/forms/taskAssignmentForm.jsp" %>

    <c:set var="tinymceReadOnly" value="${inputReadOnly ? 1 : 0}"/>

    <script>
        tinymce.init({
            selector: '.tinymce-editor',
            plugins: [
                'quickbars',
                'autolink',
                'codesample',
                'link',
                'lists',
                'media',
                'table',
                'image',
                'quickbars',
                'codesample',
                'help'
            ],
            toolbar: false,
            menubar: false,
            quickbars_selection_toolbar: 'image media ' + '| ' +
                'bold italic underline | alignleft aligncenter alignright alignjustify | ' +
                'formatselect | blockquote quicklink',
            contextmenu: 'undo redo | link image media codesample | inserttable | ' +
                'cell row column deletetable | help',
            readonly: ${tinymceReadOnly}
        });
    </script>
</div>