package com.golovkin.chat.web.filters;

import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.web.utils.IoUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class ReadonlyFilter implements Filter {
    private Set<String> readonlyUserNames;

    @Override
    public void init(FilterConfig filterConfig) {
        InputStream readonlyUsers = ReadonlyFilter.class.getResourceAsStream("/ReadonlyUsers.txt");

        readonlyUserNames = IoUtils.getSetWithLinesFrom(readonlyUsers);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        User currentUser = (User) httpServletRequest.getSession().getAttribute("currentUser");
        String currentUserName = currentUser.getName();

        boolean isUserReadonly = readonlyUserNames.contains(currentUserName);
        httpServletRequest.setAttribute("readonly", isUserReadonly);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
