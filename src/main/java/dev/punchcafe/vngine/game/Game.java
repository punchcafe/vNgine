package dev.punchcafe.vngine.game;

import ascii.example.AsciiPlayerObserver;
import dev.punchcafe.vngine.narrative.NarrativeReader;
import dev.punchcafe.vngine.narrative.NarrativeService;
import dev.punchcafe.vngine.narrative.imp.NarrativeReaderImp;
import dev.punchcafe.vngine.narrative.imp.NarrativeServiceImp;
import dev.punchcafe.vngine.node.Node;
import lombok.AccessLevel;
import lombok.Builder;

import java.io.File;

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

    public static void main(String[] args) {
        final var gameBuilder = new dev.punchcafe.vngine.game.GameBuilder();
        gameBuilder.setNarrativeReader(new NarrativeReaderImp());
        gameBuilder.setNarrativeService(new NarrativeServiceImp());
        gameBuilder.setPlayerObserver(new AsciiPlayerObserver());
        gameBuilder.setNodeConfigurationFile(new File("src/main/resources/game-config/chapter_1.yaml"));
        gameBuilder.build().play();
    }
}
