package net.therap.notestasks.web.controller;

import net.therap.notestasks.domain.ConnectionRequest;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserConnectionService;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import static net.therap.notestasks.config.Constants.CURRENT_USER_COMMAND;
import static net.therap.notestasks.helper.UrlHelper.getUrl;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class ConnectionController {

    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConnectionService userConnectionService;

    @RequestMapping(value = "/connection/remove/{userId}", method = RequestMethod.GET)
    public String removeConnection(@PathVariable("userId") User user,
                                   @SessionAttribute(CURRENT_USER_COMMAND) User currentUser) {

        user.getConnections().stream()
                .filter(connection -> connection.getUsers().contains(userService.refreshUser(currentUser)))
                .filter(connection -> !connection.isDeleted())
                .findFirst()
                .ifPresent(connection -> userConnectionService.removeUserConnection(connection));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/cancel/{userId}", method = RequestMethod.GET)
    public String cancelConnectionRequest(@PathVariable("userId") User user,
                                          @SessionAttribute(CURRENT_USER_COMMAND) User currentUser) {
        currentUser = userService.refreshUser(currentUser);

        currentUser.getSentConnectionRequests().stream()
                .filter(connectionRequest -> connectionRequest.getReceiver().getId() == user.getId())
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .findFirst()
                .ifPresent(connectionRequest -> userConnectionService.cancelConnectionRequest(connectionRequest));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/reject/{userId}", method = RequestMethod.GET)
    public String rejectConnectionRequest(@PathVariable("userId") User user,
                                          @SessionAttribute(CURRENT_USER_COMMAND) User currentUser) {
        currentUser = userService.refreshUser(currentUser);

        currentUser.getReceivedConnectionRequests().stream()
                .filter(connectionRequest -> connectionRequest.getSender().getId() == user.getId())
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .findFirst()
                .ifPresent(connectionRequest -> userConnectionService.cancelConnectionRequest(connectionRequest));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/accept/{userId}", method = RequestMethod.GET)
    public String acceptConnectionRequest(@PathVariable("userId") User user,
                                          @SessionAttribute(CURRENT_USER_COMMAND) User currentUser) {
        currentUser = userService.refreshUser(currentUser);

        currentUser.getReceivedConnectionRequests().stream()
                .filter(connectionRequest -> connectionRequest.getSender().getId() == user.getId())
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .findFirst()
                .ifPresent(connectionRequest -> userConnectionService.acceptConnectionRequest(connectionRequest));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/send/{userId}", method = RequestMethod.GET)
    public String sendConnectionRequest(@PathVariable("userId") User user,
                                        @SessionAttribute(CURRENT_USER_COMMAND) User currentUser) {
        currentUser = userService.refreshUser(currentUser);

        ConnectionRequest request = new ConnectionRequest();
        request.setSender(currentUser);
        request.setReceiver(user);
        userConnectionService.sendConnectionRequest(request);

        return redirectTo(getUrl(user));
    }
}
