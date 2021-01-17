package com.golovkin.chat.web.utils;

import java.util.regex.Pattern;

public class ValidationChecks {
    private static final Pattern LEGIT_PASSWORD_PATTERN = Pattern.compile("[A-z|0-9]+");
    private static final Pattern LEGIT_USERNAME_PATTERN = Pattern.compile("[A-z]+");

    public static boolean isUsernameValid(String username) {
        if (username != null) {
            return LEGIT_USERNAME_PATTERN.matcher(username).matches();
        } else {
            return false;
        }
    }

    public static boolean isPasswordValid(String password) {
        if (password != null) {
            return LEGIT_PASSWORD_PATTERN.matcher(password).matches();
        } else {
            return false;
        }
    }

    public static boolean isValidMessageText(String message) {
        return message != null && !isEmptyMessage(message);
    }

    private static boolean isEmptyMessage(String message) {
        return message.trim().equals("");
    }
}
