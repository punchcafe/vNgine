package dev.punchcafe.vngine.node.predicate;

import dev.punchcafe.vngine.game.GameState;

public interface GameStatePredicate {
    boolean evaluate(GameState gameState);

    <T> T acceptVisitor(GameStatePredicateVisitor<T> visitor);
}
