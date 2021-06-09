package dev.punchcafe.vngine.node.predicate.integer;

import dev.punchcafe.vngine.node.gsm.StateLevel;
import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import dev.punchcafe.vngine.node.predicate.PredicateValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class IntegerVariableValue implements PredicateValue<Integer> {

    @NonNull
    private final String variableName;
    @NonNull
    private final StateLevel stateLevel;

    @Override
    public Integer getValue(final GameState gameState) {
        switch (stateLevel) {
            case GAME:
            default:
                return gameState.getIntegerProperty(variableName);
            case CHAPTER:
                return gameState.getChapterState().getIntegerProperty(variableName);
        }
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitIntegerVariableValue(this);
    }
}
