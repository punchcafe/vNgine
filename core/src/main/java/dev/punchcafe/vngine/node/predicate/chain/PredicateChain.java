package dev.punchcafe.vngine.node.predicate.chain;

import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PredicateChain implements GameStatePredicate {

    private final List<PredicateChainLink> predicateChainLinks;


    @Override
    public boolean evaluate(StateContainer gameState) {
        var result = predicateChainLinks.get(0).getPredicate().evaluate(gameState);
        for (var link : predicateChainLinks.subList(1, predicateChainLinks.size())) {
            switch (link.getLink()) {
                case OR:
                    result = result || link.getPredicate().evaluate(gameState);
                    break;
                case AND:
                    result = result && link.getPredicate().evaluate(gameState);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return result;
    }

    @Override
    public <T> T acceptVisitor(GameStatePredicateVisitor<T> visitor) {
        return visitor.visitChainPredicate(this);
    }
}
