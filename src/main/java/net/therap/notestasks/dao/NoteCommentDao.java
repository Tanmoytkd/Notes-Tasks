package net.therap.notestasks.dao;

import net.therap.notestasks.domain.NoteComment;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class NoteCommentDao extends GenericDao<NoteComment> {

    protected NoteCommentDao() {
        super(NoteComment.class);
    }
}
