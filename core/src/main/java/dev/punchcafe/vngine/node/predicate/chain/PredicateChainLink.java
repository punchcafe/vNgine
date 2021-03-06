package dev.punchcafe.vngine.node.predicate.chain;

import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import lombok.Getter;

@Getter
public class PredicateChainLink {

    public enum LogicalLink {
        OR, AND
    }

    private final GameStatePredicate predicate;
    private final LogicalLink link;

    public PredicateChainLink(final GameStatePredicate gameStatePredicate, final LogicalLink logicalLink) {
        this.predicate = gameStatePredicate;
        this.link = logicalLink;
    }
}