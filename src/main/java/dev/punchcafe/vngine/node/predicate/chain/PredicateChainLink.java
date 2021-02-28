package dev.punchcafe.vngine.node.predicate.chain;

import dev.punchcafe.vngine.node.predicate.GameStatePredicate;

public class PredicateChainLink {

    public enum LogicalLink {
        OR, AND
    }

    GameStatePredicate predicate;
    LogicalLink link;

    public PredicateChainLink(final GameStatePredicate gameStatePredicate, final LogicalLink logicalLink) {
        this.predicate = gameStatePredicate;
        this.link = logicalLink;
    }
}