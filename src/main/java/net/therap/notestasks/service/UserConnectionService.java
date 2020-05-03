package net.therap.notestasks.service;

import net.therap.notestasks.dao.ConnectionRequestDao;
import net.therap.notestasks.dao.UserConnectionDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.ConnectionRequest;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.domain.UserConnection;
import net.therap.notestasks.exception.DuplicateConnectionRequestException;
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
@Transactional
public class UserConnectionService {

    @Autowired
    private ConnectionRequestDao connectionRequestDao;

    @Autowired
    private UserConnectionDao connectionDao;

    @Autowired
    private UserDao userDao;

    public void sendConnectionRequest(ConnectionRequest connectionRequest) {
        if (isRequestAlreadySent(connectionRequest)) {
            throw new DuplicateConnectionRequestException();
        }

        User sender = userDao.refresh(connectionRequest.getSender());
        User receiver = userDao.refresh(connectionRequest.getReceiver());
        if (isAlreadyConnected(sender, receiver)) {
            throw new UserConnectionAlreadyExistsException();
        }

        createConnectionRequest(connectionRequest);
    }

    public boolean isRequestAlreadySent(User sender, User receiver) {
        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setSender(sender);
        connectionRequest.setReceiver(receiver);
        return isRequestAlreadySent(connectionRequest);
    }

    public boolean isRequestAlreadySent(ConnectionRequest connectionRequest) {
        return connectionRequestDao.findByExample(connectionRequest).isPresent();
    }

    public void acceptConnectionRequest(ConnectionRequest connectionRequest) {
        connectionRequest = connectionRequestDao.refresh(connectionRequest);

        User sender = connectionRequest.getSender();
        User receiver = connectionRequest.getReceiver();
        if (isAlreadyConnected(sender, receiver)) {
            throw new UserConnectionAlreadyExistsException();
        }
        deleteConnectionRequest(connectionRequest);

        createConnection(sender, receiver);
    }

    public void rejectConnectionRequest(ConnectionRequest connectionRequest) {
        connectionRequest = connectionRequestDao.refresh(connectionRequest);

        deleteConnectionRequest(connectionRequest);
    }

    public void removeUserConnection(UserConnection connection) {
        connection = connectionDao.refresh(connection);

        for (User user : connection.getUsers()) {
            user.getConnections().remove(connection);
            userDao.saveOrUpdate(user);
        }

        connectionDao.delete(connection);
    }

    private void createConnection(User sender, User receiver) {
        UserConnection connection = new UserConnection();
        connection.setUsers(Arrays.asList(sender, receiver));
        connectionDao.saveOrUpdate(connection);

        sender.getConnections().add(connection);
        receiver.getConnections().add(connection);

        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);
    }

    private void deleteConnectionRequest(ConnectionRequest connectionRequest) {
        User sender = connectionRequest.getSender();
        User receiver = connectionRequest.getReceiver();

        sender.getSentConnectionRequests().remove(connectionRequest);
        receiver.getReceivedConnectionRequests().remove(connectionRequest);

        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);
        connectionRequestDao.delete(connectionRequest);
    }

    public boolean isAlreadyConnected(User sender, User receiver) {
        return userDao.refresh(sender).getConnections().stream()
                .anyMatch(userConnection -> userConnection.getUsers().contains(receiver));
    }

    private void createConnectionRequest(ConnectionRequest connectionRequest) {
        User sender = userDao.refresh(connectionRequest.getSender());
        User receiver = userDao.refresh(connectionRequest.getReceiver());

        connectionRequestDao.saveOrUpdate(connectionRequest);

        sender.getSentConnectionRequests().add(connectionRequest);
        receiver.getReceivedConnectionRequests().add(connectionRequest);
        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);
    }

    public boolean isRequestAlreadyReceived(User currentUser, User user) {
        return isRequestAlreadySent(user, currentUser);
    }
}
