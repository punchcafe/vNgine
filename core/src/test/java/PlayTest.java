import dev.punchcafe.vngine.game.Game;
import dev.punchcafe.vngine.player.PlayerObserver;
import dev.punchcafe.vngine.pom.NarrativeAdaptor;
import dev.punchcafe.vngine.pom.PomLoader;
import dev.punchcafe.vngine.pom.narrative.Narrative;
import dev.punchcafe.vngine.pom.narrative.NarrativeReader;
import dev.punchcafe.vngine.pom.narrative.NarrativeService;
import dev.punchcafe.vngine.save.GameSave;
import dev.punchcafe.vngine.save.NodeIdentifier;
import dev.punchcafe.vngine.save.SavedGameState;
import dev.punchcafe.vngine.save.StateSnapshot;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public abstract class PlayTest {

    protected NarrativeService<NarrativeTest> mockNarrativeService;
    protected NarrativeReader<NarrativeTest> narrativeReader;
    protected PlayerObserver playerObserver;
    protected Game<NarrativeTest> game;

    void buildGame() {
        mockNarrativeService = mock(NarrativeService.class);
        narrativeReader = mock(NarrativeReader.class);
        playerObserver = mock(PlayerObserver.class);
        final dev.punchcafe.vngine.game.GameBuilder<NarrativeTest> gameBuilder = new dev.punchcafe.vngine.game.GameBuilder();
        final NarrativeAdaptor<Object> narrativeReader = (file) -> List.of();
        final var pom = PomLoader.forGame(new File(this.gameConfigLocation()), narrativeReader).loadGameConfiguration();
        gameBuilder.setNarrativeReader(this.narrativeReader);
        gameBuilder.setNarrativeService(this.mockNarrativeService);
        gameBuilder.setPlayerObserver(this.playerObserver);
        gameBuilder.setProjectObjectModel(pom);
        game = gameBuilder.build();
    }

    abstract String gameConfigLocation();

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
