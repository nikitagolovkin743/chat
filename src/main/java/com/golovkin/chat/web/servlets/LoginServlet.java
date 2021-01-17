package com.golovkin.chat.web.servlets;

import com.golovkin.chat.web.utils.ValidationChecks;
import com.golovkin.chat.data.dao.UserDao;
import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.web.utils.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var webContext = new WebContext(req, resp, getServletContext());
        TemplateEngine.renderPage("login", webContext, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var username = req.getParameter("login");
        var password = req.getParameter("password");

        var webContext = new WebContext(req, resp, getServletContext());

        if (!ValidationChecks.isUsernameValid(username) || !ValidationChecks.isPasswordValid(password)) {
            webContext.setVariable("invalidCredentials", true);
            TemplateEngine.renderPage("login", webContext, resp);
            return;
        }

        User user = UserDao.instance.findByCredentials(username, password);
        if (user == null) {
            webContext.setVariable("invalidCredentials", true);
            TemplateEngine.renderPage("login", webContext, resp);
            return;
        }

        req.getSession().setAttribute("currentUser", user);
        resp.sendRedirect("/users");
    }
}
