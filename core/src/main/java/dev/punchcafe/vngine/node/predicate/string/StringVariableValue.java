package dev.punchcafe.vngine.node.predicate.string;

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
public class StringVariableValue implements PredicateValue<String> {

    @NonNull
    private final String variableName;
    @NonNull
    private final StateLevel stateLevel;

    @Override
    public String getValue(final GameState gameState) {
        switch (this.stateLevel){
            case GAME:
            default:
                return gameState.getStringProperty(variableName);
            case CHAPTER:
                return gameState.getChapterState().getStringProperty(variableName);
        }
    }

    @Override
    public <R> R acceptVisitor(GameStatePredicateVisitor<R> visitor) {
        return visitor.visitStringVariableValue(this);
    }
}
