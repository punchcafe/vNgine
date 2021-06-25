package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.node.predicate.GameStatePredicate;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class StateDeterminedNextNodeStrategy implements NextNodeStrategy {

    @Builder
    @Getter
    public static class Branch {
        private final GameStatePredicate predicate;
        private final Node node;
    }

    private final GameState gameState;
    private final List<Branch> branches;

    @Override
    public Node getNextNode() {
        return branches.stream()
                .filter(this::branchEvaluatesToTrue)
                .findFirst()
                .map(Branch::getNode)
                //TODO: implement default node
                .orElse(null);
    }

    private boolean branchEvaluatesToTrue(final Branch branch){
        return branch.getPredicate().evaluate(this.gameState);
    }
}
