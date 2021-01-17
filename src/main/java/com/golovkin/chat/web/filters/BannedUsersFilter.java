package com.golovkin.chat.web.filters;

import com.golovkin.chat.web.utils.IoUtils;
import com.golovkin.chat.web.utils.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class BannedUsersFilter implements Filter {
    private Set<String> bannedUserNames;

    @Override
    public void init(FilterConfig filterConfig) {
        var bannedUsers = BannedUsersFilter.class.getResourceAsStream("/BannedUsers.txt");

        bannedUserNames = IoUtils.getSetWithLinesFrom(bannedUsers);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        var username = request.getParameter("login");
        var isPostMethod = httpServletRequest.getMethod().equals("POST");

        if (isUsernameBanned(username) && isPostMethod) {
            var webContext = new WebContext(httpServletRequest, httpServletResponse, request.getServletContext());
            webContext.setVariable("errorMessage", "Вы забанены. :(");
            TemplateEngine.renderPage("error", webContext, httpServletResponse);
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean isUsernameBanned(String username) {
        return username != null && bannedUserNames.contains(username);
    }

    @Override
    public void destroy() {

    }
}
