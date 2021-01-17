package com.golovkin.chat.web.servlets;

import com.golovkin.chat.web.utils.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var webContext = new WebContext(req, resp, getServletContext());
        setupGetWebContext(webContext, req, resp, getServletContext());

        TemplateEngine.renderPage(getGetTemplateName(), webContext, resp);
    }

    protected abstract String getGetTemplateName();

    protected void setupGetWebContext(WebContext webContext, HttpServletRequest req, HttpServletResponse resp, ServletContext servletContext) throws IOException {
    }
}
