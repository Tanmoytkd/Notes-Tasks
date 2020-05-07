package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.Message;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.MessageService;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static net.therap.notestasks.config.Constants.*;
import static net.therap.notestasks.helper.UrlHelper.getMessageUrl;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/message"}, method = RequestMethod.POST)
    public String sendMessage(@ModelAttribute("messageCommand") Message message) {

        if (messageService.canSendMessage(message.getSender(), message.getReceiver())) {
            messageService.sendMessage(message);
        }

        return redirectTo(getMessageUrl(message.getReceiver()));
    }

    @RequestMapping(value = {"/message/delete/{messageId}"}, method = RequestMethod.GET)
    public String deleteMessage(@PathVariable("messageId") Message message,
                                @SessionAttribute(CURRENT_USER_COMMAND) User currentUser) {

        User persistedCurrentUser = userService.refreshUser(currentUser);
        if (message.getSender().getId() == persistedCurrentUser.getId()) {
            messageService.deleteMessage(message);
        }

        return redirectTo(getMessageUrl(message.getReceiver()));
    }

    @RequestMapping(value = {"/messages/{userId}"}, method = RequestMethod.GET)
    public String showMessagesFromUser(@PathVariable("userId") User user,
                                       @SessionAttribute(CURRENT_USER_COMMAND) User currentUser,
                                       ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        currentUser = userService.refreshUser(currentUser);
        if (!messageService.canSendMessage(currentUser, user)) {
            return REDIRECT_MESSAGES;
        }

        model.addAttribute("currentMessagedUser", user);

        List<Message> messages = messageService.findAllMessagesGroupedByUsers(currentUser)
                .getOrDefault(user, new ArrayList<>());
        Collections.reverse(messages);
        model.addAttribute("messages", messages);

        return showMessages(currentUser, model, req, resp);
    }

    @RequestMapping(value = {"/messages"}, method = RequestMethod.GET)
    public String showMessages(@SessionAttribute(CURRENT_USER_COMMAND) User currentUser, ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isMessagesPage", true);

        User managedCurrentUser = userService.refreshUser(currentUser);
        model.addAttribute("messageCommand", new Message());

        List<User> allMessagedUsers = messageService.findAllMessagedUsers(managedCurrentUser);
        model.addAttribute("allMessagedUsers", allMessagedUsers);

        Map<User, List<Message>> allMessageGroupedByUsers = messageService.findAllMessagesGroupedByUsers(managedCurrentUser);
        model.addAttribute("allMessageGroupedByUsers", allMessageGroupedByUsers);

        return DASHBOARD_PAGE;
    }
}
