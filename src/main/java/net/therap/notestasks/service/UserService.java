package net.therap.notestasks.service;

import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.exception.InvalidUserException;
import net.therap.notestasks.util.HashingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
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
    public User createOrUpdateUser(User user) throws NoSuchAlgorithmException {
        user.setPassword(HashingUtil.sha256Hash(user.getPassword()));
        return userDao.saveOrUpdate(user);
    }

    public User findUserWithSameEmail(User user) {
        return userDao.findByEmail(user.getEmail()).orElseThrow(InvalidUserException::new);
    }


    public List<User> findUsersByNameOrEmail(String s) {
        List<User> users = userDao.findUsersContainingName(s);

        findUserByEmail(s).ifPresent(users::add);

        return users.stream().distinct().collect(Collectors.toList());
    }

    public Optional<User> findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) throws NoSuchAlgorithmException {
        return userDao.findByEmailAndPassword(email, HashingUtil.sha256Hash(password));
    }

    public Optional<User> findUserBySecret(String secret) {
        return userDao.findBySecret(secret);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
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
