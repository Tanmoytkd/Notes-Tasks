package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@NamedQueries({
        @NamedQuery(name = "Task.findAll",
                query = "FROM Task task WHERE task.isDeleted = false")
})

@Entity
@Table(name = "tasks")
public class Task extends BasicEntity {

    @NotEmpty
    private String title;
    private String description;

    @Column(name = "is_complete")
    private boolean isComplete;

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

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(boolean complete) {
        isComplete = complete;
    }
}
