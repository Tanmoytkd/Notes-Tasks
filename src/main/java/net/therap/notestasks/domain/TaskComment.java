package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@NamedQueries({
        @NamedQuery(name = "TaskComment.findAll",
                query = "FROM TaskComment taskComment WHERE taskComment.deleted = false")
})
@Entity
@Table(name = "task_comment")
public class TaskComment extends Comment implements Serializable {

    private static final long serialVersionUID = 1;

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
