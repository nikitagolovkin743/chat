package com.golovkin.chat.web.servlets;

import com.golovkin.chat.data.dao.UserDao;
import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.web.utils.TemplateEngine;
import com.golovkin.chat.web.utils.ValidationChecks;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    // Пользователи по умолчанию
    @Override
    public void init() throws ServletException {
        super.init();

        User user1 = new User("Nikita", "qwerty");
        User user2 = new User("Ivan", "qwerty");
        User user3 = new User("BlockedUser", "qwerty");
        User user4 = new User("ReadonlyUser", "qwerty");

        UserDao.instance.persist(user1);
        UserDao.instance.persist(user2);
        UserDao.instance.persist(user3);
        UserDao.instance.persist(user4);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebContext webContext = new WebContext(req, resp, getServletContext());
        TemplateEngine.renderPage("login", webContext, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("login");
        String password = req.getParameter("password");

        WebContext webContext = new WebContext(req, resp, getServletContext());

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
