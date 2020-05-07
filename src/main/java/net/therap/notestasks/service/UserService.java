package net.therap.notestasks.service;

import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.exception.DuplicateEmailException;
import net.therap.notestasks.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public User createUser(User user) {
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        return userDao.saveOrUpdate(user);
    }

    public void updateUser(User user) {
        if (!userDao.findByExample(user).isPresent()) {
            throw new InvalidUserException("User does not exist");
        }

        userDao.saveOrUpdate(user);
    }

    public User refreshUser(User user) {
        return userDao.findByExample(user).orElse(null);
    }

    public Optional<User> findByExample(User user) {
        return userDao.findByExample(user);
    }

    public List<User> findUsersByName(String name) {
        return userDao.findUsersContainingName(name);
    }

    public List<User> findUsersWithString(String s) {
        List<User> users = findUsersByName(s);

        findUserByEmail(s).ifPresent(users::add);

        return users.stream().distinct().collect(Collectors.toList());
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userDao.findByEmailAndPassword(email, password);
    }

    public Optional<User> findUserBySecret(String secret) {
        return userDao.findBySecret(secret);
    }

    public void changePassword(User user, String password) {
        User persistedUser = userDao.findByExample(user)
                .orElseThrow(() -> new InvalidUserException("User does not exist"));

        persistedUser.setPassword(password);
        userDao.saveOrUpdate(persistedUser);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public void verifyEmail(User user) {
        user = userDao.refresh(user);

        user.setEmailVerified(true);
        userDao.saveOrUpdate(user);
    }

    public void destroyUser(User user) {
        userDao.destroy(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public Optional<User> findUserById(long id) {
        return userDao.find(id);
    }

    public List<User> getConnectedUsers(User persistedCurrentUser) {
        return refreshUser(persistedCurrentUser)
                .getConnections().stream()
                .filter(connection -> !connection.isDeleted())
                .map(connection -> connection.getUsers().stream()
                        .filter(user -> !isSameUser(persistedCurrentUser, user))
                        .findFirst()
                        .orElse(null)
                ).filter(user -> user != null && !user.isDeleted())
                .collect(Collectors.toList());
    }

    private boolean isSameUser(User persistedCurrentUser, User user) {
        return user.getId() == persistedCurrentUser.getId();
    }

    public boolean canFindUserByEmail(String email) {
        return findUserByEmail(email).isPresent();
    }

    public boolean canFindUserByEmailAndPassword(String email, String password) {
        return findUserByEmailAndPassword(email, password).isPresent();
    }
}
