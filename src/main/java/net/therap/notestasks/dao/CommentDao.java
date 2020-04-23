package net.therap.notestasks.dao;

import net.therap.notestasks.domain.Comment;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class CommentDao extends BasicDao<Comment> {

    protected CommentDao() {
        super(Comment.class);
    }
}