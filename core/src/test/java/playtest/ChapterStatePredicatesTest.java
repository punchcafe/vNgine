package playtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChapterStatePredicatesTest extends PlayTest {

    private static String NODE_0_NARRATIVE_ID = "0";
    private static String NODE_1_0_NARRATIVE_ID = "1-0";
    private static String NODE_1_1_NARRATIVE_ID = "1-1";
    private static String NODE_1_2_NARRATIVE_ID = "1-2";
    private static String NODE_2_NARRATIVE_ID = "2";
    private static String NODE_3_0_NARRATIVE_ID = "3-0";
    private static String NODE_3_1_NARRATIVE_ID = "3-1";
    private static String NODE_3_2_NARRATIVE_ID = "3-2";


    private static Set<String> LEGAL_NODE_IDS = Set.of(
            NODE_0_NARRATIVE_ID,
            NODE_1_0_NARRATIVE_ID,
            NODE_1_1_NARRATIVE_ID,
            NODE_1_2_NARRATIVE_ID,
            NODE_2_NARRATIVE_ID,
            NODE_3_0_NARRATIVE_ID,
            NODE_3_1_NARRATIVE_ID,
            NODE_3_2_NARRATIVE_ID);

    @Override
    String gameConfigLocation() {
        return "src/test/resources/test-chapter-predicates-game";
    }

    @Override
    Set<String> validNarrativeIds() {
        return LEGAL_NODE_IDS;
    }

    @BeforeEach
    void beforeEach(){
        this.buildGame();
    }

    @Test
    void canUpdateAndPredicateOnIntegerGameStateVariable() {
        game.startNewGame();
        // Build chapter and get first node
        game.tick();
        // Trigger node 0, choose increment $int.gs-int-var-1 by 1
        when(playerObserver.getFromChoice(eq(List.of("1-0", "1-1", "1-2")))).thenReturn("1-0");
        game.tick();
        // Trigger 1-0 -> 2
        game.tick();
        // Trigger 2 -> 3-0 (this is the expected route due to the predicate)
        game.tick();
        // Trigger through 3-0 to the end of the game;
        game.tick();
        // Assert all expected nodes have been visited by asserting on the observed narratives
        Assertions.assertEquals(this.narrativeService.getObservingNarrative().getAllReadNarratives(),
                List.of("0", "1-0", "2", "3-0"));
    }

    @Test
    void canUpdateAndPredicateOnStringGameStateVariable() {
        game.startNewGame();
        // Build chapter and get first node
        game.tick();
        // Trigger node 0, choose increment $int.gs-int-var-1 by 1
        when(playerObserver.getFromChoice(eq(List.of("1-0", "1-1", "1-2")))).thenReturn("1-1");
        game.tick();
        // Trigger 1-1 -> 2
        game.tick();
        // Trigger 2 -> 3-1 (this is the expected route due to the predicate)
        game.tick();
        // Trigger through 3-1 to the end of the game;
        game.tick();
        // Assert all expected nodes have been visited by asserting on the observed narratives
        Assertions.assertEquals(this.narrativeService.getObservingNarrative().getAllReadNarratives(),
                List.of("0", "1-1", "2", "3-1"));
    }

    @Test
    void canUpdateAndPredicateOnBooleanGameStateVariable() {
        game.startNewGame();
        // Build chapter and get first node
        game.tick();
        // Trigger node 0, choose increment $int.gs-int-var-1 by 1
        when(playerObserver.getFromChoice(eq(List.of("1-0", "1-1", "1-2")))).thenReturn("1-2");
        game.tick();
        // Trigger 1-2 -> 2
        game.tick();
        // Trigger 2 -> 3-2 (this is the expected route due to the predicate)
        game.tick();
        // Trigger through 3-2 to the end of the game;
        game.tick();
        // Assert all expected nodes have been visited by asserting on the observed narratives
        Assertions.assertEquals(this.narrativeService.getObservingNarrative().getAllReadNarratives(),
                List.of("0", "1-2", "2", "3-2"));
    }
}
