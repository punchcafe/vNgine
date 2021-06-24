package dev.punchcafe.vngine.expression;

import dev.punchcafe.vngine.node.gsm.ChangeIntegerProperty;
import dev.punchcafe.vngine.node.gsm.SetBooleanProperty;
import dev.punchcafe.vngine.node.gsm.SetStringProperty;
import dev.punchcafe.vngine.config.GameStateModifierParser;
import dev.punchcafe.vngine.node.gsm.StateLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateMutationParserTest {

    @Test
    void evaluates_increaseByCommand_forGameState() {
        final var testStringOne = "increase $int.happiness by 3";
        final var expectedResult = ChangeIntegerProperty.builder()
                .propertyName("happiness")
                .valueChange(3)
                .stateLevel(StateLevel.GAME)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_decreaseByCommand_forGameState() {
        final var testStringOne = "decrease $int.happiness by 3";
        final var expectedResult = ChangeIntegerProperty.builder()
                .propertyName("happiness")
                .valueChange(-3)
                .stateLevel(StateLevel.GAME)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_setBoolCommand_forGameState() {
        final var testStringOne = "set $bool.is-happy to true";
        final var expectedResult = SetBooleanProperty.builder()
                .propertyName("is-happy")
                .booleanValue(true)
                .stateLevel(StateLevel.GAME)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_setStringCommand_forGameState() {
        final var testStringOne = "set $str.favorite-food to 'PIZZA'";
        final var expectedResult = SetStringProperty.builder()
                .propertyName("favorite-food")
                .propertyValue("PIZZA")
                .stateLevel(StateLevel.GAME)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }


    @Test
    void evaluates_increaseByCommand_forChapterState() {
        final var testStringOne = "increase @int.happiness by 3";
        final var expectedResult = ChangeIntegerProperty.builder()
                .propertyName("happiness")
                .valueChange(3)
                .stateLevel(StateLevel.CHAPTER)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_decreaseByCommand_forChapterState() {
        final var testStringOne = "decrease @int.happiness by 3";
        final var expectedResult = ChangeIntegerProperty.builder()
                .propertyName("happiness")
                .valueChange(-3)
                .stateLevel(StateLevel.CHAPTER)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_setBoolCommand_forChapterState() {
        final var testStringOne = "set @bool.is-happy to true";
        final var expectedResult = SetBooleanProperty.builder()
                .propertyName("is-happy")
                .booleanValue(true)
                .stateLevel(StateLevel.CHAPTER)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_setStringCommand_forChapterState() {
        final var testStringOne = "set @str.favorite-food to 'PIZZA'";
        final var expectedResult = SetStringProperty.builder()
                .propertyName("favorite-food")
                .propertyValue("PIZZA")
                .stateLevel(StateLevel.CHAPTER)
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

}
