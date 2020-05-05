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
            <a class="text-secondary font-weight-bold" data-toggle="collapse" href="#ownTasksList"
               role="button" aria-expanded="false" aria-controls="collapseExample">
                Own Tasks
            </a>

            <c:url var="createTaskLink" value="/task/new"/>
            <a href="${createTaskLink}">
                <em class="fa fa-plus"></em>
            </a>
        </div>
        <ul id="ownTasksList" class="collapse show list-group list-group-flush" aria-expanded="true">
            <c:forEach items="${ownTasks}" var="task">
                <li class="list-group-item">
                    <div class="row justify-content-center">
                        <div class="col-11">
                            <c:url var="taskLink" value="/task/${task.id}"/>
                            <a href="${taskLink}">
                                <h6 class="text-dark font-weight-bold">${task.title}</h6>
                            </a>
                        </div>

                        <div class="col-1">
                            <c:url var="deleteTaskLink" value="/task/delete/${task.id}"/>
                            <a href="${deleteTaskLink}">
                                <em class="fa fa-trash"></em>
                            </a>
                        </div>

                    </div>

                </li>
            </c:forEach>
        </ul>


        <div class="card-header p-1 justify-content-center">
            Assigned Tasks from Others
        </div>
        <ul class="list-group list-group-flush">
            <c:forEach items="${taskAssignments}" var="taskAssignment">
                <c:set var="task" value="${taskAssignment.task}"/>
                <li class="list-group-item">
                    <c:url var="assignedTaskLink" value="/task/${task.id}"/>
                    <div class="row d-flex">
                        <div class="flex-fill m-3">
                            <a href="${assignedTaskLink}">
                                <h6 class="text-dark font-weight-bold">${task.title}</h6>
                                <small class="text-muted">${task.creator.name}</small>
                            </a>
                        </div>
                        <c:url var="deleteTaskAssignmentLink" value="/taskAssignment/delete/${taskAssignment.id}"/>
                        <div>
                            <a href="${deleteTaskAssignmentLink}" class="mx-2 text-info text-lg">
                                <em class="fa fa-times"></em>
                            </a>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
