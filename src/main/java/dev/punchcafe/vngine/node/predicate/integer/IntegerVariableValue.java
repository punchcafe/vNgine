package dev.punchcafe.vngine.node.predicate.integer;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerVariableValue implements PredicateValue<Integer> {

    private String variableName;


    @Override
    public Integer getValue(final GameState gameState) {
        return gameState.getIntegerProperty(variableName);
    }
}
