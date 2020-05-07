package net.therap.notestasks.web.controller;

import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import net.therap.notestasks.util.HashingUtil;
import net.therap.notestasks.validator.UserPersistedWithCredentialValidator;
import net.therap.notestasks.validator.UserWithDistinctEmailValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static net.therap.notestasks.config.Constants.*;
import static net.therap.notestasks.helper.UrlHelper.redirectTo;

/**
 * @author tanmoy.das
 * @since 4/8/20
 */
@Controller
public class AuthController {

    public static final String HOMEPAGE_PATH = "";
    public static final String INDEX_PAGE = "index";

    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    @Autowired
    private UserWithDistinctEmailValidator userWithDistinctEmailValidator;

    @Autowired
    private UserPersistedWithCredentialValidator userPersistedWithCredentialValidator;

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String handleLogout(HttpSession session) {
        session.invalidate();

        return redirectTo(HOMEPAGE_PATH);
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String handleRegister(@Valid @ModelAttribute(REGISTER_USER_COMMAND) User user,
                                 Errors errors,
                                 ModelMap model,
                                 HttpSession session) throws NoSuchAlgorithmException {

        userWithDistinctEmailValidator.validate(user, errors);
        if (errors.hasErrors()) {
            user.setPassword("");
            setupModelUserCommands(model, user, new User());
            return INDEX_PAGE;
        }

        user.setPassword(HashingUtil.sha256Hash(user.getPassword()));
        User persistedUser = userService.createUser(user);
        logger.info("User created with email {}", persistedUser.getEmail());

        session.setAttribute(CURRENT_USER_COMMAND, persistedUser);
        return redirectTo(HOMEPAGE_PATH);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String handleLogin(@Valid @ModelAttribute(LOGIN_USER_COMMAND) User user,
                              Errors errors,
                              ModelMap model,
                              HttpSession session) throws NoSuchAlgorithmException {

        user.setPassword(HashingUtil.sha256Hash(user.getPassword()));
        userPersistedWithCredentialValidator.validate(user, errors);

        if (errors.hasErrors()) {
            user.setPassword("");
            setupModelUserCommands(model, new User(), user);
            return redirectTo(INDEX_PAGE);
        }

        Optional<User> userOptional = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        userOptional.ifPresent(value -> session.setAttribute(CURRENT_USER_COMMAND, value));

        return redirectTo(HOMEPAGE_PATH);
    }

    private void setupModelUserCommands(ModelMap model, User registerUserCommand, User loginUserCommand) {
        model.addAttribute(REGISTER_USER_COMMAND, registerUserCommand);
        model.addAttribute(LOGIN_USER_COMMAND, loginUserCommand);
    }
}
