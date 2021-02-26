package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.old.OldGameState;

// To be built up by yaml expressions.
/*
TODO: unlike before where we have a predicate to string, instead with yaml we'll have a continous
sewt of predicates which are checked
 */
@FunctionalInterface
public interface GameStatePredicate {
    boolean evaluate(OldGameState gameState);

}
