package dev.punchcafe.vngine.pom.model.vngpl.variable.bool;

import dev.punchcafe.vngine.pom.model.vngpl.GameVariableLevelUtil;
import dev.punchcafe.vngine.pom.model.vngpl.variable.GameVariableLevel;
import lombok.Builder;

@Builder
public class BoolGameVariable implements BooleanVariable {

    private static final String BOOL_VARIABLE_PREFIX = "bool";

    private final GameVariableLevel gameVariableLevel;
    private final String variableName;

    @Override
    public String asVngQL() {
        return GameVariableLevelUtil.getGameVariableLevelPrefix(this.gameVariableLevel)
                + BOOL_VARIABLE_PREFIX
                + this.variableName;
    }
}
