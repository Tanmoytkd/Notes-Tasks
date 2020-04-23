package net.therap.notestasks.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
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
