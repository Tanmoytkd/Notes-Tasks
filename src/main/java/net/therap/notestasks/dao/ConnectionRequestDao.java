package net.therap.notestasks.dao;

import net.therap.notestasks.domain.ConnectionRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class ConnectionRequestDao extends GenericDao<ConnectionRequest> {

    @Override
    public Optional<ConnectionRequest> find(ConnectionRequest request) {
        TypedQuery<ConnectionRequest> query =
                em.createNamedQuery("ConnectionRequest.findByExample", ConnectionRequest.class);
        query.setParameter("sender", request.getSender());
        query.setParameter("receiver", request.getReceiver());

        return query.getResultList().stream().findFirst();
    }

    protected ConnectionRequestDao() {
        super(ConnectionRequest.class);
    }
}
