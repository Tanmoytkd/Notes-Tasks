<%--
  @author tanmoy.das
  @since 5/6/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.Constants" %>

<div class="d-flex flex-fill flex-column m-2 overflow-auto">
    <div class="d-flex flex-column mh-100 align-content-start overflow-auto h-100">
        <c:forEach items="${connectionRequests}" var="connectionRequest">
            <c:url var="connectionRequestUserLink"
                   value="${Constants.PROFILE_BASE_PATH}/${connectionRequest.sender.id}"/>
            <div class="d-flex alert alert-primary">
                <div class="m-2 flex-fill">
                    <a href="${connectionRequestUserLink}">
                        You received a connection request from ${connectionRequest.sender.name}
                    </a>
                </div>
                <c:url var="acceptConnectionReqeustLink"
                       value="${Constants.ACCEPT_CONNECTION_PATH}/${connectionRequest.sender.id}"/>
                <a href="${acceptConnectionReqeustLink}" class="btn btn-success mx-2">
                    <spring:message code="label.acceptConnection"/>
                </a>

                <c:url var="rejectConnectionRequestLink"
                       value="${Constants.REJECT_CONNECTION_PATH}/${connectionRequest.sender.id}"/>
                <a href="${rejectConnectionRequestLink}" class="btn btn-danger mx-2">
                    <spring:message code="label.rejectConnection"/>
                </a>
            </div>
        </c:forEach>
    </div>

</div>