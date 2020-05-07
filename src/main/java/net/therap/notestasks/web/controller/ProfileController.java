package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.config.Constants;
import net.therap.notestasks.domain.BasicEntity;
import net.therap.notestasks.domain.Note;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.NoteService;
import net.therap.notestasks.service.UserConnectionService;
import net.therap.notestasks.service.UserService;
import net.therap.notestasks.util.HashingUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.config.Constants.CURRENT_USER_COMMAND;
import static net.therap.notestasks.config.Constants.DASHBOARD_PAGE;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class ProfileController {

    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserConnectionService userConnectionService;

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String showOwnProfile(@SessionAttribute(CURRENT_USER_COMMAND) User currentUser, ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        return showUserProfile(currentUser, currentUser, model, req, resp);
    }

    @RequestMapping(value = {"/profile/{id}"}, method = RequestMethod.GET)
    public String showUserProfile(@PathVariable("id") User user, @SessionAttribute(CURRENT_USER_COMMAND) User currentUser,
                                  ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());

        currentUser = userService.findUserByEmail(currentUser.getEmail()).orElse(null);
        setupUserDataInModel(user, currentUser, model);

        return Constants.DASHBOARD_PAGE;
    }

    @RequestMapping(value = {"/profile/update"}, method = RequestMethod.POST)
    public String updateUserProfile(@Valid @ModelAttribute(CURRENT_USER_COMMAND) User user, Errors errors,
                                    @RequestParam("newPassword") String newPassword,
                                    @SessionAttribute(CURRENT_USER_COMMAND) User currentUser, ModelMap model,
                                    HttpServletRequest req, HttpServletResponse resp) throws NoSuchAlgorithmException {

        currentUser = userService.refreshUser(currentUser);
        model.addAttribute(CURRENT_USER_COMMAND, currentUser);

        user.setPassword(HashingUtil.sha256Hash(user.getPassword()));

        if (!hasSameCredentials(user, currentUser)) {
            setupUserDataInModel(user, currentUser, model);

            model.addAttribute("searchQuery", new SearchQuery());
            errors.rejectValue(null, "credentialIncorrect");
            model.addAttribute(CURRENT_USER_COMMAND, user);


            return DASHBOARD_PAGE;
        }

        userService.updateUser(user);

        if (!newPassword.isEmpty()) {
            userService.changePassword(user, HashingUtil.sha256Hash(newPassword));
        }

        return Constants.REDIRECT_PROFILE;
    }

    private void setupUserDataInModel(User user, User currentUser, ModelMap model) {
        User persistedCurrentUser = userService.refreshUser(currentUser);
        model.addAttribute(CURRENT_USER_COMMAND, persistedCurrentUser);

        User persistedUser = userService.refreshUser(user);
        model.put(Constants.USER_TXT, persistedUser);

        model.addAttribute("isProfilePage", true);

        if (persistedUser.getId() == currentUser.getId()) {
            model.addAttribute(Constants.IS_MYSELF_TXT, true);
        }

        List<User> connectedUsers = userService.getConnectedUsers(persistedUser);
        model.addAttribute("connectedUsers", connectedUsers);

        List<Note> accessibleNotes = persistedUser.getOwnNotes().stream()
                .filter(note -> !note.isDeleted())
                .filter(note -> noteService.hasReadAccess(persistedCurrentUser, note))
                .sorted(Comparator.comparing(BasicEntity::getUpdatedOn))
                .collect(Collectors.toList());
        model.addAttribute("accessibleNotes", accessibleNotes);

        boolean alreadyConnected = userConnectionService.isAlreadyConnected(currentUser, persistedUser);
        if (alreadyConnected) {
            model.addAttribute(Constants.IS_USER_CONNECTED_TXT, true);
        }

        boolean requestAlreadySent = userConnectionService.isRequestAlreadySent(currentUser, persistedUser);
        if (requestAlreadySent) {
            model.addAttribute(Constants.IS_REQUEST_SENT_TXT, true);
        }

        boolean requestAlreadyReceived = userConnectionService.isRequestAlreadyReceived(currentUser, persistedUser);
        if (requestAlreadyReceived) {
            model.addAttribute(Constants.IS_REQUEST_RECEIVED_TXT, true);
        }
    }

    private boolean hasSameCredentials(@Valid User user1, User user2) {
        return user2.getEmail().equals(user1.getEmail()) && user2.getPassword().equals(user1.getPassword());
    }
}
