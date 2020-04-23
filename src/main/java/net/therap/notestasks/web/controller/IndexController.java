package net.therap.notestasks.web.controller;

import net.therap.notestasks.dao.UserDao;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author tanmoy.das
 * @since 4/23/20
 */
@Controller
public class IndexController {

    @Autowired
    private Logger logger;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index(Locale locale, HttpServletRequest request, HttpServletResponse response) {
        logger.info("Welcome home! The client locale is {}.", locale);

        return "index";
    }
}
