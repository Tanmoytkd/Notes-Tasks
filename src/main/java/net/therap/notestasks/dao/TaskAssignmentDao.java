package net.therap.notestasks.dao;

import net.therap.notestasks.domain.TaskAssignment;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class TaskAssignmentDao extends BasicDao<TaskAssignment> {

    protected TaskAssignmentDao() {
        super(TaskAssignment.class);
    }
}
