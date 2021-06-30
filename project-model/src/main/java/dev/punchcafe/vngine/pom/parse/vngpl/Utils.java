package dev.punchcafe.vngine.pom.parse.vngpl;

import java.util.List;

public class Utils {
    public static boolean messageStartsWithAnyOf(final String message, final List<String> prefixes){
        return prefixes.stream().anyMatch(message::startsWith);
    }
}
