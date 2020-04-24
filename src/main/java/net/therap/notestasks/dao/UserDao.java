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
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findBySecret(String secret) {
        TypedQuery<User> query = em.createNamedQuery("User.findBySecret", User.class);
        query.setParameter("secret", secret);

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmailAndPassword", User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        return query.getResultList().stream().findFirst();
    }
}
