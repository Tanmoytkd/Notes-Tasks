package net.therap.notestasks.service;

import net.therap.notestasks.dao.TaskAssignmentDao;
import net.therap.notestasks.dao.TaskCommentDao;
import net.therap.notestasks.dao.TaskDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.Task;
import net.therap.notestasks.domain.TaskAssignment;
import net.therap.notestasks.domain.TaskComment;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskCommentDao taskCommentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TaskAssignmentDao taskAssignmentDao;

    public void createTask(Task task) {
        taskDao.saveOrUpdate(task);

        User creator = task.getCreator();
        creator.getOwnTasks().add(task);
        userDao.saveOrUpdate(creator);
    }

    public void updateTask(Task task) {
        taskDao.saveOrUpdate(task);
    }

    public void deleteTask(Task task) {
        User creator = task.getCreator();
        creator.getOwnTasks().remove(task);
        userDao.saveOrUpdate(creator);

        task.getTaskAssignments().forEach(this::deleteTaskAssignment);

        task.getComments().forEach(this::deleteTaskComment);

        taskDao.delete(task);
    }

    public void createTaskComment(TaskComment taskComment) {
        taskCommentDao.saveOrUpdate(taskComment);

        Task task = taskComment.getTask();
        task.getComments().add(taskComment);
        taskDao.saveOrUpdate(task);

        User writer = taskComment.getWriter();
        writer.getTaskComments().add(taskComment);
        userDao.saveOrUpdate(writer);
    }

    public void updateTaskComment(TaskComment taskComment) {
        taskCommentDao.saveOrUpdate(taskComment);
    }

    public void deleteTaskComment(TaskComment taskComment) {
        Task task = taskComment.getTask();
        task.getComments().remove(taskComment);
        taskDao.saveOrUpdate(task);

        User writer = taskComment.getWriter();
        writer.getTaskComments().remove(taskComment);
        userDao.saveOrUpdate(writer);

        taskCommentDao.delete(taskComment);
    }

    public void createTaskAssignment(TaskAssignment taskAssignment) {
        User user = taskAssignment.getUser();
        Task task = taskAssignment.getTask();

        TaskAssignment persistedTaskAssignment = task.getTaskAssignments().stream()
                .filter(taskAssignment1 -> !taskAssignment1.isDeleted())
                .filter(taskAssignment1 -> taskAssignment1.getUser().getId() == user.getId())
                .findFirst()
                .orElse(null);

        if (persistedTaskAssignment == null) {
            taskAssignmentDao.saveOrUpdate(taskAssignment);

            user.getTaskAssignments().add(taskAssignment);
            userDao.saveOrUpdate(user);

            task.getTaskAssignments().add(taskAssignment);
            taskDao.saveOrUpdate(task);
        }
    }

    public void updateTaskAssignment(TaskAssignment taskAssignment) {
        taskAssignmentDao.saveOrUpdate(taskAssignment);
    }

    public void deleteTaskAssignment(TaskAssignment taskAssignment) {
        Task task = taskAssignment.getTask();
        task.getTaskAssignments().remove(taskAssignment);
        taskDao.saveOrUpdate(task);

        User user = taskAssignment.getUser();
        user.getTaskAssignments().remove(taskAssignment);
        userDao.saveOrUpdate(user);

        taskAssignmentDao.delete(taskAssignment);
    }

    public void updateTaskAssignmentCompleteStatus(TaskAssignment taskAssignment, boolean isComplete) {
        taskAssignment.setCompleted(isComplete);
        updateTaskAssignment(taskAssignment);
    }

    public boolean hasDeleteAccess(User persistedCurrentUser, Task task) {
        return isCreator(persistedCurrentUser, task);
    }

    public Optional<Task> findTaskById(long taskId) {
        return taskDao.find(taskId);
    }

    public Optional<TaskComment> findTaskCommentById(long taskCommentId) {
        return taskCommentDao.find(taskCommentId);
    }

    public Optional<TaskAssignment> findTaskAssignmentById(long taskAssignmentId) {
        return taskAssignmentDao.find(taskAssignmentId);
    }

    public boolean canDeleteTaskComment(User user, TaskComment taskComment) {
        User persistedUser = userDao.findByEmail(user.getEmail())
                .orElseThrow(InvalidUserException::new);

        return isCreator(persistedUser, taskComment.getTask()) ||
                taskComment.getWriter().getId() == persistedUser.getId();
    }

    public boolean canAccessTask(User persistedCurrentUser, Task task) {
        if (isCreator(persistedCurrentUser, task)) {
            return true;
        }

        return task.getTaskAssignments().stream()
                .filter(taskAssignment -> !taskAssignment.isDeleted())
                .anyMatch(taskAssignment -> taskAssignment.getUser().getId() == persistedCurrentUser.getId());
    }

    public boolean isCreator(User persistedCurrentUser, Task task) {
        return persistedCurrentUser.getId() == task.getCreator().getId();
    }

    public boolean hasReadAccess(User persistedCurrentUser, Task task) {
        return canAccessTask(persistedCurrentUser, task);
    }

    public boolean hasWriteAccess(User persistedCurrentUser, Task task) {
        return isCreator(persistedCurrentUser, task);
    }

    public boolean hasAssignmentCreateAccess(User persistedCurrentUser, Task task) {
        return isCreator(persistedCurrentUser, task);
    }

    public boolean hasAssignmentDeleteAccess(User persistedCurrentUser, TaskAssignment taskAssignment) {
        return isCreator(persistedCurrentUser, taskAssignment.getTask()) ||
                taskAssignment.getUser().getId() == persistedCurrentUser.getId();
    }

    public boolean hasAssignmentUpdateAccess(User persistedCurrentUser, TaskAssignment taskAssignment) {
        return isCreator(persistedCurrentUser, taskAssignment.getTask()) ||
                taskAssignment.getUser().getId() == persistedCurrentUser.getId();
    }
}
