package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import net.therap.notestasks.domain.User;
import net.therap.notestasks.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class SearchController {

    private static final String SEARCH_PAGE = "search";
    @Autowired
    private Logger logger;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String listReports(@ModelAttribute("searchQuery") SearchQuery searchQuery, Errors errors, ModelMap model) {
        model.addAttribute("isSearchPage", true);

        List<User> users = userService.findUsersByNameOrEmail(searchQuery.getQuery());
        model.addAttribute("users", users);

        return SEARCH_PAGE;
    }
}
