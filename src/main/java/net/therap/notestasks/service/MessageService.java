package net.therap.notestasks.service;

import net.therap.notestasks.dao.MessageDao;
import net.therap.notestasks.dao.UserDao;
import net.therap.notestasks.domain.BasicEntity;
import net.therap.notestasks.domain.Message;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.exception.MessagingNotPermittedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tanmoy.das
 * @since 4/24/20
 */
@Service
@Transactional
public class MessageService {

    @Autowired
    private UserConnectionService userConnectionService;

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private UserDao userDao;

    public void sendMessage(Message message) {
        User sender = userDao.refresh(message.getSender());
        User receiver = userDao.refresh(message.getReceiver());

        if (!canSendMessage(sender, receiver)) {
            throw new MessagingNotPermittedException(sender, receiver);
        }

        sender.getSentMessages().add(message);
        receiver.getReceivedMessages().add(message);

        messageDao.saveOrUpdate(message);
        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);
    }

    public void updateMessage(Message message) {
        messageDao.saveOrUpdate(message);
    }

    public void deleteMessage(Message message) {
        User sender = message.getSender();
        User receiver = message.getReceiver();

        sender.getSentMessages().remove(message);
        receiver.getReceivedMessages().remove(message);

        userDao.saveOrUpdate(sender);
        userDao.saveOrUpdate(receiver);

        messageDao.delete(message);
    }

    public void markMessageAsRead(Message message) {
        message.setSeen(true);
        messageDao.saveOrUpdate(message);
    }

    public Map<User, List<Message>> findAllMessageGroupedByUsers(User user) {
        User persistedUser = userDao.refresh(user);

        List<Message> messageList = new ArrayList<>();
        messageList.addAll(persistedUser.getSentMessages());
        messageList.addAll(persistedUser.getReceivedMessages());

        return messageList.stream()
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
        return new ArrayList<>(findAllMessageGroupedByUsers(user).keySet());
    }

    public boolean canSendMessage(User sender, User receiver) {
        sender = userDao.refresh(sender);
        receiver = userDao.refresh(receiver);

        if (userConnectionService.isAlreadyConnected(sender, receiver)) {
            return true;
        } else {
            return hasSentMessage(sender, receiver) || hasReceivedMessage(sender, receiver);
        }
    }

    private boolean hasReceivedMessage(User sender, User receiver) {
        sender = userDao.refresh(sender);

        return sender.getReceivedMessages().stream()
                .anyMatch(message -> {
                    return message.getSender().equals(userDao.refresh(receiver));
                });
    }

    private boolean hasSentMessage(User sender, User receiver) {
        sender = userDao.refresh(sender);

        return sender.getSentMessages().stream()
                .anyMatch(message -> message.getReceiver().equals(userDao.refresh(receiver)));
    }
}
