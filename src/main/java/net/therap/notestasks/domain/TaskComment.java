package net.therap.notestasks.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Entity
@Table(name = "task_comments")
public class TaskComment extends Comment {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
