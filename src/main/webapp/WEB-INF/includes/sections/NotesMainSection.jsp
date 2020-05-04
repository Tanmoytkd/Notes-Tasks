<%--
  @author tanmoy.das
  @since 5/4/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.domain.Privacy" %>

<main role="main" class="col-md-10 col-xl-11 vh-100 pt-3 px-4">
    <%--    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap--%>
    <%--                    align-items-center pb-2 mb-3 border-bottom">--%>
    <%--        <h1 class="h2">--%>
    <%--            <spring:message code="label.searchResults"/>: ${searchQuery.query}--%>
    <%--        </h1>--%>
    <%--    </div>--%>

    <div class="container-fluid d-flex flex-column flex-grow-1 vh-100 overflow-hidden">
        <div class="row flex-grow-1 overflow-hidden">
            <div class="col-md-4 mh-100 overflow-auto">
                <div class="card flex-fill vh-100">
                    <div class="card-header p-1 justify-content-center">
                        <span>Own Notes</span>

                        <c:url var="createNoteLink" value="/note/new"/>
                        <a href="${createNoteLink}">
                            <em class="fa fa-plus"></em>
                        </a>
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach items="${ownNotes}" var="note">
                            <li class="list-group-item">
                                <div class="row justify-content-center">

                                    <div class="col-11">
                                        <c:url var="noteLink" value="/note/${note.id}"/>
                                        <a href="${noteLink}">
                                            <h6 class="text-dark font-weight-bold">${note.title}</h6>
                                        </a>
                                    </div>

                                    <div class="col-1">
                                        <c:url var="deleteNoteLink" value="/note/delete/${note.id}"/>
                                        <a href="${deleteNoteLink}">
                                            <em class="fa fa-trash"></em>
                                        </a>
                                    </div>

                                </div>

                            </li>
                        </c:forEach>
                    </ul>


                    <div class="card-header p-1 justify-content-center">
                        Shared Notes from Others
                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach items="${sharedNoteAccesses}" var="noteAccess">
                            <c:set var="note" value="${noteAccess.note}"/>
                            <li class="list-group-item">
                                <c:url var="userMessageLink" value="/notes/${note.id}"/>
                                <a href="${noteLink}">
                                    <div class="row">
                                        <div class="col">
                                            <h6 class="text-dark font-weight-bold">${note.title}</h6>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>


            <div class="col-md-8 d-flex flex-column vh-75 mh-100 overflow-auto py-2">
                <c:if test="${currentNoteCommand!=null}">
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
                                <form:textarea rows="25" readonly="${inputReadOnly}" disabled="${inputReadOnly}"
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

                            // var quill = new Quill('#editor', {
                            //     theme: 'snow'
                            // });
                            //
                            // let content = $('.editor')[0].value;
                            // if (content !== '') {
                            //     quill.setContents(JSON.parse(content));
                            // }
                            // quill.on('text-change', function (delta, oldDelta, source) {
                            //     $(".editor")[0].value = JSON.stringify(oldDelta.concat(delta));
                            // });
                        </script>
                    </div>
                </c:if>

                <c:if test="${currentMessagedUser!=null}">
                    <div class="flex-item">
                        <c:url var="sendMessageLink" value="/message"/>
                        <form:form action="${sendMessageLink}" method="post" cssClass="flex-row form-inline w-100 my-1"
                                   modelAttribute="messageCommand">
                            <form:hidden path="sender" value="${currentUserCommand.id}"/>
                            <form:hidden path="receiver" value="${currentMessagedUser.id}"/>

                            <form:textarea path="content.text"
                                           cssClass="flex-item flex-fill form-control rounded form-control-dark"
                                           type="text"
                                           placeholder="Search" aria-label="Search" required="required"/>
                            <button type="submit" class="btn btn-info align-self-center mx-2" style="height: 4rem">
                                <spring:message code="label.send"/>
                            </button>
                        </form:form>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</main>