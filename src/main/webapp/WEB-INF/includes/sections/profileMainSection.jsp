<%--
  @author tanmoy.das
  @since 5/4/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.Constants" %>
<%@ page import="net.therap.notestasks.util.HashingUtil" %>

<main role="main" class="flex-fill d-flex pt-3 px-4">
    <div class="d-flex flex-fill">
        <c:url var="userLink" value="${Constants.PROFILE_BASE_PATH}/${user.id}"/>
        <div class="card col-6 col-md-6 mx-2 my-1 p-3">
            <c:set var="emailHash" value="${HashingUtil.md5Hex(user.getEmail())}"/>
            <c:url var="profilePictureUrl" value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=800"/>
            <a href="${userLink}">
                <img class="card-img-top" src="${profilePictureUrl}" alt="Avatar from Gravatar">
            </a>
            <div class="small text-muted text-right">
                <span>Profile picture taken from</span>
                <a href="https://en.gravatar.com/" class="text-muted">
                    <u>Gravatar</u>
                </a>
            </div>
            <div class="card-body">
                <c:if test="${!isMyself}">
                    <a href="${userLink}">
                        <h5 class="card-title text-dark font-weight-bold">
                            <c:out value="${user.name}"/>
                        </h5>
                    </a>
                    <p class="card-text">
                        <c:out value="${user.about}"/>
                    </p>
                    <c:choose>
                        <c:when test="${isUserConnected}">
                            <c:url var="removeConnectionLink" value="${Constants.REMOVE_CONNECTION_PATH}/${user.id}"/>
                            <a href="${removeConnectionLink}" class="btn btn-danger">
                                <spring:message code="label.removeConnection"/>
                            </a>

                            <c:url var="showUserMessagesLink" value="${Constants.MESSAGES_BASE_PATH}/${user.id}"/>
                            <a href="${showUserMessagesLink}" class="btn btn-secondary">
                                <em class="fa fa-envelope"></em>
                                Send Message
                            </a>
                        </c:when>
                        <c:when test="${isRequestSent}">
                            <c:url var="cancelConnectionRequestLink" value="${Constants.CANCEL_CONNECTION_PATH}/${user.id}"/>
                            <a href="${cancelConnectionRequestLink}" class="btn btn-info">
                                <spring:message code="label.cancelConnectionRequest"/>
                            </a>
                        </c:when>
                        <c:when test="${isRequestReceived}">
                            <c:url var="acceptConnectionReqeustLink" value="${Constants.ACCEPT_CONNECTION_PATH}/${user.id}"/>
                            <a href="${acceptConnectionReqeustLink}" class="btn btn-success">
                                <spring:message code="label.acceptConnection"/>
                            </a>

                            <c:url var="rejectConnectionRequestLink" value="${Constants.REJECT_CONNECTION_PATH}/${user.id}"/>
                            <a href="${rejectConnectionRequestLink}" class="btn btn-danger">
                                <spring:message code="label.rejectConnection"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <c:url var="sendConnectionRequestLink" value="${Constants.SEND_CONNECTION_REQUEST_PATH}/${user.id}"/>
                            <a href="${sendConnectionRequestLink}" class="btn btn-primary">
                                <spring:message code="label.sendConnectionRequest"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="">

                    </c:if>

                </c:if>

                <c:if test="${isMyself}">
                    <c:url var="updateProfileLink" value="${Constants.UPDATE_PROFILE_PATH}"/>

                    <form:errors path="currentUserCommand.*" cssClass="alert-danger" element="div"/>

                    <form:form action="${updateProfileLink}" method="post" cssClass="form"
                               modelAttribute="currentUserCommand">

                        <form:hidden path="id"/>

                        <div class="form-group">
                            <form:label path="name"><spring:message code="label.nameTxt"/>: </form:label>
                            <form:input path="name" type="text" cssClass="form-control my-2" placeholder="Name"/>
                        </div>

                        <div class="form-group">
                            <form:label path="email"><spring:message code="label.emailTxt"/>: </form:label>
                            <form:input path="email" type="email" cssClass="form-control my-2" readonly="true"/>
                        </div>

                        <div class="form-group">
                            <form:label path="about"><spring:message code="label.aboutTxt"/>: </form:label>
                            <form:textarea path="about" type="text" cssClass="form-control my-2"
                                           placeholder="About you..."/>
                        </div>

                        <div class="form-group">
                            <label for="password"><spring:message code="label.passwordTxt"/>: </label>
                            <form:password path="password" cssClass="form-control"
                                           placeholder="Enter Password to update profile"/>
                        </div>

                        <div class="form-group">
                            <label for="newPassword">New Password (Optional)</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword"
                                   placeholder="New Password">
                        </div>

                        <button type="submit" class="btn btn-primary my-3">Update Profile</button>
                    </form:form>
                </c:if>


            </div>
        </div>

        <div class="flex-fill flex-fill">
            <div class="accordion" id="accordionExample">
                <div class="card">
                    <div class="card-header" id="headingOne">
                        <div class="mb-0">
                            <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne"
                                    aria-expanded="true" aria-controls="collapseOne">
                                <strong><spring:message code="label.connections"/></strong>
                            </button>
                        </div>
                    </div>

                    <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                        <div class="card-body">
                            <div class="d-flex flex-column">
                                <c:forEach items="${connectedUsers}" var="user">
                                    <c:url var="userLink" value="${Constants.PROFILE_BASE_PATH}/${user.id}"/>

                                    <div class="flex-fill d-flex my-2">
                                        <c:set var="emailHash" value="${HashingUtil.md5Hex(user.getEmail())}"/>
                                        <c:url var="profilePictureUrl"
                                               value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=64"/>
                                        <a href="${userLink}">
                                            <img class="card-img-top" src="${profilePictureUrl}"
                                                 alt="Avatar from Gravatar">
                                        </a>
                                        <div class="d-flex flex-column flex-fill mx-2">
                                            <a href="${userLink}">
                                                <h5 class="card-title text-dark font-weight-bold">
                                                    <c:out value="${user.name}"/>
                                                </h5>
                                            </a>
                                            <p class="card-text">
                                                <c:out value="${user.about}"/>
                                            </p>
                                        </div>
                                        <a href="${userLink}" class="btn btn-primary">
                                            <spring:message code="label.viewProfile"/>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <div class="card-header" id="headingTwo">
                        <div class="mb-0">
                            <button class="btn btn-link collapsed" type="button" data-toggle="collapse"
                                    data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                <strong><spring:message code="label.notes"/></strong>
                            </button>
                        </div>
                    </div>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
                        <div class="card-body">
                            <div class="d-flex flex-column list-group">
                                <c:forEach items="${accessibleNotes}" var="note">
                                    <div class="d-flex list-group-item">
                                        <c:url var="noteLink" value="${Constants.NOTE_BASE_PATH}/${note.id}"/>
                                        <a href="${noteLink}">
                                            <h2>${note.title}</h2>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</main>