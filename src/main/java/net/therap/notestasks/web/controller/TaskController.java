package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.*;
import net.therap.notestasks.service.TaskService;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.config.Constants.*;
import static net.therap.notestasks.helper.UrlHelper.getUrl;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class TaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/task/new"}, method = {RequestMethod.GET})
    public String createNewTask(@SessionAttribute(CURRENT_USER) User currentUser, ModelMap model,
                                HttpServletRequest req, HttpServletResponse resp) {

        User persistedCurrentUser = userService.refreshUser(currentUser);

        Task task = new Task();
        task.setCreator(persistedCurrentUser);
        task.setTitle("Untitled Task");
        task.setDescription("");

        taskService.createTask(task);

        persistedCurrentUser.getOwnTasks().add(task);

        return redirectTo(getUrl(task));
    }

    @RequestMapping(value = {"/tasks"}, method = RequestMethod.GET)
    public String showTasks(@SessionAttribute(CURRENT_USER) User currentUser, ModelMap model,
                            HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isTasksPage", true);

        User persistedCurrentUser = userService.refreshUser(currentUser);

        setupModelTasks(model, persistedCurrentUser);

        setupModelTaskAssignments(model, persistedCurrentUser);

        return "dashboard";
    }

    @RequestMapping(value = {"/task/{taskId}"}, method = RequestMethod.GET)
    public String showTask(@PathVariable("taskId") Task task,
                           @SessionAttribute(CURRENT_USER) User currentUser,
                           ModelMap model, HttpServletRequest req, HttpServletResponse resp) {

        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (!taskService.canAccessTask(persistedCurrentUser, task)) {
            return REDIRECT_NOTES;
        }

        setupModelTaskPermissions(task, model, persistedCurrentUser);

        model.addAttribute("taskService", taskService);
        model.addAttribute("taskCommand", task);

        List<TaskComment> comments = task.getComments().stream()
                .filter(taskComment -> !taskComment.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("taskComments", comments);

        List<TaskAssignment> taskAssignments = task.getTaskAssignments().stream()
                .filter(taskAssignment -> !taskAssignment.isDeleted())
                .collect(Collectors.toList());
        model.addAttribute("taskAssignments", taskAssignments);


        TaskComment taskComment = new TaskComment();
        taskComment.setTask(task);
        taskComment.setWriter(currentUser);
        model.addAttribute("taskCommentCommand", taskComment);

        TaskAssignment taskAssignment = new TaskAssignment();
        taskAssignment.setTask(task);
        model.addAttribute("taskAssignmentCommand", taskAssignment);

        List<User> connectedUsers = userService.getConnectedUsers(persistedCurrentUser);
        model.addAttribute("connectedUsers", connectedUsers);

        return showTasks(currentUser, model, req, resp);
    }

    @RequestMapping(value = {"/task/update"}, method = RequestMethod.POST)
    public String updateTask(@ModelAttribute("taskCommand") Task task,
                             @SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (!taskService.hasWriteAccess(persistedCurrentUser, task)) {
            return redirectTo(getUrl(task));
        }

        taskService.updateTask(task);
        return redirectTo(getUrl(task));
    }


    @RequestMapping(value = {"/task/delete/{taskId}"}, method = RequestMethod.GET)
    public String deleteTask(@PathVariable("taskId") Task task,
                             @SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (!taskService.hasDeleteAccess(persistedCurrentUser, task)) {
            return REDIRECT_NOTES;
        }

        taskService.deleteTask(task);

        return REDIRECT_TASKS;
    }

    @RequestMapping(value = "/taskAssignment", method = RequestMethod.POST)
    public String createTaskAssignment(@Valid @ModelAttribute("taskAssignmentCommand") TaskAssignment taskAssignment,
                                       Errors errors,
                                       @SessionAttribute(CURRENT_USER) User currentUser,
                                       ModelMap model,
                                       HttpServletRequest req, HttpServletResponse resp) {

        if (errors.hasErrors()) {
            String taskPage = showTask(taskAssignment.getTask(), currentUser, model, req, resp);
            model.addAttribute("taskAssignmentCommand", taskAssignment);
            return taskPage;
        }

        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (!taskService.hasAssignmentCreateAccess(persistedCurrentUser, taskAssignment.getTask())) {
            return redirectTo(getUrl(taskAssignment.getTask()));
        }

        taskService.createTaskAssignment(taskAssignment);
        return redirectTo(getUrl(taskAssignment.getTask()));
    }

    @RequestMapping(value = "/taskAssignment/delete/{taskAssignmentId}", method = RequestMethod.GET)
    public String deleteTaskAssignment(@PathVariable("taskAssignmentId") TaskAssignment taskAssignment,
                                       @SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (!taskService.hasAssignmentDeleteAccess(persistedCurrentUser, taskAssignment)) {
            return REDIRECT_TASKS;
        }

        taskService.deleteTaskAssignment(taskAssignment);
        return REDIRECT_TASKS;
    }

    @RequestMapping(value = "/taskAssignment/markAsComplete/{taskAssignmentId}", method = RequestMethod.GET)
    public String markTaskAssignmentAsComplete(@PathVariable("taskAssignmentId") TaskAssignment taskAssignment,
                                               @SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (taskService.hasAssignmentUpdateAccess(persistedCurrentUser, taskAssignment)) {
            taskService.updateTaskAssignmentCompleteStatus(taskAssignment, true);
        }

        return REDIRECT_TASKS;
    }

    @RequestMapping(value = "/taskAssignment/markAsIncomplete/{taskAssignmentId}", method = RequestMethod.GET)
    public String markTaskAssignmentAsIncomplete(@PathVariable("taskAssignmentId") TaskAssignment taskAssignment,
                                                 @SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (taskService.hasAssignmentUpdateAccess(persistedCurrentUser, taskAssignment)) {
            taskService.updateTaskAssignmentCompleteStatus(taskAssignment, false);
        }

        return REDIRECT_TASKS;
    }

    @RequestMapping(value = "/taskComment", method = RequestMethod.POST)
    public String createTaskComment(@ModelAttribute("taskCommentCommand") TaskComment taskComment, Errors errors,
                                    @SessionAttribute(CURRENT_USER) User currentUser, ModelMap model,
                                    HttpServletRequest req, HttpServletResponse resp) {
        if (errors.hasErrors()) {
            String taskPage = showTask(taskComment.getTask(), currentUser, model, req, resp);
            model.addAttribute("taskCommentCommand", taskComment);
            return taskPage;
        }

        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (!taskService.hasAssignmentCreateAccess(persistedCurrentUser, taskComment.getTask())) {
            return redirectTo(getUrl(taskComment.getTask()));
        }

        taskService.createTaskComment(taskComment);
        return redirectTo(getUrl(taskComment.getTask()));
    }

    @RequestMapping(value = {"/taskComment/delete/{taskCommentId}"}, method = RequestMethod.GET)
    public String deleteNoteComment(@PathVariable("taskCommentId") TaskComment taskComment,
                                    @SessionAttribute(CURRENT_USER) User currentUser,
                                    ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        User persistedCurrentUser = userService.refreshUser(currentUser);

        if (taskService.canDeleteTaskComment(persistedCurrentUser, taskComment)) {
            taskService.deleteTaskComment(taskComment);
        }

        return redirectTo(getUrl(taskComment.getTask()));
    }

    private void setupModelTaskAssignments(ModelMap model, User persistedCurrentUser) {
        List<TaskAssignment> taskAssignments = persistedCurrentUser.getTaskAssignments().stream()
                .filter(taskAssignment -> !taskAssignment.isDeleted())
                .sorted(Comparator.comparing(BasicEntity::getUpdatedOn))
                .collect(Collectors.toList());

        List<TaskAssignment> ownTaskAssignmentsComplete = taskAssignments.stream()
                .filter(TaskAssignment::getIsCompleted)
                .collect(Collectors.toList());
        model.addAttribute("ownTaskAssignmentsComplete", ownTaskAssignmentsComplete);

        List<TaskAssignment> ownTaskAssignmentsIncomplete = taskAssignments.stream()
                .filter(taskAssignment -> !taskAssignment.getIsCompleted())
                .collect(Collectors.toList());
        model.addAttribute("ownTaskAssignmentsIncomplete", ownTaskAssignmentsIncomplete);
    }

    private void setupModelTasks(ModelMap model, User persistedCurrentUser) {
        List<Task> ownTasks = persistedCurrentUser.getOwnTasks().stream()
                .filter(task -> !task.isDeleted())
                .sorted(Comparator.comparing(BasicEntity::getUpdatedOn))
                .collect(Collectors.toList());
        model.addAttribute("ownTasks", ownTasks);

        List<Task> ownTasksComplete = ownTasks.stream()
                .filter(Task::getIsComplete)
                .collect(Collectors.toList());
        model.addAttribute("ownTasksComplete", ownTasksComplete);

        List<Task> ownTasksIncomplete = ownTasks.stream()
                .filter(task -> !task.getIsComplete())
                .collect(Collectors.toList());
        model.addAttribute("ownTasksIncomplete", ownTasksIncomplete);
    }

    private void setupModelTaskPermissions(@PathVariable("taskId") Task task, ModelMap model, User persistedCurrentUser) {
        boolean hasReadAccess = taskService.hasReadAccess(persistedCurrentUser, task);
        model.addAttribute("hasReadAccess", hasReadAccess);

        boolean hasWriteAccess = taskService.hasWriteAccess(persistedCurrentUser, task);
        model.addAttribute("hasWriteAccess", hasWriteAccess);

        boolean hasAssignmentAccess = taskService.hasAssignmentCreateAccess(persistedCurrentUser, task);
        model.addAttribute("hasAssignmentAccess", hasAssignmentAccess);

        boolean hasDeleteAccess = taskService.hasDeleteAccess(persistedCurrentUser, task);
        model.addAttribute("hasDeleteAccess", hasDeleteAccess);
    }
}
