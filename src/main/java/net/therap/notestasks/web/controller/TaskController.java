package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.Note;
import net.therap.notestasks.domain.Task;
import net.therap.notestasks.domain.TaskAssignment;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.TaskService;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.config.Constants.*;

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

        return REDIRECT_TASKS;
    }

    @RequestMapping(value = {"/tasks"}, method = RequestMethod.GET)
    public String listMessages(@SessionAttribute(CURRENT_USER) User currentUser, ModelMap model,
                               HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isTasksPage", true);

        User persistedCurrentUser = userService.refreshUser(currentUser);

        List<Task> ownTasks = persistedCurrentUser.getOwnTasks().stream()
                .filter(task -> !task.isDeleted())
                .sorted((task1, task2) -> task1.getUpdatedOn().compareTo(task2.getUpdatedOn()))
                .collect(Collectors.toList());
        model.addAttribute("ownTasks", ownTasks);

        List<TaskAssignment> taskAssignments = persistedCurrentUser.getTaskAssignments().stream()
                .filter(taskAssignment -> !taskAssignment.isDeleted())
                .sorted((taskAssignment1, taskAssignment2) ->
                        taskAssignment1.getUpdatedOn().compareTo(taskAssignment2.getUpdatedOn()))
                .collect(Collectors.toList());
        model.addAttribute("taskAssignments", taskAssignments);

        return "dashboard";
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
}
