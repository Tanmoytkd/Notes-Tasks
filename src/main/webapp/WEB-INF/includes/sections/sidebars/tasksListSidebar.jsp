<%--
  @author tanmoy.das
  @since 5/5/20
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="net.therap.notestasks.util.Constants" %>

<div class="d-flex flex-column flex-shrink-0 mb-3 mh-100 overflow-auto">
    <div class="card flex-fill vh-100">
        <div class="card-header p-1 justify-content-center">
            <a class="text-secondary font-weight-bold" data-toggle="collapse" href="#ownTasksList"
               role="button" aria-expanded="false" aria-controls="collapseExample">
                Own Tasks (Incomplete) (${ownTasksIncomplete.size()})
            </a>

            <c:url var="createTaskLink" value="${Constants.CREATE_TASK_PATH}"/>
            <a href="${createTaskLink}" class="btn btn-sm btn-round btn-primary mx-3">
                <em class="fa fa-plus"></em> Create Task
            </a>
        </div>
        <ul id="ownTasksList" class="collapse show list-group list-group-flush" aria-expanded="true">
            <c:forEach items="${ownTasksIncomplete}" var="task">
                <li class="list-group-item">
                    <div class="row justify-content-center">
                        <div class="col-11">
                            <c:url var="taskLink" value="${Constants.TASK_BASE_PATH}/${task.id}"/>
                            <a href="${taskLink}">
                                <h6 class="text-dark font-weight-bold">${task.title}</h6>
                            </a>
                        </div>

                        <div class="col-1">
                            <c:url var="deleteTaskLink" value="${Constants.DELETE_TASK_PATH}/${task.id}"/>
                            <a href="${deleteTaskLink}">
                                <em class="fa fa-trash"></em>
                            </a>
                        </div>

                    </div>

                </li>
            </c:forEach>
        </ul>

        <div class="card-header p-1 justify-content-center">
            <a class="text-secondary font-weight-bold" data-toggle="collapse" href="#ownTasksListComplete"
               role="button" aria-expanded="false" aria-controls="collapseExample">
                Own Tasks (Complete) (${ownTasksComplete.size()})
            </a>
        </div>
        <ul id="ownTasksListComplete" class="collapse list-group list-group-flush" aria-expanded="true">
            <c:forEach items="${ownTasksComplete}" var="task">
                <li class="list-group-item">
                    <div class="row justify-content-center">
                        <div class="col-11">
                            <c:url var="taskLink" value="${Constants.TASK_BASE_PATH}/${task.id}"/>
                            <a href="${taskLink}">
                                <h6 class="text-dark font-weight-bold">${task.title}</h6>
                            </a>
                        </div>

                        <div class="col-1">
                            <c:url var="deleteTaskLink" value="${Constants.DELETE_TASK_PATH}/${task.id}"/>
                            <a href="${deleteTaskLink}">
                                <em class="fa fa-trash"></em>
                            </a>
                        </div>

                    </div>

                </li>
            </c:forEach>
        </ul>


        <div class="card-header p-1 justify-content-center">
            <a class="text-secondary font-weight-bold" data-toggle="collapse" href="#assignedTasksListIncomplete"
               role="button" aria-expanded="false" aria-controls="collapseExample">
                Assigned Tasks (Incomplete) (${ownTaskAssignmentsIncomplete.size()})
            </a>
        </div>
        <ul id="assignedTasksListIncomplete" class="collapse show list-group list-group-flush">
            <c:forEach items="${ownTaskAssignmentsIncomplete}" var="taskAssignment">
                <c:set var="task" value="${taskAssignment.task}"/>
                <li class="list-group-item">
                    <c:url var="assignedTaskLink" value="${Constants.TASK_BASE_PATH}/${task.id}"/>
                    <div class="row d-flex">
                        <div class="flex-fill m-3">
                            <a href="${assignedTaskLink}">
                                <h6 class="text-dark font-weight-bold">${task.title}</h6>
                                <small class="text-muted">${task.creator.name}</small>
                            </a>
                        </div>
                        <c:url var="deleteTaskAssignmentLink"
                               value="${Constants.DELETE_TASK_ASSIGNMENT_PATH}/${taskAssignment.id}"/>
                        <div>
                            <c:url var="markTaskAssignmentAsComplete"
                                   value="/taskAssignment/markAsComplete/${taskAssignment.id}"/>
                            <a href="${markTaskAssignmentAsComplete}" class="mx-2 btn btn-xs btn-success">
                                <em class="fa fa-check-circle"></em><span class="mx-1">Mark as Complete</span>
                            </a>

                            <a href="${deleteTaskAssignmentLink}" class="mx-2 text-info text-lg">
                                <em class="fa fa-times"></em>
                            </a>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>

        <div class="card-header p-1 justify-content-center">
            <a class="text-secondary font-weight-bold" data-toggle="collapse" href="#assignedTasksListComplete"
               role="button" aria-expanded="false" aria-controls="collapseExample">
                Assigned Tasks (Complete) (${ownTaskAssignmentsComplete.size()})
            </a>
        </div>
        <ul id="assignedTasksListComplete" class="collapse list-group list-group-flush">
            <c:forEach items="${ownTaskAssignmentsComplete}" var="taskAssignment">
                <c:set var="task" value="${taskAssignment.task}"/>
                <li class="list-group-item">
                    <c:url var="assignedTaskLink" value="${Constants.TASK_BASE_PATH}/${task.id}"/>
                    <div class="row d-flex">
                        <div class="flex-fill m-3">
                            <a href="${assignedTaskLink}">
                                <h6 class="text-dark font-weight-bold">${task.title}</h6>
                                <small class="text-muted">${task.creator.name}</small>
                            </a>
                        </div>

                        <div>
                            <c:url var="markTaskAssignmentAsIncomplete"
                                   value="/taskAssignment/markAsIncomplete/${taskAssignment.id}"/>
                            <a href="${markTaskAssignmentAsIncomplete}" class="mx-2 btn btn-xs btn-warning">
                                Mark as Incomplete
                            </a>

                            <c:url var="deleteTaskAssignmentLink"
                                   value="${Constants.DELETE_TASK_ASSIGNMENT_PATH}/${taskAssignment.id}"/>
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
