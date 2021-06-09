package dev.punchcafe.vngine.parse;

import dev.punchcafe.vngine.node.gsm.StateLevel;

public class GameStateParseUtils {

    public static StateLevel parseStateLevelFromVarPrefix(final String prefix) {
        switch (prefix) {
            case "$":
                return StateLevel.GAME;
            case "@":
                return StateLevel.CHAPTER;
            default:
                throw new IllegalArgumentException(String.format("Unsupported variable prefix: %s", prefix));
        }
    }
}
