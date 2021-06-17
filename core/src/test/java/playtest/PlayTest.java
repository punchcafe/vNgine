package playtest;

import playtest.fixture.ObservingNarrative;
import playtest.fixture.ObservingNarrativeReader;
import playtest.fixture.ObservingNarrativeService;
import dev.punchcafe.vngine.game.Game;
import dev.punchcafe.vngine.player.PlayerObserver;
import dev.punchcafe.vngine.pom.NarrativeAdaptor;
import dev.punchcafe.vngine.pom.PomLoader;
import dev.punchcafe.vngine.pom.narrative.NarrativeReader;

import java.io.File;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public abstract class PlayTest {

    protected ObservingNarrativeService narrativeService;
    protected NarrativeReader<ObservingNarrative> narrativeReader;
    protected PlayerObserver playerObserver;
    protected Game<ObservingNarrative> game;

    void buildGame() {
        narrativeService = new ObservingNarrativeService(this.validNarrativeIds());
        narrativeReader = new ObservingNarrativeReader();
        playerObserver = mock(PlayerObserver.class);
        final dev.punchcafe.vngine.game.GameBuilder<ObservingNarrative> gameBuilder = new dev.punchcafe.vngine.game.GameBuilder();
        final NarrativeAdaptor<Object> narrativeReader = (file) -> List.of();
        final var pom = PomLoader.forGame(new File(this.gameConfigLocation()), narrativeReader).loadGameConfiguration();
        gameBuilder.setNarrativeReader(this.narrativeReader);
        gameBuilder.setNarrativeService(this.narrativeService);
        gameBuilder.setPlayerObserver(this.playerObserver);
        gameBuilder.setProjectObjectModel(pom);
        game = gameBuilder.build();
    }

    void resetObservers(){
        this.narrativeService.getObservingNarrative().reset();
    }

    abstract String gameConfigLocation();

    abstract Set<String> validNarrativeIds();

    // helpers

    void assertGameStateIntVarEquals(final String varName, final int expectedValue) {
        assertEquals(game.getGameState().getIntegerProperty(varName), expectedValue);
    }

    void assertGameStateStrVarEquals(final String varName, final String expectedValue) {
        assertEquals(game.getGameState().getStringProperty(varName), expectedValue);
    }

    void assertGameStateBooleanVarIsTrue(final String varName) {
        assertTrue(game.getGameState().getBooleanProperty(varName));
    }

    void assertGameStateBooleanVarIsFalse(final String varName) {
        assertFalse(game.getGameState().getBooleanProperty(varName));
    }

    void assertCurrentChapterIntVarEquals(final String varName, final int expectedValue) {
        assertEquals(game.getGameState().getChapterState().getIntegerProperty(varName), expectedValue);
    }

    void assertCurrentChapterStrVarEquals(final String varName, final String expectedValue) {
        assertEquals(game.getGameState().getChapterState().getStringProperty(varName), expectedValue);
    }

    void assertCurrentChapterBooleanVarIsTrue(final String varName) {
        assertTrue(game.getGameState().getChapterState().getBooleanProperty(varName));
    }

    void assertCurrentChapterBooleanVarIsFalse(final String varName) {
        assertTrue(game.getGameState().getChapterState().getBooleanProperty(varName));
    }
}
