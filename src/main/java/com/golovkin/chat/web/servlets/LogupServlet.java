package com.golovkin.chat.web.servlets;

import com.golovkin.chat.web.utils.ValidationChecks;
import com.golovkin.chat.data.dao.UserDao;
import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.web.utils.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var webContext = new WebContext(req, resp, getServletContext());
        TemplateEngine.renderPage("logup", webContext, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var username = req.getParameter("login");
        var password = req.getParameter("password");
        var repeatedPassword = req.getParameter("repeated-password");

        var webContext = new WebContext(req, resp, getServletContext());

        boolean isUsernameLegit = processUsername(webContext, username);
        boolean isPasswordLegit = processPassword(webContext, password, repeatedPassword);

        if (isUsernameLegit && isPasswordLegit) {
            var user = new User(username, password);
            UserDao.instance.persist(user);

            webContext.setVariable("success", true);
        }

        TemplateEngine.renderPage("logup", webContext, resp);
    }

    private boolean processPassword(WebContext webContext, String password, String repeatedPassword) {
        boolean arePasswordsMatch = false;

        if (ValidationChecks.isPasswordValid(password) && repeatedPassword != null) {
            arePasswordsMatch = password.equals(repeatedPassword);
            webContext.setVariable("noMatch", !arePasswordsMatch);
        } else {
            webContext.setVariable("invalidPassword", true);
        }

        return arePasswordsMatch;
    }

    private boolean processUsername(WebContext webContext, String username) {
        boolean isUsernameValid = ValidationChecks.isUsernameValid(username);
        boolean doesUserExist = false;

        if (isUsernameValid) {
            doesUserExist = doesUsernameExist(username);
            webContext.setVariable("userExists", doesUserExist);
        } else {
            webContext.setVariable("invalidUsername", true);
        }

        return isUsernameValid && !doesUserExist;
    }

    private boolean doesUsernameExist(String username) {
        var user = UserDao.instance.findByName(username);

        return user != null;
    }
}
