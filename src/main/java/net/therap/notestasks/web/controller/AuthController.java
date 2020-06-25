package net.therap.notestasks.web.controller;

import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import net.therap.notestasks.validator.UserPersistedWithCredentialValidator;
import net.therap.notestasks.validator.UserWithDistinctEmailValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static net.therap.notestasks.helper.UrlHelper.redirectTo;
import static net.therap.notestasks.util.Constants.CURRENT_USER_LABEL;
import static net.therap.notestasks.util.Constants.DASHBOARD_PAGE_PATH;

/**
 * @author tanmoy.das
 * @since 4/8/20
 */
@Controller
public class AuthController {

    public static final String HOMEPAGE_PATH = "/";
    public static final String INDEX_PAGE = "index";

    public static final String REGISTER_USER_COMMAND_NAME = "registerUserCommand";
    public static final String LOGIN_USER_COMMAND_NAME = "loginUserCommand";

    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    @Autowired
    private UserWithDistinctEmailValidator userWithDistinctEmailValidator;

    @Autowired
    private UserPersistedWithCredentialValidator userPersistedWithCredentialValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showHomePage(ModelMap model, HttpSession session) {
        if (isAuthenticated(session)) {
            return redirectTo(DASHBOARD_PAGE_PATH);
        } else {
            setupModelUserCommands(model, new User(), new User());

            return INDEX_PAGE;
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String handleLogout(HttpSession session) {
        session.invalidate();

        return redirectTo(HOMEPAGE_PATH);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String handleRegister(@Valid @ModelAttribute(REGISTER_USER_COMMAND_NAME) User user,
                                 BindingResult bindingResult,
                                 ModelMap model,
                                 HttpSession session) throws NoSuchAlgorithmException {

        userWithDistinctEmailValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            user.setPassword("");
            setupModelUserCommands(model, user, new User());
            return INDEX_PAGE;
        }

        User persistedUser = userService.createOrUpdateUser(user);
        logger.info("User created with email {}", persistedUser.getEmail());

        session.setAttribute(CURRENT_USER_LABEL, persistedUser);
        return redirectTo(HOMEPAGE_PATH);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String handleLogin(@Valid @ModelAttribute(LOGIN_USER_COMMAND_NAME) User user,
                              BindingResult bindingResult,
                              ModelMap model,
                              HttpSession session) throws NoSuchAlgorithmException {

        userPersistedWithCredentialValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            user.setPassword("");
            setupModelUserCommands(model, new User(), user);
            return INDEX_PAGE;
        }

        Optional<User> userOptional = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        userOptional.ifPresent(value -> session.setAttribute(CURRENT_USER_LABEL, value));

        return redirectTo(HOMEPAGE_PATH);
    }

    private void setupModelUserCommands(ModelMap model, User registerUserCommand, User loginUserCommand) {
        model.addAttribute(REGISTER_USER_COMMAND_NAME, registerUserCommand);
        model.addAttribute(LOGIN_USER_COMMAND_NAME, loginUserCommand);
    }

    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute(CURRENT_USER_LABEL) != null;
    }
}
