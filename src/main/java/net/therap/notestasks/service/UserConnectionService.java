package net.therap.notestasks.service;

import net.therap.notestasks.dao.ConnectionRequestDao;
import net.therap.notestasks.dao.UserConnectionDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.ConnectionRequest;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.domain.UserConnection;
import net.therap.notestasks.exception.DuplicateConnectionRequestException;
import net.therap.notestasks.exception.InvalidUserException;
import net.therap.notestasks.exception.UserConnectionAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
@Service
public class UserConnectionService {

    @Autowired
    private ConnectionRequestDao connectionRequestDao;

    @Autowired
    private UserConnectionDao connectionDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    public void sendConnectionRequest(ConnectionRequest connectionRequest) {
        if (isRequestAlreadySent(connectionRequest)) {
            throw new DuplicateConnectionRequestException();
        }

        User sender = userDao.find(connectionRequest.getSender()).orElse(null);
        User receiver = userDao.find(connectionRequest.getReceiver()).orElse(null);

        if (sender == null || receiver == null) {
            throw new InvalidUserException();
        }

        if (isAlreadyConnected(sender, receiver)) {
            throw new UserConnectionAlreadyExistsException();
        }

        createConnectionRequest(connectionRequest);
    }

    @Transactional
    public void acceptConnectionRequest(ConnectionRequest connectionRequest) {

        User sender = connectionRequest.getSender();
        User receiver = connectionRequest.getReceiver();
        if (isAlreadyConnected(sender, receiver)) {
            throw new UserConnectionAlreadyExistsException();
        }
        deleteConnectionRequest(connectionRequest);

        createConnection(sender, receiver);
    }

    @Transactional
    public void cancelConnectionRequest(ConnectionRequest connectionRequest) {
        deleteConnectionRequest(connectionRequest);
    }

    @Transactional
    public void removeUserConnection(UserConnection connection) {
        for (User user : connection.getUsers()) {
            user.getConnections().remove(connection);
            userDao.saveOrUpdate(user);
        }

        connection.setDeleted(true);
        connectionDao.saveOrUpdate(connection);
    }

    @Transactional
    public void createConnection(User sender, User receiver) {
        UserConnection connection = new UserConnection();
        connection.setUsers(Arrays.asList(sender, receiver));

        sender.getConnections().add(connection);
        receiver.getConnections().add(connection);

        connection = connectionDao.saveOrUpdate(connection);
    }

    @Transactional
    public void deleteConnectionRequest(ConnectionRequest connectionRequest) {
        User sender = connectionRequest.getSender();
        User receiver = connectionRequest.getReceiver();

        sender.getSentConnectionRequests().remove(connectionRequest);
        receiver.getReceivedConnectionRequests().remove(connectionRequest);

        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);

        connectionRequest.setDeleted(true);
        connectionRequestDao.saveOrUpdate(connectionRequest);
    }

    @Transactional
    public void createConnectionRequest(ConnectionRequest connectionRequest) {
        User sender = userDao.find(connectionRequest.getSender()).orElseThrow(InvalidUserException::new);
        User receiver = userDao.find(connectionRequest.getReceiver()).orElseThrow(InvalidUserException::new);

        connectionRequestDao.saveOrUpdate(connectionRequest);

        sender.getSentConnectionRequests().add(connectionRequest);
        receiver.getReceivedConnectionRequests().add(connectionRequest);
        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);
    }

    public boolean isRequestAlreadySent(User sender, User receiver) {
        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setSender(sender);
        connectionRequest.setReceiver(receiver);
        return isRequestAlreadySent(connectionRequest);
    }

    public boolean isAlreadyConnected(User sender, User receiver) {
        return sender.getConnections().stream()
                .anyMatch(userConnection -> userConnection.getUsers().contains(receiver)
                        && !userConnection.isDeleted());
    }

    public boolean isRequestAlreadySent(ConnectionRequest connectionRequest) {
        return connectionRequestDao.find(connectionRequest).isPresent();
    }

    public boolean isRequestAlreadyReceived(User currentUser, User user) {
        return isRequestAlreadySent(user, currentUser);
    }
}
