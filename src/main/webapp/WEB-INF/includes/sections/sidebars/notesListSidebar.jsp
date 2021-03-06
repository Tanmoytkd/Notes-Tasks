<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.Constants" %>

<div class="d-flex flex-column flex-shrink-0 mb-3 mh-100 overflow-auto">
    <div class="card flex-fill vh-100">
        <div class="card-header p-1 justify-content-center">
            <span>Own Notes</span>

            <c:url var="createNoteLink" value="${Constants.CREATE_NOTE_PATH}"/>
            <a href="${createNoteLink}">
                <em class="fa fa-plus"></em>
            </a>
        </div>
        <ul class="list-group list-group-flush">
            <c:forEach items="${ownNotes}" var="note">
                <li class="list-group-item">
                    <div class="row justify-content-center">

                        <div class="col-11">
                            <c:url var="noteLink" value="${Constants.NOTE_BASE_PATH}/${note.id}"/>
                            <a href="${noteLink}">
                                <h6 class="text-dark font-weight-bold">${note.title}</h6>
                            </a>
                        </div>

                        <div class="col-1">
                            <c:url var="deleteNoteLink" value="${Constants.DELETE_NOTE_PATH}/${note.id}"/>
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
                    <c:url var="sharedNoteLink" value="${Constants.NOTE_BASE_PATH}/${note.id}"/>

                    <div class="row d-flex">
                        <div class="flex-fill m-3">
                            <a href="${sharedNoteLink}">
                                <h6 class="text-dark font-weight-bold">${note.title}</h6>
                                <small class="text-muted">${note.writer.name}</small>
                            </a>
                        </div>
                        <c:url var="deleteNoteAccessLink"
                               value="${Constants.DELETE_NOTE_ACCESS_PATH}/${noteAccess.id}"/>
                        <div>
                            <a href="${deleteNoteAccessLink}" class="mx-2 text-info text-lg">
                                <em class="fa fa-times"></em>
                            </a>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
