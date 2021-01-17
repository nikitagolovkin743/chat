package com.golovkin.chat.web.servlets;

import com.golovkin.chat.web.utils.RequestUtils;
import com.golovkin.chat.web.utils.ValidationChecks;
import com.golovkin.chat.data.dao.MessageDao;
import com.golovkin.chat.data.dao.UserDao;
import com.golovkin.chat.data.entities.Message;
import com.golovkin.chat.data.entities.User;
import com.golovkin.chat.web.utils.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class DialogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var webContext = new WebContext(req, resp, getServletContext());

        var sender = RequestUtils.getSessionAttribute("currentUser", User.class, req);

        var receiverId = RequestUtils.getParameterAsInt("id", req);
        var receiver = UserDao.instance.findById(receiverId);
        if (receiver == null) {
            webContext.setVariable("errorMessage", "Собеседник с таким ID не найден. :(");
            TemplateEngine.renderPage("error", webContext, resp);
            return;
        }

        var messages = MessageDao.instance.findMessagesBetweenUsers(sender, receiver);

        webContext.setVariable("sender", sender);
        webContext.setVariable("receiver", receiver);
        webContext.setVariable("messages", messages);
        webContext.setVariable("readonly", req.getAttribute("readonly"));

        TemplateEngine.renderPage("dialog", webContext, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var sender = RequestUtils.getSessionAttribute("currentUser", User.class, req);
        var isSenderReadonly = (boolean) req.getAttribute("readonly");

        var receiverId = RequestUtils.getParameterAsInt("receiverId", req);

        if (!isSenderReadonly) {
            var receiver = UserDao.instance.findById(receiverId);
            if (receiver == null) {
                var webContext = new WebContext(req, resp, getServletContext());
                webContext.setVariable("errorMessage", "Собеседник с таким ID не найден. :(");
                TemplateEngine.renderPage("error", webContext, resp);
                return;
            }

            var messageText = req.getParameter("message");
            if (ValidationChecks.isValidMessageText(messageText)) {
                Message message = new Message(sender, receiver, messageText.trim(), Timestamp.from(Instant.now()));
                MessageDao.instance.persist(message);
            }
        }

        resp.sendRedirect("/dialog?id=" + receiverId);
    }
}
