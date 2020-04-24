package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@NamedQueries({
        @NamedQuery(name = "TaskComment.findAll",
                query = "FROM TaskComment taskComment WHERE taskComment.isDeleted = false")
})

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
