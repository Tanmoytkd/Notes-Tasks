package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.Message;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.MessageService;
import net.therap.notestasks.service.UserConnectionService;
import net.therap.notestasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.therap.notestasks.helper.UrlHelper.getMessageUrl;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;
import static net.therap.notestasks.util.Constants.CURRENT_USER_LABEL;
import static net.therap.notestasks.util.Constants.MESSAGES_PAGE_PATH;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class MessageController {

    private static final String MESSAGES_PAGE = "messages";

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConnectionService userConnectionService;

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String sendMessage(@ModelAttribute("messageCommand") Message message) {

        if (canSendMessage(message.getSender(), message.getReceiver())) {
            messageService.sendMessage(message);
        }

        return redirectTo(getMessageUrl(message.getReceiver()));
    }

    @RequestMapping(value = "/message/delete/{messageId}", method = RequestMethod.GET)
    public String deleteMessage(@PathVariable("messageId") Message message,
                                @SessionAttribute(CURRENT_USER_LABEL) User currentUser) {

        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);
        if (message.getSender().getId() == persistedCurrentUser.getId()) {
            messageService.deleteMessage(message);
        }

        return redirectTo(getMessageUrl(message.getReceiver()));
    }

    @RequestMapping(value = "/messages/{userId}", method = RequestMethod.GET)
    public String showMessagesFromUser(@PathVariable("userId") User user,
                                       @SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                                       ModelMap model) {
        currentUser = userService.findUserWithSameEmail(currentUser);
        if (!canSendMessage(currentUser, user)) {
            return redirectTo(MESSAGES_PAGE_PATH);
        }

        model.addAttribute("currentMessagedUser", user);

        List<Message> messages = messageService.findAllMessagesGroupedByUsers(currentUser)
                .getOrDefault(user, new ArrayList<>());
        Collections.reverse(messages);
        model.addAttribute("messages", messages);

        return showMessages(currentUser, model);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String showMessages(@SessionAttribute(CURRENT_USER_LABEL) User currentUser,
                               ModelMap model) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isMessagesPage", true);

        User managedCurrentUser = userService.findUserWithSameEmail(currentUser);
        model.addAttribute("messageCommand", new Message());

        List<User> allMessagedUsers = messageService.findAllMessagedUsers(managedCurrentUser);
        model.addAttribute("allMessagedUsers", allMessagedUsers);

        Map<User, List<Message>> allMessageGroupedByUsers = messageService.findAllMessagesGroupedByUsers(managedCurrentUser);
        model.addAttribute("allMessageGroupedByUsers", allMessageGroupedByUsers);

        return MESSAGES_PAGE;
    }

    public boolean canSendMessage(User sender, User receiver) {
        if (userConnectionService.isAlreadyConnected(sender, receiver)) {
            return true;
        } else {
            return hasSentMessage(sender, receiver) || hasReceivedMessage(sender, receiver);
        }
    }

    private boolean hasReceivedMessage(User sender, User receiver) {
        return sender.getReceivedMessages().stream()
                .anyMatch(message -> message.getSender().equals(receiver));
    }

    private boolean hasSentMessage(User sender, User receiver) {
        return sender.getSentMessages().stream()
                .anyMatch(message -> message.getReceiver().equals(receiver));
    }
}
