package net.therap.notestasks.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static net.therap.notestasks.util.Constants.CURRENT_USER;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
public class AuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    public static String[] GUEST_URLS = new String[]{
            "/login",
            "/register",
    };

    public static String[] ALL_ACCESS_URLS = new String[]{
            "/"
    };

    public static String[] ALL_ACCESS_PREFIXES = new String[]{
            "/lib/",
            "/css/",
            "/js/",
            "/img/",
            "/webfonts/"
    };


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getServletPath();

        if (hasAccess(req)) {
            logger.info("Access Granted at {}", url);
            chain.doFilter(request, response);
        } else {
            String userStatus = isUser(req) ? "User" : "Guest";
            logger.warn("Access not Granted to {} at {}", userStatus, url);

            resp.sendRedirect(req.getContextPath());
        }
    }

    private boolean hasAccess(HttpServletRequest req) {
        String url = req.getServletPath();

        if (Arrays.stream(ALL_ACCESS_PREFIXES).anyMatch(url::startsWith)) {
            return true;
        }

        if (Arrays.asList(ALL_ACCESS_URLS).contains(url)) {
            return true;
        }

        if (isUser(req) && !Arrays.asList(GUEST_URLS).contains(url)) {
            return true;
        }

        return isGuest(req) && Arrays.asList(GUEST_URLS).contains(url);
    }

    @Override
    public void destroy() {

    }

    public boolean isUser(HttpServletRequest request) {
        return request.getSession().getAttribute(CURRENT_USER) != null;
    }

    public boolean isGuest(HttpServletRequest request) {
        return !isUser(request);
    }
}