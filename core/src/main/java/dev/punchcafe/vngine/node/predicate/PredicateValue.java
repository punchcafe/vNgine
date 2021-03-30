package dev.punchcafe.vngine.node.predicate;

import dev.punchcafe.vngine.state.StateContainer;

public interface PredicateValue<T> {

    T getValue(final StateContainer gameState);

    <R> R acceptVisitor(final GameStatePredicateVisitor<R> visitor);
}
