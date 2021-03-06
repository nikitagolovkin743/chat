package com.golovkin.chat.web.servlets;

import com.golovkin.chat.data.dao.UserDao;
import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.web.utils.RequestUtils;
import com.golovkin.chat.web.utils.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserlistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext webContext = new WebContext(req, resp, getServletContext());
        User currentUser = RequestUtils.getSessionAttribute("currentUser", User.class, req);

        List<User> users = UserDao.findAllUsersExceptId(currentUser.getId());
        webContext.setVariable("users", users);

        TemplateEngine.renderPage("users", webContext, resp);
    }
}
