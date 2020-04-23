package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends BasicEntity {

    @NotNull
    private String email;
    @NotNull
    @Size(min = 3, max = 30)
    private String name;
    @NotNull
    private String password;
    private String phone;


    @OneToMany(mappedBy = "writer", cascade = {CascadeType.ALL})
    List<Note> ownNotes;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    List<NoteAccess> sharedNoteAccesses;

    @OneToMany(mappedBy = "sender", cascade = {CascadeType.ALL})
    private List<ConnectionRequest> sentConnectionRequests;

    @OneToMany(mappedBy = "receiver", cascade = {CascadeType.ALL})
    private List<ConnectionRequest> receivedConnectionRequests;

    @OneToMany(mappedBy = "sender", cascade = {CascadeType.ALL})
    private List<Report> sentReports;

    @OneToMany(mappedBy = "target", cascade = {CascadeType.ALL})
    private List<Report> targetedReports;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "users")
    private List<UserConnection> connections;

    @OneToMany(mappedBy = "creator", cascade = {CascadeType.ALL})
    private List<Task> ownTasks;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<TaskAssignment> taskAssignments;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "writer", cascade = {CascadeType.ALL})
    private List<NoteComment> noteComments;

    @OneToMany(mappedBy = "writer", cascade = {CascadeType.ALL})
    private List<TaskComment> taskComments;

    @OneToMany(mappedBy = "writer", cascade = {CascadeType.ALL})
    private List<ReportComment> reportComments;

    public User() {
        this.ownNotes = new ArrayList<>();
        this.sharedNoteAccesses = new ArrayList<>();
        this.sentConnectionRequests = new ArrayList<>();
        this.receivedConnectionRequests = new ArrayList<>();
        this.sentReports = new ArrayList<>();
        this.targetedReports = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.ownTasks = new ArrayList<>();
        this.taskAssignments = new ArrayList<>();
        this.role = Role.BASIC_USER;
        this.noteComments = new ArrayList<>();
        this.taskComments = new ArrayList<>();
        this.reportComments = new ArrayList<>();
    }

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

    public List<UserConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<UserConnection> connections) {
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

    public List<NoteComment> getNoteComments() {
        return noteComments;
    }

    public void setNoteComments(List<NoteComment> noteComments) {
        this.noteComments = noteComments;
    }

    public List<TaskComment> getTaskComments() {
        return taskComments;
    }

    public void setTaskComments(List<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }

    public List<ReportComment> getReportComments() {
        return reportComments;
    }

    public void setReportComments(List<ReportComment> reportComments) {
        this.reportComments = reportComments;
    }
}
