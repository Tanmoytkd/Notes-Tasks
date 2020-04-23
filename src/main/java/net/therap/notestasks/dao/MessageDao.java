package net.therap.notestasks.dao;

import net.therap.notestasks.domain.Message;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class MessageDao extends BasicDao<Message> {

    protected MessageDao() {
        super(Message.class);
    }
}
