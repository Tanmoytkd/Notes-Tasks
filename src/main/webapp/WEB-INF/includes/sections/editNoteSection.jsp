<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.Constants" %>
<%@ page import="net.therap.notestasks.domain.Privacy" %>

<c:url var="updateNoteLink" value="${Constants.UPDATE_NOTE_PATH}"/>
<div class="d-flex flex-column">
    <form:form cssClass="form" action="${updateNoteLink}" method="post"
               modelAttribute="noteCommand">
        <form:hidden path="id"/>
        <form:hidden path="writer" value="${noteCommand.writer.id}"/>

        <div class="flex-item form-group d-flex flex-row">
            <form:input path="title" readonly="${!hasWriteAccess}"
                        cssClass="col-6 form-control form-control-lg"/>


            <form:select path="privacy" disabled="${!hasWriteAccess}"
                         cssClass="col-2 ml-2 form-control form-control-lg">
                <c:forEach var="notePrivacy" items="${Privacy.values()}">
                    <form:option value="${notePrivacy}">
                        <spring:message code="${notePrivacy.name()}"/>
                    </form:option>
                </c:forEach>
            </form:select>

            <div class="col-4">
                <c:if test="${hasWriteAccess==true}">
                    <button type="submit" class="btn btn-lg btn-success">
                        <em class="fa fa-edit"></em>
                        <span>Update</span>
                    </button>
                </c:if>

                <c:if test="${hasShareAccess}">
                    <button type="button" class="btn btn-lg btn-outline-light btn-dark"
                            data-toggle="modal" data-target="#noteShareModal">
                        <em class="fa fa-share"></em>
                    </button>
                </c:if>

                <c:if test="${hasDeleteAccess}">
                    <c:url var="deleteNoteLink" value="${Constants.DELETE_NOTE_PATH}/${noteCommand.id}"/>
                    <a href="${deleteNoteLink}" class="btn btn-lg btn-outline-danger">
                        <em class="fa fa-trash"></em>
                    </a>
                </c:if>


            </div>
        </div>


        <c:url var="noteCommandWriterLink" value="${Constants.PROFILE_BASE_PATH}/${noteCommand.writer.id}"/>
        <small class="text-muted">
            <span>Author: </span>
            <a href="${noteCommandWriterLink}">${noteCommand.writer.name}</a>
        </small>


        <div class="form-group">
            <form:textarea rows="15"
                           path="content.text"
                           cssClass="tinymce-editor form-control form-control-lg"/>
        </div>
    </form:form>

    <%@ include file="/WEB-INF/includes/sections/forms/noteShareForm.jsp" %>

    <c:set var="tinymceReadOnly" value="${hasWriteAccess ? 0 : 1}"/>

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