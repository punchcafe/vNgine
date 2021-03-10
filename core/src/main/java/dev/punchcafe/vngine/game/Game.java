package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.narrative.NarrativeReader;
import dev.punchcafe.vngine.narrative.NarrativeService;
import dev.punchcafe.vngine.node.Node;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
public class Game<N> {
    private final GameState gameState;
    private final Node firstNode;
    private final NarrativeService<N> narrativeService;
    private final NarrativeReader<N> narrativeReader;

    public void play(){
        Node nextNode = firstNode;
        while(nextNode != null){
            nextNode.getNodeGameStateChange().modify(gameState);
            final var narrative = narrativeService.getNarrative(nextNode.getNarrativeId());
            narrativeReader.readNarrative(narrative);
            nextNode = nextNode.getNextNode();
        }
    }
}
