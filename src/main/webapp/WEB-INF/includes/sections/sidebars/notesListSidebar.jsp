<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="d-flex flex-column flex-shrink-0 mb-3 mh-100 overflow-auto">
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
                    <c:url var="sharedNoteLink" value="/note/${note.id}"/>
                    <a href="${sharedNoteLink}">
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
