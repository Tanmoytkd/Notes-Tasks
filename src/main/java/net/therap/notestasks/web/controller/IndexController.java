package net.therap.notestasks.web.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Controller
public class IndexController {

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String index(Locale locale, ModelMap model) {
        logger.info("Welcome home! The client locale is {}.", locale);

        return "index";
    }
}
