package net.therap.notestasks.web.controller;

import net.therap.notestasks.dao.ConnectionRequestDao;
import net.therap.notestasks.domain.ConnectionRequest;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.exception.UserConnectionAlreadyExistsException;
import net.therap.notestasks.service.UserConnectionService;
import net.therap.notestasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import static net.therap.notestasks.helper.UrlHelper.getUrl;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;
import static net.therap.notestasks.util.Constants.CURRENT_USER;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class ConnectionController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConnectionService userConnectionService;

    @Autowired
    private ConnectionRequestDao connectionRequestDao;

    @RequestMapping(value = "/connection/remove/{userId}", method = RequestMethod.GET)
    public String removeConnection(@PathVariable("userId") User user,
                                   @SessionAttribute(CURRENT_USER) User currentUser) {

        user.getConnections().stream()
                .filter(connection -> connection.getUsers().contains(userService.findUserWithSameEmail(currentUser)))
                .filter(connection -> !connection.isDeleted())
                .findFirst()
                .ifPresent(connection -> userConnectionService.removeUserConnection(connection));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/cancel/{userId}", method = RequestMethod.GET)
    public String cancelConnectionRequest(@PathVariable("userId") User user,
                                          @SessionAttribute(CURRENT_USER) User currentUser) {
        currentUser = userService.findUserWithSameEmail(currentUser);

        currentUser.getSentConnectionRequests().stream()
                .filter(connectionRequest -> connectionRequest.getReceiver().getId() == user.getId())
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .findFirst()
                .ifPresent(connectionRequest -> userConnectionService.cancelConnectionRequest(connectionRequest));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/reject/{userId}", method = RequestMethod.GET)
    public String rejectConnectionRequest(@PathVariable("userId") User user,
                                          @SessionAttribute(CURRENT_USER) User currentUser) {
        currentUser = userService.findUserWithSameEmail(currentUser);

        currentUser.getReceivedConnectionRequests().stream()
                .filter(connectionRequest -> connectionRequest.getSender().getId() == user.getId())
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .findFirst()
                .ifPresent(connectionRequest -> userConnectionService.cancelConnectionRequest(connectionRequest));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/accept/{userId}", method = RequestMethod.GET)
    public String acceptConnectionRequest(@PathVariable("userId") User user,
                                          @SessionAttribute(CURRENT_USER) User currentUser) {

        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);
        if (userConnectionService.isAlreadyConnected(persistedCurrentUser, user)) {
            throw new UserConnectionAlreadyExistsException();
        }

        persistedCurrentUser.getReceivedConnectionRequests().stream()
                .filter(connectionRequest -> connectionRequest.getSender().getId() == user.getId())
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .findFirst()
                .ifPresent(connectionRequest -> userConnectionService.acceptConnectionRequest(connectionRequest));

        return redirectTo(getUrl(user));
    }

    @RequestMapping(value = "/connection/send/{userId}", method = RequestMethod.GET)
    public String sendConnectionRequest(@PathVariable("userId") User user,
                                        @SessionAttribute(CURRENT_USER) User currentUser) {
        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        if (userConnectionService.isAlreadyConnected(persistedCurrentUser, user)) {
            throw new UserConnectionAlreadyExistsException();
        }

        ConnectionRequest request = new ConnectionRequest();
        request.setSender(persistedCurrentUser);
        request.setReceiver(user);
        userConnectionService.sendConnectionRequest(request);

        return redirectTo(getUrl(user));
    }
}
