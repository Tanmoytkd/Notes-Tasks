package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@NamedQueries({
        @NamedQuery(name = "NoteComment.findAll",
                query = "FROM NoteComment noteComment WHERE noteComment.deleted = false")
})
@Entity
@Table(name = "note_comment")
public class NoteComment extends Comment implements Serializable {

    private static final long serialVersionUID = 1;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "note_id")
    private Note note;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
