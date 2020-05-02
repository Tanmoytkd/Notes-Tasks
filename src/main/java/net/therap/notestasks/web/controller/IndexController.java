package net.therap.notestasks.web.controller;

import net.therap.notestasks.config.Constants;
import net.therap.notestasks.domain.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static net.therap.notestasks.config.Constants.LOGIN_USER_COMMAND;
import static net.therap.notestasks.config.Constants.REGISTER_USER_COMMAND;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Controller
public class IndexController {

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index(Locale locale, ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        logger.info("Welcome home! The client locale is {}.", locale);

        model.addAttribute(LOGIN_USER_COMMAND, new User());
        model.addAttribute(REGISTER_USER_COMMAND, new User());

        if (isAuthenticated(req)) {
            return "dashboard";
        } else {
            return "index";
        }
    }

    private boolean isAuthenticated(HttpServletRequest req) {
        return req.getSession().getAttribute(Constants.USER_TXT) != null;
    }
}
