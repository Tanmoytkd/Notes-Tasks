package net.therap.notestasks.service;

import net.therap.notestasks.dao.TaskAssignmentDao;
import net.therap.notestasks.dao.TaskCommentDao;
import net.therap.notestasks.dao.TaskDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.Task;
import net.therap.notestasks.domain.TaskAssignment;
import net.therap.notestasks.domain.TaskComment;
import net.therap.notestasks.domain.User;
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

        task.getTaskComments().forEach(this::deleteTaskComment);

        taskDao.delete(task);
    }

    public void createTaskComment(TaskComment taskComment) {
        taskCommentDao.saveOrUpdate(taskComment);

        Task task = taskComment.getTask();
        task.getTaskComments().add(taskComment);
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
        task.getTaskComments().remove(taskComment);
        taskDao.saveOrUpdate(task);

        User writer = taskComment.getWriter();
        writer.getTaskComments().remove(taskComment);
        userDao.saveOrUpdate(writer);

        taskCommentDao.delete(taskComment);
    }

    public void createTaskAssignment(TaskAssignment taskAssignment) {
        taskAssignmentDao.saveOrUpdate(taskAssignment);

        User user = taskAssignment.getUser();
        user.getTaskAssignments().add(taskAssignment);
        userDao.saveOrUpdate(user);

        Task task = taskAssignment.getTask();
        task.getTaskAssignments().add(taskAssignment);
        taskDao.saveOrUpdate(task);
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
        return task.getCreator().getId() == persistedCurrentUser.getId();
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
}
