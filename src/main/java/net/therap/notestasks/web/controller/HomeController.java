package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.BasicEntity;
import net.therap.notestasks.domain.ConnectionRequest;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.config.Constants.*;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String showHomePage(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        if (isAuthenticated(req)) {
            return REDIRECT_DASHBOARD;
        } else {
            model.addAttribute(LOGIN_USER_COMMAND, new User());
            model.addAttribute(REGISTER_USER_COMMAND, new User());

            return INDEX_PAGE;
        }
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String showDashboard(@SessionAttribute(CURRENT_USER) User currentUser, ModelMap model) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isDashboardPage", true);
        model.addAttribute(CURRENT_USER, currentUser);

        User persistedUser = userService.refreshUser(currentUser);

        List<ConnectionRequest> connectionRequests = persistedUser.getReceivedConnectionRequests().stream()
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .sorted(Comparator.comparing(BasicEntity::getUpdatedOn))
                .collect(Collectors.toList());
        model.addAttribute("connectionRequests", connectionRequests);

        return DASHBOARD_PAGE;
    }

    private boolean isAuthenticated(HttpServletRequest req) {
        return req.getSession().getAttribute(CURRENT_USER) != null;
    }
}
