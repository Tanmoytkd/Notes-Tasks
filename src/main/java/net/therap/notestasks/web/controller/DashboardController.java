package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.BasicEntity;
import net.therap.notestasks.domain.ConnectionRequest;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.therap.notestasks.util.Constants.*;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Controller
public class DashboardController {

    public static final String DASHBOARD_PAGE = "dashboard";
    public static final String IS_DASHBOARD_PAGE = "isDashboardPage";

    @Autowired
    private UserService userService;

    @RequestMapping(value = "dashboard", method = RequestMethod.GET)
    public String showDashboard(@SessionAttribute(CURRENT_USER_LABEL) User currentUser, ModelMap model) {
        User persistedCurrentUser = userService.findUserWithSameEmail(currentUser);
        setupConnectionRequestsInModel(model, persistedCurrentUser);

        return DASHBOARD_PAGE;
    }

    private void setupConnectionRequestsInModel(ModelMap model, User user) {
        List<ConnectionRequest> connectionRequests = user.getReceivedConnectionRequests().stream()
                .filter(connectionRequest -> !connectionRequest.isDeleted())
                .sorted(Comparator.comparing(BasicEntity::getUpdatedOn))
                .collect(Collectors.toList());

        model.addAttribute(CONNECTION_REQUESTS_LABEL, connectionRequests);
    }

    @ModelAttribute(SEARCH_QUERY_LABEL)
    private SearchQuery searchQuery() {
        return new SearchQuery();
    }

    @ModelAttribute(IS_DASHBOARD_PAGE)
    private boolean isDashboardPage() {
        return true;
    }
}
