package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.config.Constants;
import net.therap.notestasks.domain.User;
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

import static net.therap.notestasks.config.Constants.CURRENT_USER;
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
    private UserConnectionService userConnectionService;

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String showOwnProfile(@SessionAttribute(CURRENT_USER) User currentUser, ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        return showUserProfile(currentUser, currentUser, model, req, resp);
    }

    @RequestMapping(value = {"/profile/{id}"}, method = RequestMethod.GET)
    public String showUserProfile(@PathVariable("id") User user, @SessionAttribute(CURRENT_USER) User currentUser,
                                  ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());

        currentUser = userService.findUserByEmail(currentUser.getEmail()).orElse(null);
        setupUserDataInModel(user, currentUser, model);

        return Constants.DASHBOARD_PAGE;
    }

    @RequestMapping(value = {"/profile/update"}, method = RequestMethod.POST)
    public String updateUserProfile(@Valid @ModelAttribute(CURRENT_USER) User user, Errors errors,
                                    @RequestParam("newPassword") String newPassword,
                                    @SessionAttribute(CURRENT_USER) User currentUser, ModelMap model,
                                    HttpServletRequest req, HttpServletResponse resp) throws NoSuchAlgorithmException {

        currentUser = userService.refreshUser(currentUser);
        model.addAttribute(CURRENT_USER, currentUser);

        user.setPassword(HashingUtil.sha256Hash(user.getPassword()));

        if (!hasSameCredentials(user, currentUser)) {
            setupUserDataInModel(user, currentUser, model);

            model.addAttribute("searchQuery", new SearchQuery());
            errors.rejectValue(null, "credentialIncorrect");
            model.addAttribute(CURRENT_USER, user);


            return DASHBOARD_PAGE;
        }

        userService.updateUser(user);

        if (!newPassword.isEmpty()) {
            userService.changePassword(user, HashingUtil.sha256Hash(newPassword));
        }

        return Constants.REDIRECT_PROFILE;
    }

    private void setupUserDataInModel(@PathVariable("id") User user, @SessionAttribute(CURRENT_USER) User currentUser, ModelMap model) {
        model.addAttribute(CURRENT_USER, currentUser);

        model.put(Constants.USER_TXT, user);

        model.addAttribute("isProfilePage", true);

        if (user.getId() == currentUser.getId()) {
            model.addAttribute(Constants.IS_MYSELF_TXT, true);
        }

        boolean alreadyConnected = userConnectionService.isAlreadyConnected(currentUser, user);
        if (alreadyConnected) {
            model.addAttribute(Constants.IS_USER_CONNECTED_TXT, true);
        }

        boolean requestAlreadySent = userConnectionService.isRequestAlreadySent(currentUser, user);
        if (requestAlreadySent) {
            model.addAttribute(Constants.IS_REQUEST_SENT_TXT, true);
        }

        boolean requestAlreadyReceived = userConnectionService.isRequestAlreadyReceived(currentUser, user);
        if (requestAlreadyReceived) {
            model.addAttribute(Constants.IS_REQUEST_RECEIVED_TXT, true);
        }
    }

    private boolean hasSameCredentials(@Valid User user1, User user2) {
        return user2.getEmail().equals(user1.getEmail()) && user2.getPassword().equals(user1.getPassword());
    }
}
