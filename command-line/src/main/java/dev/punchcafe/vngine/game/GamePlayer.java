package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.narrative.imp.NarrativeImp;
import dev.punchcafe.vngine.narrative.imp.NarrativeReaderImp;
import dev.punchcafe.vngine.narrative.imp.NarrativeServiceImp;
import dev.punchcafe.vngine.parse.narrative.NarrativeParser;
import dev.punchcafe.vngine.player.SimplePlayerObserver;

import java.io.File;
import java.io.IOException;

public class GamePlayer {

    public static void main(String[] args) throws IOException {
        // TODO: extract to adaptor
        final dev.punchcafe.vngine.game.GameBuilder<NarrativeImp> gameBuilder = new dev.punchcafe.vngine.game.GameBuilder();
        final var narratives = NarrativeParser.parseNarrative(new File("command-line/src/main/resources/game-config/chapter_1_nar.yaml"));
        final var narrativeService = new NarrativeServiceImp(narratives);
        gameBuilder.setNarrativeReader(new NarrativeReaderImp());
        gameBuilder.setNarrativeService(narrativeService);
        gameBuilder.setPlayerObserver(new SimplePlayerObserver());
        gameBuilder.setNodeConfigurationFile(new File("command-line/src/main/resources/game-config/game_config.yaml"));
        gameBuilder.build().play();
    }
}
