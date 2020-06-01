package net.therap.notestasks.web.controller;

import net.therap.notestasks.command.SearchQuery;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static net.therap.notestasks.config.Constants.DASHBOARD_PAGE;

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class ReportController {

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/reports"}, method = RequestMethod.GET)
    public String listReports(Locale locale, ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isReportsPage", true);
        return DASHBOARD_PAGE;
    }
}
