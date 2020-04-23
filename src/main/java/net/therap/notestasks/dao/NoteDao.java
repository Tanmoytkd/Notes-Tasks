package net.therap.notestasks.dao;

import net.therap.notestasks.domain.Note;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class NoteDao extends BasicDao<Note> {

    protected NoteDao() {
        super(Note.class);
    }
}
