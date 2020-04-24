package net.therap.notestasks.dao;

import net.therap.notestasks.domain.NoteAccess;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class NoteAccessDao extends GenericDao<NoteAccess> {

    protected NoteAccessDao() {
        super(NoteAccess.class);
    }
}
