package net.therap.notestasks.service;

import net.therap.notestasks.dao.MessageDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.BasicEntity;
import net.therap.notestasks.domain.Message;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
@Service
public class MessageService {

    @Autowired
    private UserConnectionService userConnectionService;

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    public void sendMessage(Message message) {
        User sender = message.getSender();
        User receiver = message.getReceiver();

        sender.getSentMessages().add(message);
        receiver.getReceivedMessages().add(message);

        messageDao.saveOrUpdate(message);
        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);
    }

    @Transactional
    public void updateMessage(Message message) {
        messageDao.saveOrUpdate(message);
    }

    @Transactional
    public void deleteMessage(Message message) {
        User sender = message.getSender();
        User receiver = message.getReceiver();

        sender.getSentMessages().remove(message);
        receiver.getReceivedMessages().remove(message);

        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);

        message.setDeleted(true);
        messageDao.saveOrUpdate(message);
    }

    @Transactional
    public void markMessageAsRead(Message message) {
        message.setSeen(true);
        messageDao.saveOrUpdate(message);
    }

    public Map<User, List<Message>> findAllMessagesGroupedByUsers(User user) {
        User persistedUser = userDao.find(user).orElseThrow(InvalidUserException::new);

        List<Message> messageList = new ArrayList<>();
        messageList.addAll(persistedUser.getSentMessages());
        messageList.addAll(persistedUser.getReceivedMessages());

        return messageList.stream()
                .filter(message -> !message.isDeleted())
                .sorted(Comparator.comparing(BasicEntity::getCreatedOn))
                .collect(Collectors.groupingBy(message -> {
                    if (message.getSender().equals(persistedUser)) {
                        return message.getReceiver();
                    } else {
                        return message.getSender();
                    }
                }));
    }

    public List<User> findAllMessagedUsers(User user) {
        return new ArrayList<>(findAllMessagesGroupedByUsers(user).keySet());
    }

    public Optional<Message> findMessageById(long userId) {
        return messageDao.find(userId);
    }
}
