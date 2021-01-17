package com.golovkin.chat.web.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static <T> T getSessionAttribute(String attributeName, Class<T> classTag, HttpServletRequest request) {
        return (T) request.getSession().getAttribute(attributeName);
    }

    public static int getParameterAsInt(String parameterName, HttpServletRequest request) {
        String parameter = request.getParameter(parameterName);

        return Integer.parseInt(parameter);
    }
}
