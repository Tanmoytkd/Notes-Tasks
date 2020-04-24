package net.therap.notestasks.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@NamedQueries({
        @NamedQuery(name = "NoteComment.findAll",
                query = "FROM NoteComment noteComment WHERE noteComment.isDeleted = false")
})

@Entity
@Table(name = "note_comments")
public class NoteComment extends Comment {

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
