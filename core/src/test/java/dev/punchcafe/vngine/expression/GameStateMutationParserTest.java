package dev.punchcafe.vngine.expression;

import dev.punchcafe.vngine.node.gsm.ChangeIntegerProperty;
import dev.punchcafe.vngine.node.gsm.SetBooleanProperty;
import dev.punchcafe.vngine.node.gsm.SetStringProperty;
import dev.punchcafe.vngine.parse.GameStateModifierParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameStateMutationParserTest {

    @Test
    void evaluates_increaseByCommand() {
        final var testStringOne = "increase $int.happiness by 3";
        final var expectedResult = ChangeIntegerProperty.builder().propertyName("happiness").valueChange(3).build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_decreaseByCommand() {
        final var testStringOne = "decrease $int.happiness by 3";
        final var expectedResult = ChangeIntegerProperty.builder().propertyName("happiness").valueChange(-3).build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_setBoolCommand() {
        final var testStringOne = "set $bool.is-happy to true";
        final var expectedResult = SetBooleanProperty.builder().propertyName("is-happy").booleanValue(true).build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

    @Test
    void evaluates_setStringCommand() {
        final var testStringOne = "set $str.favorite-food to 'PIZZA'";
        final var expectedResult = SetStringProperty.builder()
                .propertyName("favorite-food")
                .propertyValue("PIZZA")
                .build();
        final var result = GameStateModifierParser.parse(testStringOne);
        assertEquals(expectedResult, result);
    }

}
