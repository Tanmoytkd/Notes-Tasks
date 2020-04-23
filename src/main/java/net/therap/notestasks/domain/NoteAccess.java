package net.therap.notestasks.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@Entity
public class NoteAccess extends BasicEntity {

    public enum AccessLevel {
        READ,
        WRITE,
        SHARE,
        DELETE
    }

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(targetClass = AccessLevel.class)
    @JoinTable(name = "note_access_levels", joinColumns = @JoinColumn(name = "note_id"))
    @Column(name = "access_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<AccessLevel> accessLevels;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Set<AccessLevel> accessLevels) {
        this.accessLevels = accessLevels;
    }
}
