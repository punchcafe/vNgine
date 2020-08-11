package ascii.example;

import dev.punchcafe.vngine.*;
import dev.punchcafe.vngine.node.AutomaticSceneNode;
import dev.punchcafe.vngine.node.FinalSceneNode;
import dev.punchcafe.vngine.node.PlayerInputSceneNode;
import dev.punchcafe.vngine.node.SceneNode;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface AsciiSceneNodeConstants {

    String NODE_1_ID = "NODE_1";

    String NODE_1_A_ID = "NODE_1_A";
    String NODE_1_B_ID = "NODE_1_B";
    String NODE_SEMI_LAST_ID = "NODE_SEMI_LAST";
    String NODE_LAST_GOOD_ID = "NODE_LAST_GOOD";
    String NODE_LAST_BAD_ID = "NODE_LAST_BAD";

    Consumer<GameState> NODE_1A_SET_STATE_HAPPY = gameState -> {
        ((AsciiGameState) gameState).setHappy(true);
    };
    Consumer<GameState> NODE_1B_SET_STATE_UNHAPPY = gameState -> {
        ((AsciiGameState) gameState).setHappy(false);
    };

    Narrative<AsciiNarrativeElement> NODE_1_NARRATIVE_1 = new Narrative(List.of(
            new AsciiNarrativeElement("MC", "I didn't see you, I'm sorry"),
            new AsciiNarrativeElement("Kael", "It's quite all right, really"),
            new AsciiNarrativeElement("MC", "I'll be on my way then..."),
            new AsciiNarrativeElement("Kael", "Wait, can't I get your name?")
    ));

    Narrative<AsciiNarrativeElement> NODE_1_NARRATIVE_1_A = new Narrative(List.of(
            new AsciiNarrativeElement("MC", "Sure, my name is MC"),
            new AsciiNarrativeElement("Kael", "Nice to meet you, I'm Kael.")
    ));

    Narrative<AsciiNarrativeElement> NODE_1_NARRATIVE_1_B = new Narrative(List.of(
            new AsciiNarrativeElement("MC", "I'm sorry, I don't really know you..."),
            new AsciiNarrativeElement("Kael", "That's too bad.")
    ));

    Narrative<AsciiNarrativeElement> NODE_SEMI_LAST_NARRATIVE = new Narrative(List.of(
            new AsciiNarrativeElement("Internal", "*you think for a minute*"),
            new AsciiNarrativeElement("MC", "Actually, are you free?")
    ));

    Narrative<AsciiNarrativeElement> NODE_LAST_NARRATIVE_BAD_ENDING = new Narrative(List.of(
            new AsciiNarrativeElement("Kael", "Sorry, I should go.")
    ));

    Narrative<AsciiNarrativeElement> NODE_LAST_NARRATIVE_GOOD_ENDING = new Narrative(List.of(
            new AsciiNarrativeElement("Kael", "Sure!")
    ));

    String PLAYER_CHOICE_A = "tell him your name";
    String PLAYER_CHOICE_B = "refuse";
    List<String> PLAYER_CHOICES = List.of(PLAYER_CHOICE_A, PLAYER_CHOICE_B);

    Function<String, String> PLAYER_CHOICE_TO_ID = (choice) -> {
        if (PLAYER_CHOICE_A.equals(choice)) {
            return NODE_1_A_ID;
        } else if (PLAYER_CHOICE_B.equals(choice)) {
            return NODE_1_B_ID;
        } else return null;
    };

    SceneNode NODE_LAST_GOOD = new FinalSceneNode(NODE_LAST_GOOD_ID, NODE_LAST_NARRATIVE_GOOD_ENDING);

    SceneNode NODE_LAST_BAD = new FinalSceneNode(NODE_LAST_BAD_ID, NODE_LAST_NARRATIVE_BAD_ENDING);


    SceneNode NODE_SEMI_LAST = new AutomaticSceneNode(
            NODE_SEMI_LAST_ID,
            NODE_SEMI_LAST_NARRATIVE,
            (gamestate) -> ((AsciiGameState) gamestate).isHappy() ? NODE_LAST_GOOD_ID : NODE_LAST_BAD_ID,
            //TODO: replace with id map instead? think about loading though
            List.of(NODE_LAST_GOOD, NODE_LAST_BAD),
            null
    );

    SceneNode NODE_SET_HAPPY = new AutomaticSceneNode(
            NODE_1_A_ID,
            NODE_1_NARRATIVE_1_A,
            //TODO: replace with single node transfer, introduce pure functional nodes.
            gameState -> NODE_SEMI_LAST_ID,
            List.of(NODE_SEMI_LAST),
            NODE_1A_SET_STATE_HAPPY);

    SceneNode NODE_SET_UNHAPPY = new AutomaticSceneNode(
            NODE_1_B_ID,
            NODE_1_NARRATIVE_1_B,
            //TODO: replace with single node transfer, introduce pure functional nodes.
            gameState -> NODE_SEMI_LAST_ID,
            List.of(NODE_SEMI_LAST),
            NODE_1B_SET_STATE_UNHAPPY);

    SceneNode NODE_1 = new PlayerInputSceneNode(
            NODE_1_ID,
            NODE_1_NARRATIVE_1,
            PLAYER_CHOICE_TO_ID,
            List.of(NODE_SET_HAPPY, NODE_SET_UNHAPPY),
            PLAYER_CHOICES,
            null);


}
