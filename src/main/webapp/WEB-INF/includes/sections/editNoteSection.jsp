<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="updateNoteLink" value="/note/update"/>
<div class="d-flex flex-column">
    <c:if test="${!hasWriteAccess}">
        <c:set var="inputReadOnly" value="${true}"/>
    </c:if>

    <form:form cssClass="form" action="${updateNoteLink}" method="post"
               modelAttribute="currentNoteCommand">
        <form:hidden path="id"/>
        <form:hidden path="writer" value="${currentNoteCommand.writer.id}"/>

        <div class="flex-item form-group d-flex flex-row">
            <form:input path="title" readonly="${inputReadOnly}"
                        cssClass="col-6 form-control form-control-lg"/>

            <form:select path="privacy" disabled="${inputReadOnly}"
                         cssClass="col-2 ml-2 form-control form-control-lg">
                <c:forEach var="notePrivacy" items="${Privacy.values()}">
                    <form:option value="${notePrivacy}">
                        <spring:message code="${notePrivacy.name()}"/>
                    </form:option>
                </c:forEach>
            </form:select>

            <div class="col-4">
                <c:if test="${hasWriteAccess}">
                    <button type="submit" class="btn btn-lg btn-success">
                        <em class="fa fa-edit"></em>
                        <span>Update</span>
                    </button>
                </c:if>

                <c:if test="${hasShareAccess}">
                    <button type="button" class="btn btn-lg btn-outline-light btn-dark">
                        <em class="fa fa-share"></em>
                    </button>
                </c:if>

                <c:if test="${hasDeleteAccess}">
                    <c:url var="deleteNoteLink" value="/note/delete/${currentNoteCommand.id}"/>
                    <a href="${deleteNoteLink}" class="btn btn-lg btn-outline-danger">
                        <em class="fa fa-trash"></em>
                    </a>
                </c:if>


            </div>
        </div>

        <div class="form-group">
            <form:textarea rows="20" readonly="${inputReadOnly}" disabled="${inputReadOnly}"
                           path="content.text"
                           cssClass="tinymce-editor form-control form-control-lg"/>
        </div>
    </form:form>

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
            quickbars_insert_toolbar: '',
            quickbars_selection_toolbar: 'image media ' + '| ' +
                'bold italic underline | alignleft aligncenter alignright alignjustify | ' +
                'formatselect | blockquote quicklink',
            contextmenu: 'undo redo | link image media codesample | inserttable | ' +
                'cell row column deletetable | help'
        });
    </script>
</div>