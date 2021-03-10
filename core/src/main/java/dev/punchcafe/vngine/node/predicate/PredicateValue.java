package dev.punchcafe.vngine.node.predicate;

import dev.punchcafe.vngine.game.GameState;

public interface PredicateValue<T> {

    T getValue(final GameState gameState);

    <R> R acceptVisitor(final GameStatePredicateVisitor<R> visitor);
}
