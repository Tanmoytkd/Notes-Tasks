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

/**
 * @author tanmoy.das
 * @since 5/3/20
 */
@Controller
public class ReportController {

    private static final String REPORTS_PAGE = "reports";

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/reports"}, method = RequestMethod.GET)
    public String listReports(Locale locale, ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("searchQuery", new SearchQuery());
        model.addAttribute("isReportsPage", true);
        return REPORTS_PAGE;
    }
}
