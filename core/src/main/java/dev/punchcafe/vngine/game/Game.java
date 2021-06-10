package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.pom.narrative.NarrativeReader;
import dev.punchcafe.vngine.pom.narrative.NarrativeService;
import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.state.GameState;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
public class Game<N> {
    private final GameState gameState;
    private final Node firstNode;
    private final NarrativeService<N> narrativeService;
    private final NarrativeReader<N> narrativeReader;
    private Node currentNode;

    public void play(){
        currentNode = firstNode;
        while(currentNode != null){
            currentNode.getNodeGameStateChange().modify(gameState);
            if(currentNode.getNarrativeId() != null && !currentNode.getNarrativeId().trim().equals("")){
                final var narrative = narrativeService.getNarrative(currentNode.getNarrativeId());
                narrativeReader.readNarrative(narrative);
            }
            currentNode = currentNode.getNextNode();
        }
    }
}
