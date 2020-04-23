package net.therap.notestasks.dao;

import net.therap.notestasks.domain.ConnectionRequest;
import org.springframework.stereotype.Repository;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class ConnectionRequestDao extends BasicDao<ConnectionRequest> {

    protected ConnectionRequestDao() {
        super(ConnectionRequest.class);
    }
}
