package net.therap.notestasks.web.controller;

import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import net.therap.notestasks.util.HashingUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static net.therap.notestasks.config.Constants.*;

/**
 * @author tanmoy.das
 * @since 4/8/20
 */
@Controller
public class AuthController {

    public static final String REDIRECT_ROOT = "redirect:/";

    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String handleLogout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.invalidate();

        return REDIRECT_ROOT;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String handleRegister(@Valid @ModelAttribute(REGISTER_USER_COMMAND) User user, Errors errors, ModelMap model,
                                 HttpServletRequest req, HttpServletResponse resp) throws NoSuchAlgorithmException {
        if (errors.hasErrors()) {
            model.addAttribute(REGISTER_USER_COMMAND, user);
            model.addAttribute(LOGIN_USER_COMMAND, new User());
            return INDEX_PAGE;
        }

        user.setPassword(HashingUtil.sha256Hash(user.getPassword()));

        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            logger.warn("Duplicate Email for user: {}", user.getEmail());

            errors.rejectValue(null, "duplicateEmailError");
            user.setPassword("");
            model.addAttribute(LOGIN_USER_COMMAND, new User());

            return INDEX_PAGE;
        }

        user = userService.createUser(user);
        req.getSession().setAttribute(CURRENT_USER_TXT, user);
        logger.info("User created with email {}", user.getEmail());

        return REDIRECT_ROOT;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String handleLogin(@Valid @ModelAttribute(LOGIN_USER_COMMAND) User user, Errors errors, ModelMap model,
                              HttpServletRequest req, HttpServletResponse resp) throws NoSuchAlgorithmException {
        if (errors.hasErrors()) {
            model.addAttribute(LOGIN_USER_COMMAND, user);
            model.addAttribute(REGISTER_USER_COMMAND, new User());
            return INDEX_PAGE;
        }

        user.setPassword(HashingUtil.sha256Hash(user.getPassword()));

        Optional<User> optionalUser = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());

        if (!optionalUser.isPresent()) {
            errors.rejectValue(null, "credentialIncorrect");

            user.setPassword("");
            model.addAttribute(REGISTER_USER_COMMAND, new User());
            return INDEX_PAGE;
        }

        req.getSession().setAttribute(CURRENT_USER_TXT, optionalUser.get());
        return REDIRECT_ROOT;
    }
}
