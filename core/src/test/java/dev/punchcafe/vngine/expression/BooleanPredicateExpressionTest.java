package dev.punchcafe.vngine.expression;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.config.GameStatePredicateParser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanPredicateExpressionTest {

    @Test
    void evaluates_simpleExpression_is_withValues(){
        final var testStringOne = "true is true";
        final var unitOne = GameStatePredicateParser.parseBooleanPredicate(testStringOne);
        assertTrue(unitOne.evaluate(null));

        final var testStringTwo = "true is false";
        final var unitTwo = GameStatePredicateParser.parseBooleanPredicate(testStringTwo);
        assertFalse(unitTwo.evaluate(null));

        final var testStringThree = "false is false";
        final var unitThree = GameStatePredicateParser.parseBooleanPredicate(testStringThree);
        assertTrue(unitThree.evaluate(null));

        final var testStringFour = "false is true";
        final var unitFour = GameStatePredicateParser.parseBooleanPredicate(testStringFour);
        assertFalse(unitFour.evaluate(null));
    }

    @Test
    void evaluates_simpleExpression_isnt_withValues(){
        final var testStringOne = "true isnt true";
        final var unitOne = GameStatePredicateParser.parseBooleanPredicate(testStringOne);
        assertFalse(unitOne.evaluate(null));

        final var testStringTwo = "true isn't true";
        final var unitTwo = GameStatePredicateParser.parseBooleanPredicate(testStringTwo);
        assertFalse(unitTwo.evaluate(null));

        final var testStringThree = "true isnt false";
        final var unitThree = GameStatePredicateParser.parseBooleanPredicate(testStringThree);
        assertTrue(unitThree.evaluate(null));

        final var testStringFour = "true isn't false";
        final var unitFour = GameStatePredicateParser.parseBooleanPredicate(testStringFour);
        assertTrue(unitFour.evaluate(null));

        final var testStringFive = "false isnt false";
        final var unitFive = GameStatePredicateParser.parseBooleanPredicate(testStringFive);
        assertFalse(unitFive.evaluate(null));

        final var testStringSix = "false isn't false";
        final var unitSix = GameStatePredicateParser.parseBooleanPredicate(testStringSix);
        assertFalse(unitSix.evaluate(null));

        final var testStringSeven = "false isnt true";
        final var unitSeven = GameStatePredicateParser.parseBooleanPredicate(testStringSeven);
        assertTrue(unitSeven.evaluate(null));

        final var testStringEight = "false isn't true";
        final var unitEight = GameStatePredicateParser.parseBooleanPredicate(testStringEight);
        assertTrue(unitEight.evaluate(null));
    }

    @Test
    void evaluates_complexExpression_is_withValues(){
        final var propertyName = "PROPERTY";
        final var gameState = new GameState(List.of(), List.of(propertyName), List.of());
        gameState.setBooleanProperty(propertyName, true);

        final var testStringOne = "$bool.property is true";
        final var unitOne = GameStatePredicateParser.parseBooleanPredicate(testStringOne);
        assertTrue(unitOne.evaluate(gameState));

        final var testStringTwo = "$bool.property is false";
        final var unitTwo = GameStatePredicateParser.parseBooleanPredicate(testStringTwo);
        assertFalse(unitTwo.evaluate(gameState));

        final var testStringFour = "false is $bool.property";
        final var unitFour = GameStatePredicateParser.parseBooleanPredicate(testStringFour);
        assertFalse(unitFour.evaluate(gameState));
    }

    @Test
    void evaluates_complexExpression_isnt_withValues(){
        final var propertyName = "PROPERTY";
        final var gameState = new GameState(List.of(), List.of(propertyName), List.of());
        gameState.setBooleanProperty(propertyName, true);

        final var testStringOne = "$bool.property isnt true";
        final var unitOne = GameStatePredicateParser.parseBooleanPredicate(testStringOne);
        assertFalse(unitOne.evaluate(gameState));

        final var testStringTwo = "$bool.property isn't true";
        final var unitTwo = GameStatePredicateParser.parseBooleanPredicate(testStringTwo);
        assertFalse(unitTwo.evaluate(gameState));

        final var testStringThree = "$bool.property isnt false";
        final var unitThree = GameStatePredicateParser.parseBooleanPredicate(testStringThree);
        assertTrue(unitThree.evaluate(gameState));

        final var testStringFour = "$bool.property isn't false";
        final var unitFour = GameStatePredicateParser.parseBooleanPredicate(testStringFour);
        assertTrue(unitFour.evaluate(gameState));

        final var testStringSeven = "false isnt $bool.property";
        final var unitSeven = GameStatePredicateParser.parseBooleanPredicate(testStringSeven);
        assertTrue(unitSeven.evaluate(gameState));

        final var testStringEight = "false isn't $bool.property";
        final var unitEight = GameStatePredicateParser.parseBooleanPredicate(testStringEight);
        assertTrue(unitEight.evaluate(gameState));
    }

}
