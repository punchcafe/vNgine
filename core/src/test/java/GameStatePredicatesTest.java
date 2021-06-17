import dev.punchcafe.vngine.pom.narrative.Narrative;
import dev.punchcafe.vngine.pom.narrative.NarrativeReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

//TODO:
public class GameStatePredicatesTest extends PlayTest {

    private static NarrativeTest NODE_0_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_0_NARRATIVE").build();
    private static NarrativeTest NODE_1_0_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_1_0_NARRATIVE").build();
    private static NarrativeTest NODE_1_1_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_1_1_NARRATIVE").build();
    private static NarrativeTest NODE_1_2_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_1_2_NARRATIVE").build();
    private static NarrativeTest NODE_2_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_2_NARRATIVE").build();
    private static NarrativeTest NODE_3_0_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_3_0_NARRATIVE").build();
    private static NarrativeTest NODE_3_1_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_3_1_NARRATIVE").build();
    private static NarrativeTest NODE_3_2_NARRATIVE_CONTENTS = NarrativeTest.builder().narrativeText("NODE_3_2_NARRATIVE").build();

    private static Narrative<NarrativeTest> NODE_0_NARRATIVE = Narrative.of(NODE_0_NARRATIVE_CONTENTS);
    private static Narrative<NarrativeTest> NODE_1_0_NARRATIVE = Narrative.of(NODE_1_0_NARRATIVE_CONTENTS);
    private static Narrative<NarrativeTest> NODE_1_1_NARRATIVE = Narrative.of(NODE_1_1_NARRATIVE_CONTENTS);
    private static Narrative<NarrativeTest> NODE_1_2_NARRATIVE = Narrative.of(NODE_1_2_NARRATIVE_CONTENTS);
    private static Narrative<NarrativeTest> NODE_2_NARRATIVE = Narrative.of(NODE_2_NARRATIVE_CONTENTS);
    private static Narrative<NarrativeTest> NODE_3_0_NARRATIVE = Narrative.of(NODE_3_0_NARRATIVE_CONTENTS);
    private static Narrative<NarrativeTest> NODE_3_1_NARRATIVE = Narrative.of(NODE_3_1_NARRATIVE_CONTENTS);
    private static Narrative<NarrativeTest> NODE_3_2_NARRATIVE = Narrative.of(NODE_3_2_NARRATIVE_CONTENTS);

    /*
    TO TEST:
    simple case game state predicates work for INT, STRING, BOOL
    load new chapter, simple case game state predicates for INT, STRING, BOOL (game state choices are preserved)

     */
    @Override
    String gameConfigLocation() {
        return "src/test/resources/test-gs-predicates-game";
    }

    @BeforeAll
    void beforeAll() {
        when(narrativeReader.readNarrative(eq(NODE_0_NARRATIVE))).
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);
        this.buildGame();
    }

    @Test
    void canUpdateAndPredicateOnIntegerGameStateVariable() {
        game.startNewGame();
    }
}
