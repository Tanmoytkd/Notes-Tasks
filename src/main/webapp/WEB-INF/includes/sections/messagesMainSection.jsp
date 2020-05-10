<%--
  @author tanmoy.das
  @since 5/4/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<main role="main" class="col-md-10 col-xl-11 vh-100 pt-3 px-4">
    <div class="d-flex flex-column flex-grow-1 vh-100 overflow-hidden">
        <div class="row flex-grow-1 overflow-hidden">
            <div class="col-md-4 mh-100 overflow-auto">
                <div class="card flex-fill vh-100">
                    <div class="card-header p-1 justify-content-center">

                        <c:url var="searchLink" value="/search"/>
                        <form:form action="${searchLink}" method="get" cssClass="form-inline w-100 mx-3 my-1"
                                   modelAttribute="searchQuery">
                            <form:input path="query" cssClass="form-control rounded form-control-dark w-75" type="text"
                                        placeholder="Search" aria-label="Search" required="required"/>
                            <button type="submit" class="btn btn-info mx-2">
                                <em class="fa fa-search"></em>
                            </button>
                        </form:form>

                    </div>
                    <ul class="list-group list-group-flush">
                        <c:forEach items="${allMessagedUsers}" var="messagedUser">
                            <li class="list-group-item">
                                <c:url var="userMessageLink" value="/messages/${messagedUser.id}"/>
                                <a href="${userMessageLink}">
                                    <div class="row">
                                        <c:set var="emailHash" value="${HashingUtil.md5Hex(messagedUser.getEmail())}"/>
                                        <c:url var="profilePictureUrl"
                                               value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=64"/>
                                        <div class="col-2">
                                            <img class="img-thumbnail" src="${profilePictureUrl}"
                                                 alt="Avatar from Gravatar">
                                        </div>
                                        <div class="col-10">
                                            <h6 class="text-dark font-weight-bold">${messagedUser.name}</h6>
                                            <p class="text-black-50 text-muted">
                                                <c:set var="userMessages"
                                                       value="${allMessageGroupedByUsers.get(messagedUser)}"/>
                                                    ${userMessages.get(userMessages.size()-1).getContent().getText()}
                                            </p>
                                        </div>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>


            <div class="col-md-8 d-flex flex-column vh-75 mh-100 overflow-auto py-2">
                <c:if test="${currentMessagedUser!=null}">
                    <div class="flex-item">
                        <c:url var="sendMessageLink" value="/message"/>
                        <form:form action="${sendMessageLink}" method="post" cssClass="flex-row form-inline w-100 my-1"
                                   modelAttribute="messageCommand">
                            <form:hidden path="sender" value="${currentUser.id}"/>
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

                    <%--all messages--%>
                    <div data-simplebar class="flex-item flex-column flex-shrink-1 my-3 vh-75 mh-100">
                        <c:forEach items="${messages}" var="message">

                            <c:choose>
                                <c:when test="${message.sender.id == currentUser.id}">
                                    <c:set var="messageClass" value="flex-row"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="messageClass" value="flex-row-reverse"/>
                                </c:otherwise>
                            </c:choose>

                            <div class="flex-item my-2 d-flex ${messageClass} align-content-center">
                                <c:set var="emailHash" value="${HashingUtil.md5Hex(message.getSender().getEmail())}"/>
                                <c:url var="profilePictureUrl"
                                       value="https://www.gravatar.com/avatar/${emailHash}.jpg?s=40"/>
                                <div class="flex-item">
                                    <img class="rounded-circle" src="${profilePictureUrl}"
                                         alt="Avatar from Gravatar">
                                </div>
                                <div class="flex-item mx-2">
                                    <div class="d-flex flex-column">
                                        <p class="btn btn-primary btn-round mb-0">
                                                ${message.content.text}
                                        </p>
                                        <small class="text-muted mt-0 mb-2">${message.updatedOn}</small>
                                    </div>
                                </div>
                                <div class="flex-item mx-2">
                                    <c:if test="${message.sender.id == currentUser.id}">
                                        <c:url var="messageDeleteLink" value="/message/delete/${message.id}"/>
                                        <a href="${messageDeleteLink}">
                                            <em class="fa fa-trash text-muted fa-1x align-self-baseline"></em>
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

</main>