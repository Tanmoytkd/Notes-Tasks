<%--
  @author tanmoy.das
  @since 5/4/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.HashingUtil" %>

<main role="main" class="col-md-9 col-lg-10 pt-3 px-4">
    <div class="row">
        <c:url var="userLink" value="/profile/${user.id}"/>

        <div class="card col-6 col-md-6 mx-2 my-1 p-3">
            <c:set var="emailHash" value="${HashingUtil.md5Hex(user.getEmail())}"/>
            <c:url var="profilePictureUrl" value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=800"/>
            <a href="${userLink}">
                <img class="card-img-top" src="${profilePictureUrl}" alt="Avatar from Gravatar">
            </a>
            <div class="small text-muted text-right">Profile picture taken from <a href="https://en.gravatar.com/"
                                                                                   class="text-muted"><u>Gravatar</u></a>
            </div>
            <div class="card-body">
                <c:if test="${!isMyself}">
                    <a href="${userLink}">
                        <h5 class="card-title text-dark font-weight-bold">
                                ${user.name}
                        </h5>
                    </a>
                    <p class="card-text">
                            ${user.about}
                    </p>
                    <c:choose>
                        <c:when test="${isUserConnected}">
                            <c:url var="removeConnectionLink" value="/connection/remove/${user.id}"/>
                            <a href="${removeConnectionLink}" class="btn btn-danger">
                                <spring:message code="label.removeConnection"/>
                            </a>

                            <c:url var="showUserMessagesLink" value="/messages/${user.id}"/>
                            <a href="${showUserMessagesLink}" class="btn btn-secondary">
                                <em class="fa fa-envelope"></em>
                                Send Message
                            </a>
                        </c:when>
                        <c:when test="${isRequestSent}">
                            <c:url var="cancelConnectionRequestLink" value="/connection/cancel/${user.id}"/>
                            <a href="${cancelConnectionRequestLink}" class="btn btn-info">
                                <spring:message code="label.cancelConnectionRequest"/>
                            </a>
                        </c:when>
                        <c:when test="${isRequestReceived}">
                            <c:url var="acceptConnectionReqeustLink" value="/connection/accept/${user.id}"/>
                            <a href="${acceptConnectionReqeustLink}" class="btn btn-success">
                                <spring:message code="label.acceptConnection"/>
                            </a>

                            <c:url var="rejectConnectionRequestLink" value="/connection/reject/${user.id}"/>
                            <a href="${rejectConnectionRequestLink}" class="btn btn-danger">
                                <spring:message code="label.rejectConnection"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <c:url var="sendConnectionRequestLink" value="/connection/send/${user.id}"/>
                            <a href="${sendConnectionRequestLink}" class="btn btn-primary">
                                <spring:message code="label.sendConnectionRequest"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="">

                    </c:if>

                </c:if>

                <c:if test="${isMyself}">
                    <c:url var="updateProfileLink" value="/profile/update"/>

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
                            <input id="password" name="password" type="password" class="form-control" value=""
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

    </div>
</main>