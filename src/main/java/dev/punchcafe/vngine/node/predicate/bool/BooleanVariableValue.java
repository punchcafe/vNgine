package dev.punchcafe.vngine.node.predicate.bool;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BooleanVariableValue implements PredicateValue<Boolean> {

    private String variableName;


    @Override
    public Boolean getValue(final GameState gameState) {
        return gameState.getBooleanProperty(variableName);
    }
}
