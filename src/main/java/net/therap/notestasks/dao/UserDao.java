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

    public static final String EMAIL_TXT = "email";
    public static final String PASSWORD_TXT = "password";
    public static final String SECRET_TXT = "secret";

    protected UserDao() {
        super(User.class);
    }

    @Override
    public Optional<User> findByExample(User user) {
        TypedQuery<User> query = em.createNamedQuery("User.findByExample", User.class);
        query.setParameter(EMAIL_TXT, user.getEmail());
        query.setParameter(PASSWORD_TXT, user.getPassword());

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter(EMAIL_TXT, email);

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findBySecret(String secret) {
        TypedQuery<User> query = em.createNamedQuery("User.findBySecret", User.class);
        query.setParameter(SECRET_TXT, secret);

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmailAndPassword", User.class);
        query.setParameter(EMAIL_TXT, email);
        query.setParameter(PASSWORD_TXT, password);

        return query.getResultList().stream().findFirst();
    }
}
