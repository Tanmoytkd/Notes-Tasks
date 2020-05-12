package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.BasicEntity;
import net.therap.notestasks.domain.Note;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.NoteService;
import net.therap.notestasks.service.UserConnectionService;
import net.therap.notestasks.service.UserService;
import net.therap.notestasks.validator.UserPersistedWithCredentialValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.helper.UrlHelper.redirectTo;
import static net.therap.notestasks.util.Constants.*;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class ProfileController {

    private static final String PROFILE_PAGE = "profile";
    private static final String IS_PROFILE_PAGE = "isProfilePage";

    public static final String CURRENT_USER_COMMAND_NAME = "currentUserCommand";

    @Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserConnectionService userConnectionService;

    @Autowired
    private UserPersistedWithCredentialValidator userPersistedWithCredentialValidator;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showOwnProfile(@SessionAttribute(CURRENT_USER) User currentUser, ModelMap model) {
        model.addAttribute(CURRENT_USER_COMMAND_NAME, currentUser);
        setupUserDataInModel(model, currentUser, currentUser);

        return PROFILE_PAGE;
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public String showUserProfile(@PathVariable("id") User user,
                                  @SessionAttribute(CURRENT_USER) User currentUser,
                                  ModelMap model) {

        if (user.getId() == currentUser.getId()) {
            model.addAttribute(CURRENT_USER_COMMAND_NAME, currentUser);
        }

        setupUserDataInModel(model, user, currentUser);
        return PROFILE_PAGE;
    }

    @RequestMapping(value = "/profile/update", method = RequestMethod.POST)
    public String updateUserProfile(@Valid @ModelAttribute(CURRENT_USER_COMMAND_NAME) User userCommand,
                                    BindingResult bindingResult,
                                    @RequestParam("newPassword") String newPassword,
                                    @SessionAttribute(CURRENT_USER) User currentUser,
                                    ModelMap model,
                                    HttpSession session) throws NoSuchAlgorithmException {

        userPersistedWithCredentialValidator.validate(userCommand, bindingResult);

        if (bindingResult.hasErrors()) {
            setupUserDataInModel(model, currentUser, currentUser);
            model.addAttribute(CURRENT_USER_COMMAND_NAME, userCommand);

            return PROFILE_PAGE;
        }

        if (!newPassword.isEmpty()) {
            userCommand.setPassword(newPassword);
        }
        userService.createOrUpdateUser(userCommand);

        session.setAttribute(CURRENT_USER, userCommand);

        return redirectTo(PROFILE_PAGE_PATH);
    }

    private void setupUserDataInModel(ModelMap model, User user, User currentUser) {
        User persistedUser = userService.findUserWithSameEmail(user);
        model.put(USER_LABEL, persistedUser);

        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);

        List<User> connectedUsers = userService.findConnectedUsers(persistedUser);
        model.addAttribute("connectedUsers", connectedUsers);

        List<Note> accessibleNotes = persistedUser.getOwnNotes().stream()
                .filter(note -> !note.isDeleted())
                .filter(note -> noteService.hasReadAccess(persistedCurrentUser, note))
                .sorted(Comparator.comparing(BasicEntity::getUpdatedOn))
                .collect(Collectors.toList());
        model.addAttribute("accessibleNotes", accessibleNotes);

        model.addAttribute(IS_MYSELF, persistedCurrentUser.getId() == persistedUser.getId());

        model.addAttribute(IS_USER_CONNECTED,
                userConnectionService.isAlreadyConnected(persistedCurrentUser, persistedUser));

        model.addAttribute(IS_REQUEST_SENT,
                userConnectionService.isRequestAlreadySent(persistedCurrentUser, persistedUser));

        model.addAttribute(IS_REQUEST_RECEIVED,
                userConnectionService.isRequestAlreadyReceived(persistedCurrentUser, persistedUser));
    }

    @ModelAttribute(SEARCH_QUERY_LABEL)
    private SearchQuery searchQuery() {
        return new SearchQuery();
    }

    @ModelAttribute(IS_PROFILE_PAGE)
    private boolean isProfilePage() {
        return true;
    }
}
