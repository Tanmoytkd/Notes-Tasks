<%--
  @author tanmoy.das
  @since 5/3/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.Constants" %>
<%@ page import="net.therap.notestasks.util.HashingUtil" %>

<main role="main" class="col-md-9 col-lg-10 pt-3 px-4">
    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap
                    align-items-center pb-2 mb-3 border-bottom">
        <h1 class="h2">
            <spring:message code="label.searchResults"/>: <c:out value="${searchQuery.query}"/>
        </h1>
    </div>

    <div class="row">
        <c:forEach items="${users}" var="user">
            <c:url var="userLink" value="${Constants.PROFILE_BASE_PATH}/${user.id}"/>

            <div class="card m-1" style="width: 18rem;">
                <c:set var="emailHash" value="${HashingUtil.md5Hex(user.getEmail())}"/>
                <c:url var="profilePictureUrl" value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=200"/>
                <a href="${userLink}">
                    <img class="card-img-top" src="${profilePictureUrl}" alt="Avatar from Gravatar">
                </a>

                <div class="card-body">
                    <a href="${userLink}">
                        <h5 class="card-title text-dark font-weight-bold">
                                ${user.name}
                        </h5>
                    </a>
                    <p class="card-text">
                            ${user.about}
                    </p>
                    <a href="${userLink}" class="btn btn-primary w-100">
                        View Profile
                    </a>
                </div>
            </div>
        </c:forEach>

    </div>
</main>