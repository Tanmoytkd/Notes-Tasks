package net.therap.notestasks.dao;

import net.therap.notestasks.domain.User;
import org.springframework.stereotype.Repository;

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

    public UserDao() {
        super(User.class);
    }

    @Override
    public Optional<User> find(User user) {
        return em.createNamedQuery("User.findByExample", User.class)
                .setParameter(EMAIL_LABEL, user.getEmail())
                .setParameter(PASSWORD_LABEL, user.getPassword())
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return em.createNamedQuery("User.findByEmail", User.class)
                .setParameter(EMAIL_LABEL, email)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findBySecret(String secret) {
        return em.createNamedQuery("User.findBySecret", User.class)
                .setParameter(SECRET_LABEL, secret)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return em.createNamedQuery("User.findByEmailAndPassword", User.class)
                .setParameter(EMAIL_LABEL, email)
                .setParameter(PASSWORD_LABEL, password)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<User> findUsersContainingName(String name) {
        return em.createNamedQuery("User.findContainingName", User.class)
                .setParameter("name", name)
                .getResultList();
    }
}
