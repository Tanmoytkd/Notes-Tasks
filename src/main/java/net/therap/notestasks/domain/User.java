package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class User extends BasicEntity {

    @NotNull
    private String email;
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    @NotNull
    private String password;
    private String phone;


    @OneToMany(mappedBy = "writer")
    List<Note> ownNotes;

    @OneToMany(mappedBy = "user")
    List<NoteAccess> sharedNoteAccesses;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "sender")
    private List<ConnectionRequest> sentConnectionRequests;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "receiver")
    private List<ConnectionRequest> receivedConnectionRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Report> sentReports;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "target")
    private List<Report> targetedReports;

    @ManyToMany(mappedBy = "connections")
    @JoinTable(
            name = "connections",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")}
    )
    private List<User> connections;

    @OneToMany(mappedBy = "creator")
    private List<Task> ownTasks;

    @OneToMany(mappedBy = "user")
    private List<TaskAssignment> taskAssignments;

    @Enumerated(EnumType.STRING)
    private Role role;

    public List<Note> getOwnNotes() {
        return ownNotes;
    }

    public void setOwnNotes(List<Note> ownNotes) {
        this.ownNotes = ownNotes;
    }

    public List<NoteAccess> getSharedNoteAccesses() {
        return sharedNoteAccesses;
    }

    public void setSharedNoteAccesses(List<NoteAccess> sharedNoteAccesses) {
        this.sharedNoteAccesses = sharedNoteAccesses;
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    public boolean isBasicUser() {
        return role.equals(Role.BASIC_USER);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ConnectionRequest> getSentConnectionRequests() {
        return sentConnectionRequests;
    }

    public void setSentConnectionRequests(List<ConnectionRequest> sentConnectionRequests) {
        this.sentConnectionRequests = sentConnectionRequests;
    }

    public List<ConnectionRequest> getReceivedConnectionRequests() {
        return receivedConnectionRequests;
    }

    public void setReceivedConnectionRequests(List<ConnectionRequest> receivedConnectionRequests) {
        this.receivedConnectionRequests = receivedConnectionRequests;
    }

    public List<Report> getTargetedReports() {
        return targetedReports;
    }

    public void setTargetedReports(List<Report> targetedReports) {
        this.targetedReports = targetedReports;
    }

    public List<Report> getSentReports() {
        return sentReports;
    }

    public void setSentReports(List<Report> sentReports) {
        this.sentReports = sentReports;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<User> getConnections() {
        return connections;
    }

    public void setConnections(List<User> connections) {
        this.connections = connections;
    }

    public List<Task> getOwnTasks() {
        return ownTasks;
    }

    public void setOwnTasks(List<Task> ownTasks) {
        this.ownTasks = ownTasks;
    }

    public List<TaskAssignment> getTaskAssignments() {
        return taskAssignments;
    }

    public void setTaskAssignments(List<TaskAssignment> taskAssignments) {
        this.taskAssignments = taskAssignments;
    }
}
