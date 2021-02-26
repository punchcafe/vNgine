package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.PlayerObserver;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Builder
public class PlayerDeterminedNextNodeStrategy implements NextNodeStrategy {

    @Builder
    @Getter
    public static class Branch {
        String prompt;
        // TODO: consider if this is easier with a nodeId Repository concept.
        // Or something injected by a node factory
        Node node;
    }

    private final PlayerObserver playerObserver;
    private final List<Branch> branches;

    @Override
    public Node getNextNode() {
        final var promptToBranch = branches.stream().collect(toMap(Branch::getPrompt, identity()));
        final var response = playerObserver.getFromChoice(branches.stream()
                .map(Branch::getPrompt)
                .collect(Collectors.toList()));
        return promptToBranch.get(response).getNode();
    }

}
