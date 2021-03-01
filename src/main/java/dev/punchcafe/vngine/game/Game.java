package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.parse.narrative.NarrativeParser;
import dev.punchcafe.vngine.player.SimplePlayerObserver;
import dev.punchcafe.vngine.narrative.NarrativeReader;
import dev.punchcafe.vngine.narrative.NarrativeService;
import dev.punchcafe.vngine.narrative.imp.NarrativeReaderImp;
import dev.punchcafe.vngine.narrative.imp.NarrativeServiceImp;
import dev.punchcafe.vngine.node.Node;
import lombok.AccessLevel;
import lombok.Builder;

import java.io.File;
import java.io.IOException;

@Builder(access = AccessLevel.PACKAGE)
public class Game {
    private final GameState gameState;
    private final Node firstNode;
    private final NarrativeService narrativeService;
    private final NarrativeReader narrativeReader;

    public void play(){
        Node nextNode = firstNode;
        while(nextNode != null){
            nextNode.getNodeGameStateChange().modify(gameState);
            final var narrative = narrativeService.getNarrative(nextNode.getNarrativeId());
            narrativeReader.readNarrative(narrative);
            nextNode = nextNode.getNextNode();
        }
    }

    public static void main(String[] args) throws IOException {
        // TODO: extract to adaptor
        final var gameBuilder = new dev.punchcafe.vngine.game.GameBuilder();
        final var narratives = NarrativeParser.parseNarrative(new File("src/main/resources/game-config/chapter_1_nar.yaml"));
        final var narrativeService = new NarrativeServiceImp(narratives);
        gameBuilder.setNarrativeReader(new NarrativeReaderImp());
        gameBuilder.setNarrativeService(narrativeService);
        gameBuilder.setPlayerObserver(new SimplePlayerObserver());
        gameBuilder.setNodeConfigurationFile(new File("src/main/resources/game-config/chapter_1.yaml"));
        gameBuilder.build().play();
    }
}
