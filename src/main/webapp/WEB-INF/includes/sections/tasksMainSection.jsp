<%--
  @author tanmoy.das
  @since 5/4/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<main role="main" class="col-md-10 col-xl-11 vh-100 py-2 px-1 d-flex">
    <div class="d-flex flex-column flex-grow-1 vh-100 overflow-auto">
        <div class="row flex-grow-1 overflow-auto">
            <div class="col-md-4 flex-column">
                <%@ include file="/WEB-INF/includes/sections/sidebars/tasksListSidebar.jsp" %>
            </div>

            <div class="col-md-8 py-2">
                <div class="d-flex flex-column vh-100 mh-100 overflow-auto py-2">
                    <c:if test="${noteCommand!=null}">
                        <%@ include file="/WEB-INF/includes/sections/editTaskSection.jsp" %>
                        <%@ include file="/WEB-INF/includes/sections/taskCommentsSection.jsp" %>
                    </c:if>
                </div>
            </div>
        </div>
    </div>

</main>