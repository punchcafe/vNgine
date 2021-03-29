package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.narrative.NarrativeReader;
import dev.punchcafe.vngine.narrative.NarrativeService;
import dev.punchcafe.vngine.node.Node;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.Optional;

@Builder(access = AccessLevel.PACKAGE)
public class Game<N> {
    private final GameState globalGameState;
    private final Node firstNode;
    private final NarrativeService<N> narrativeService;
    private final NarrativeReader<N> narrativeReader;
    private Node currentNode;

    public void play(){
        currentNode = firstNode;
        while(currentNode != null){
            currentNode.getNodeGameStateChange().modify(globalGameState);
            final var narrative = narrativeService.getNarrative(currentNode.getNarrativeId());
            narrativeReader.readNarrative(narrative);
            currentNode = currentNode.getNextNode();
        }
    }

    public GameSave saveGame(){
        return GameSave.builder()
                .nodeId(Optional.ofNullable(currentNode.getId()).orElse(null))
                .gameState(globalGameState.takeSnapshot())
                .build();
    };

    public void load(final GameSave saveGame){
        this.globalGameState
    };
}
