package net.therap.notestasks.dao;

import net.therap.notestasks.domain.Task;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class TaskDao extends BasicDao<Task> {

    protected TaskDao() {
        super(Task.class);
    }
}
