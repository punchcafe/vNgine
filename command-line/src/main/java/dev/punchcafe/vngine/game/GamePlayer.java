package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.config.narrative.NarrativeParser;
import dev.punchcafe.vngine.save.GameSave;
import dev.punchcafe.vngine.save.NodeIdentifier;
import dev.punchcafe.vngine.save.SavedGameState;
import dev.punchcafe.vngine.save.StateSnapshot;
import dev.punchcafe.vngine.player.SimplePlayerObserver;
import dev.punchcafe.vngine.pom.NarrativeAdaptor;
import dev.punchcafe.vngine.pom.PomLoader;
import dev.punchcafe.vngine.pom.narrative.imp.NarrativeImp;
import dev.punchcafe.vngine.pom.narrative.imp.NarrativeReaderImp;
import dev.punchcafe.vngine.pom.narrative.imp.NarrativeServiceImp;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GamePlayer {

    public static void main(String[] args) throws IOException {
        // TODO: extract to adaptor
        final dev.punchcafe.vngine.game.GameBuilder<NarrativeImp> gameBuilder = new dev.punchcafe.vngine.game.GameBuilder();
        final NarrativeAdaptor<Object> narrativeReader = (file) -> List.of();
        final var pom = PomLoader.forGame(new File("command-line/src/main/resources/vng-game-1"), narrativeReader).loadGameConfiguration();
        final var narratives = NarrativeParser.parseNarrative(new File("command-line/src/main/resources/vng-game-1/narratives/narratives.yml"));
        final var narrativeService = new NarrativeServiceImp(narratives);
        gameBuilder.setNarrativeReader(new NarrativeReaderImp());
        gameBuilder.setNarrativeService(narrativeService);
        gameBuilder.setPlayerObserver(new SimplePlayerObserver());
        gameBuilder.setProjectObjectModel(pom);
        final var game = gameBuilder.build();
        //game.startNewGame();
        final var saveFile = GameSave.builder()
                .nodeIdentifier(NodeIdentifier.builder()
                        .chapterId("ch_01")
                        .nodeId("1_2_2")
                        .build())
                .savedGameState(SavedGameState.builder()
                        .chapterStateSnapshot(StateSnapshot.builder()
                                .booleanPropertyMap(Map.of())
                                .integerPropertyMap(Map.of())
                                .stringPropertyMap(Map.of())
                                .build())
                        .gameStateSnapshot(StateSnapshot.builder()
                                .booleanPropertyMap(Map.of())
                                .integerPropertyMap(Map.of())
                                .stringPropertyMap(Map.of())
                                .build())
                        .build())
                .build();
        final var saveGame = game.loadGame(saveFile)
                .tick()
                .tick()
                .saveGame();
        System.out.println("checkpoint");
    }
}
