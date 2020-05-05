package net.therap.notestasks.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@NamedQueries({
        @NamedQuery(name = "NoteAccess.findAll",
                query = "FROM NoteAccess noteAccess WHERE noteAccess.isDeleted = false")
})

@Entity
@Table(name = "note_accesses")
public class NoteAccess extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(targetClass = AccessLevel.class)
    @JoinTable(name = "note_access_levels", joinColumns = @JoinColumn(name = "note_access_id"))
    @Column(name = "access_level", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<AccessLevel> accessLevels;

    public NoteAccess() {
        this.accessLevels = new ArrayList<>();
    }

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

    public List<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(List<AccessLevel> accessLevels) {
        this.accessLevels = accessLevels;
    }
}
