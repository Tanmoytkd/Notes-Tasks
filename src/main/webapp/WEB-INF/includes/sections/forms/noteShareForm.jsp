<%--
  @author tanmoy.das
  @since 5/1/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.domain.AccessLevel" %>

<div class="modal fade" id="noteShareModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
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
                    <h4><spring:message code="label.shareNoteTxt"/></h4>
                </div>
                <div>
                    <form:errors path="noteAccessCommand.*" cssClass="alert-danger text-center my-3" element="div"/>
                </div>
                <div class="d-flex flex-column text-center">
                    <c:url var="noteAccessLink" value="/noteAccess"/>
                    <form:form method="post" action="${noteAccessLink}" modelAttribute="noteAccessCommand">
                        <form:hidden path="note" value="${noteAccessCommand.note.id}"/>

                        <div class="form-group">
                            <form:select path="user" cssClass="form-control form-control-lg">
                                <c:forEach items="${connectedUsers}" var="connectedUser">
                                    <form:option value="${connectedUser.id}">${connectedUser.name}</form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <form:select path="accessLevels" cssClass="form-control form-control-lg">
                                <c:forEach var="level" items="${AccessLevel.values()}">
                                    <form:option value="${level}">
                                        <spring:message code="${level.name()}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>
                            <small class="small text-muted">Ctrl+Click to select multiple options</small>
                        </div>
                        <button type="submit" class="btn btn-info btn-block btn-round">
                            <spring:message code="label.shareNoteTxt"/>
                        </button>
                    </form:form>
                </div>
            </div>
            <div class="modal-footer d-flex justify-content-center">
                <div>Shared with:</div>
                <c:forEach items="${noteAccesses}" var="noteAccess">
                    <span class="btn btn-round btn-xs">
                        <c:url var="noteAccessUserLink" value="/user/${noteAccess.user.id}"/>
                        <a href="${noteAccessUserLink}">${noteAccess.user.name}</a>

                        <c:forEach items="${noteAccess.accessLevels}" var="level">
                            <span class="text-muted">(<spring:message code="${level}"/>)</span>
                        </c:forEach>
                        <c:url var="noteAccessDeleteLink" value="/noteAccess/delete/${noteAccess.id}"/>
                        <a href="${noteAccessDeleteLink}">
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

