package dev.punchcafe.vngine.pom.model.vngsl;

import dev.punchcafe.vngine.pom.model.vngsl.variable.GameVariableLevel;

public class GameVariableLevelUtil {
    public static String getGameVariableLevelPrefix(final GameVariableLevel level){
        return level == GameVariableLevel.CHAPTER ? "@" : "$";
    }
}
