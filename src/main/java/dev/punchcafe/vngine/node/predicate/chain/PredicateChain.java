package dev.punchcafe.vngine.node.predicate.chain;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PredicateChain implements GameStatePredicate {

    private List<PredicateChainLink> predicateChainLinks;


    @Override
    public boolean evaluate(GameState gameState) {
        var result = predicateChainLinks.get(0).predicate.evaluate(gameState);
        for (var link : predicateChainLinks.subList(1, predicateChainLinks.size())) {
            switch (link.link) {
                case OR:
                    result = result || link.predicate.evaluate(gameState);
                    break;
                case AND:
                    result = result && link.predicate.evaluate(gameState);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return result;
    }
}
