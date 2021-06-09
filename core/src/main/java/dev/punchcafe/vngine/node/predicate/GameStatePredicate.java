package dev.punchcafe.vngine.node.predicate;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;

public interface GameStatePredicate {
    boolean evaluate(GameState gameState);

    <T> T acceptVisitor(GameStatePredicateVisitor<T> visitor);
}
