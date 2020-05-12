package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@NamedQueries({
        @NamedQuery(name = "TaskAssignment.findAll",
                query = "FROM TaskAssignment taskAssignment WHERE taskAssignment.deleted = false")
})
@Entity
@Table(name = "task_assignment")
public class TaskAssignment extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1;

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
    private boolean completed;

    public TaskAssignment() {
        this.completed = false;
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
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
