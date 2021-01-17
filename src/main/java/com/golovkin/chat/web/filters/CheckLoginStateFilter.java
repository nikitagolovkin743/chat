package com.golovkin.chat.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

public class CheckLoginStateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        boolean isUserLogined = httpServletRequest.getSession().getAttribute("currentUser") != null;
        String requestedUrl = httpServletRequest.getRequestURI();

        HashSet<String> forbiddenUrlsForLoginedUser = getForbiddenUrlsForLoginedUser();
        HashSet<String> forbiddenUrlsForNonLoginedUser = getForbiddenUrlsForNonLoginedUser();

        if (isUserLogined && forbiddenUrlsForLoginedUser.contains(requestedUrl)) {
            httpServletResponse.sendRedirect("/users");
        } else if (!isUserLogined && forbiddenUrlsForNonLoginedUser.contains(requestedUrl)) {
            httpServletResponse.sendRedirect("/login");
        } else {
            chain.doFilter(request, response);
        }
    }

    private HashSet<String> getForbiddenUrlsForNonLoginedUser() {
        var forbiddenUrlsForNonLoginedUser = new HashSet<String>();
        forbiddenUrlsForNonLoginedUser.add("/users");
        forbiddenUrlsForNonLoginedUser.add("/dialog");
        return forbiddenUrlsForNonLoginedUser;
    }

    private HashSet<String> getForbiddenUrlsForLoginedUser() {
        var forbiddenUrlsForLoginedUser = new HashSet<String>();
        forbiddenUrlsForLoginedUser.add("/login");
        forbiddenUrlsForLoginedUser.add("/logup");
        return forbiddenUrlsForLoginedUser;
    }

    @Override
    public void destroy() {

    }
}
