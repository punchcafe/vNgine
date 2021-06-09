package dev.punchcafe.vngine.node.predicate.bool;

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
public class BooleanVariableValue implements PredicateValue<Boolean> {

    @NonNull
    private final String variableName;
    @NonNull
    private final StateLevel stateLevel;

    @Override
    public Boolean getValue(final GameState gameState) {
        switch (stateLevel) {
            case GAME:
            default:
                return gameState.getBooleanProperty(variableName);
            case CHAPTER:
                return gameState.getChapterState().getBooleanProperty(variableName);
        }
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitBooleanVariableValue(this);
    }
}
