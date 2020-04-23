package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Entity
@Table(name = "task_assignments")
public class TaskAssignment extends BasicEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @NotNull
    @Column(name = "is_completed")
    private boolean isCompleted;

    public TaskAssignment() {
        this.isCompleted = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
