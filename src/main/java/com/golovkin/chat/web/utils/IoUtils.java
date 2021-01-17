package com.golovkin.chat.web.utils;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class IoUtils {
    public static Set<String> getSetWithLinesFrom(InputStream stream) {
        var scanner = new Scanner(stream);

        var lines = new HashSet<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }

        return lines;
    }
}
