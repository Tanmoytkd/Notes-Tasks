package net.therap.notestasks.dao;

import net.therap.notestasks.domain.UserConnection;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class UserConnectionDao extends GenericDao<UserConnection> {

    @Override
    public Optional<UserConnection> find(UserConnection connection) {
        TypedQuery<UserConnection> query =
                em.createNamedQuery("UserConnection.findByExample", UserConnection.class);
        query.setParameter("users", connection.getUsers());

        return query.getResultList().stream().findFirst();
    }

    public UserConnectionDao() {
        super(UserConnection.class);
    }
}
