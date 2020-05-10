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
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public User createUser(User user) {
        if (userDao.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        return userDao.saveOrUpdate(user);
    }

    @Transactional
    public void updateUser(User user) {
        if (!userDao.find(user).isPresent()) {
            throw new InvalidUserException("User does not exist");
        }

        userDao.saveOrUpdate(user);
    }

    public User findUserWithSameEmail(User user) {
        return userDao.findByEmail(user.getEmail()).orElseThrow(InvalidUserException::new);
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

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    public void destroyUser(User user) {
        userDao.destroy(user);
    }

    @Transactional
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public Optional<User> findUserById(long id) {
        return userDao.find(id);
    }

    public List<User> findConnectedUsers(User persistedCurrentUser) {
        return findUserWithSameEmail(persistedCurrentUser)
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
}
