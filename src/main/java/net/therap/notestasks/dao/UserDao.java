package net.therap.notestasks.dao;

import net.therap.notestasks.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Repository
public class UserDao extends GenericDao<User> {

    public static final String EMAIL_LABEL = "email";
    public static final String PASSWORD_LABEL = "password";
    public static final String SECRET_LABEL = "secret";

    protected UserDao() {
        super(User.class);
    }

    @Override
    public Optional<User> find(User user) {
        TypedQuery<User> query = em.createNamedQuery("User.findByExample", User.class);
        query.setParameter(EMAIL_LABEL, user.getEmail());
        query.setParameter(PASSWORD_LABEL, user.getPassword());

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter(EMAIL_LABEL, email);

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findBySecret(String secret) {
        TypedQuery<User> query = em.createNamedQuery("User.findBySecret", User.class);
        query.setParameter(SECRET_LABEL, secret);

        return query.getResultList().stream().findFirst();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmailAndPassword", User.class);
        query.setParameter(EMAIL_LABEL, email);
        query.setParameter(PASSWORD_LABEL, password);

        return query.getResultList().stream().findFirst();
    }

    public List<User> findUsersContainingName(String name) {
        TypedQuery<User> query = em.createNamedQuery("User.findContainingName", User.class);
        query.setParameter("name", name);

        return query.getResultList();
    }
}
