package net.therap.notestasks.web.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
public class AuthFilter implements Filter {

    public static String[] GUEST_URLS = new String[]{
            "/login",
            "/register",
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String url = req.getServletPath();

        System.out.println(url);

        if (url.startsWith("/lib/") || url.startsWith("/css/") || url.startsWith("/js/") || url.startsWith("/img/")) {
            chain.doFilter(request, response);
        } else if (isUser(req)) {
            if (!Arrays.asList(GUEST_URLS).contains(url)) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("/");
            }
        } else {
            if (Arrays.asList(GUEST_URLS).contains(url)) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }
    }

    @Override
    public void destroy() {

    }

    public boolean isUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return false;
        }

        Cookie tokenCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("token"))
                .findAny()
                .orElse(null);

        return tokenCookie != null;
    }

    public boolean isGuest(HttpServletRequest request) {
        return !isUser(request);
    }
}