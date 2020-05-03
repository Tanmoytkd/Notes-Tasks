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
    <%--    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap--%>
    <%--                    align-items-center pb-2 mb-3 border-bottom">--%>
    <%--        <h1 class="h2">--%>
    <%--            <spring:message code="label.searchResults"/>: ${searchQuery.query}--%>
    <%--        </h1>--%>
    <%--    </div>--%>

    <div class="row">
        <c:url var="userLink" value="/profile/${user.id}"/>

        <div class="card col-6 col-md-6 mx-2 my-1 p-3">
            <c:set var="emailHash" value="${HashingUtil.md5Hex(user.getEmail())}"/>
            <c:url var="profilePictureUrl" value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=200"/>
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