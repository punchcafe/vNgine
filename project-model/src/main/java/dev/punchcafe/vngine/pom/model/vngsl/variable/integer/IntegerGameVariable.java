package dev.punchcafe.vngine.pom.model.vngsl.variable.integer;

import dev.punchcafe.vngine.pom.model.vngsl.GameVariableLevelUtil;
import dev.punchcafe.vngine.pom.model.vngsl.variable.GameVariableLevel;
import lombok.Builder;

@Builder
public class IntegerGameVariable implements IntegerVariable {

    private static final String INT_VARIABLE_PREFIX = "int";

    private final GameVariableLevel gameVariableLevel;
    private final String variableName;

    @Override
    public String asVngQL() {
        return GameVariableLevelUtil.getGameVariableLevelPrefix(this.gameVariableLevel)
                + INT_VARIABLE_PREFIX
                + this.variableName;
    }
}
