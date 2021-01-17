package com.golovkin.chat.web.utils;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TemplateEngine {
    private final static org.thymeleaf.TemplateEngine templateEngine;

    static {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(TemplateEngine.class.getClassLoader());
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);

        templateEngine = new org.thymeleaf.TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public static void renderPage(String templateName, WebContext webContext, HttpServletResponse resp) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            templateEngine.process(templateName, webContext, writer);
        }
    }
}
