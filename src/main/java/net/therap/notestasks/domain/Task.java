package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@NamedQueries({
        @NamedQuery(name = "Task.findAll",
                query = "FROM Task task WHERE task.deleted = false")
})
@Entity
@Table(name = "task")
public class Task extends BasicEntity implements Serializable {

    private static final long serialVersionUID = 1;

    @NotEmpty
    private String title;
    private String description;

    @Column(name = "is_complete")
    private boolean completed;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL})
    private List<TaskAssignment> taskAssignments;

    @OneToMany(mappedBy = "task", cascade = {CascadeType.ALL})
    private List<TaskComment> comments;

    public Task() {
        this.title = "";
        this.description = "";
        this.taskAssignments = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<TaskAssignment> getTaskAssignments() {
        return taskAssignments;
    }

    public void setTaskAssignments(List<TaskAssignment> assignments) {
        this.taskAssignments = assignments;
    }

    public List<TaskComment> getComments() {
        return comments;
    }

    public void setComments(List<TaskComment> taskComments) {
        this.comments = taskComments;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
