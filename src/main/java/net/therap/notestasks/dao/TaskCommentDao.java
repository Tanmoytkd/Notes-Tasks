package net.therap.notestasks.dao;

import net.therap.notestasks.domain.TaskComment;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class TaskCommentDao extends BasicDao<TaskComment> {

    protected TaskCommentDao() {
        super(TaskComment.class);
    }
}
