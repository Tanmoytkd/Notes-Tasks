package net.therap.notestasks.dao;

import net.therap.notestasks.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class UserDao extends BasicDao<User> {

    protected UserDao() {
        super(User.class);
    }

    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createQuery("FROM User user WHERE user.email=:email", User.class);
        query.setParameter("email", email);

        return query.getResultList().stream().findFirst();
    }
}
