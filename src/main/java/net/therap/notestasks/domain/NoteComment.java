package net.therap.notestasks.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author tanmoy.das
 * @since 4/22/20
 */
@Entity
public class NoteComment extends Comment {

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
