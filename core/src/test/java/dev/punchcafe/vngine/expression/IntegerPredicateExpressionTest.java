package dev.punchcafe.vngine.expression;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import dev.punchcafe.vngine.config.GameStatePredicateParser;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerPredicateExpressionTest {

    @Test
    void evaluates_simpleExpression_lessThan_withValues(){
        final var testStringOne = "1 less_than 2";
        final var unitOne = GameStatePredicateParser.parseIntegerPredicate(testStringOne);
        assertTrue(unitOne.evaluate(null));

        final var testStringTwo = "2 less_than 2";
        final var unitTwo = GameStatePredicateParser.parseIntegerPredicate(testStringTwo);
        assertFalse(unitTwo.evaluate(null));

        final var testStringThree = "3 less_than 2";
        final var unitThree = GameStatePredicateParser.parseIntegerPredicate(testStringThree);
        assertFalse(unitThree.evaluate(null));
    }

    @Test
    void evaluates_simpleExpression_moreThan_withValues(){
        final var testStringOne = "1 more_than 2";
        final var unitOne = GameStatePredicateParser.parseIntegerPredicate(testStringOne);
        assertFalse(unitOne.evaluate(null));

        final var testStringTwo = "2 more_than 2";
        final var unitTwo = GameStatePredicateParser.parseIntegerPredicate(testStringTwo);
        assertFalse(unitTwo.evaluate(null));

        final var testStringThree = "3 more_than 2";
        final var unitThree = GameStatePredicateParser.parseIntegerPredicate(testStringThree);
        assertTrue(unitThree.evaluate(null));
    }

    @Test
    void evaluates_simpleExpression_equals_withValues(){
        final var testStringOne = "1 equals 2";
        final var unitOne = GameStatePredicateParser.parseIntegerPredicate(testStringOne);
        assertFalse(unitOne.evaluate(null));

        final var testStringTwo = "2 equals 2";
        final var unitTwo = GameStatePredicateParser.parseIntegerPredicate(testStringTwo);
        assertTrue(unitTwo.evaluate(null));

        final var testStringThree = "3 equals 2";
        final var unitThree = GameStatePredicateParser.parseIntegerPredicate(testStringThree);
        assertFalse(unitThree.evaluate(null));
    }

    @Test
    void evaluates_complexExpression_equals_withValues(){
        final var propertyName = "PROPERTY";
        final var gameState = new GameState(List.of(propertyName), List.of(), List.of());
        gameState.changeIntegerPropertyBy(propertyName, 5);

        final var testStringOne = "$int.property equals 2";
        final var unitOne = GameStatePredicateParser.parseIntegerPredicate(testStringOne);
        assertFalse(unitOne.evaluate(gameState));

        final var testStringTwo = "$int.property equals 5";
        final var unitTwo = GameStatePredicateParser.parseIntegerPredicate(testStringTwo);
        assertTrue(unitTwo.evaluate(gameState));

        final var testStringThree = "2 equals $int.property";
        final var unitThree = GameStatePredicateParser.parseIntegerPredicate(testStringThree);
        assertFalse(unitThree.evaluate(gameState));

        final var testStringFour = "5 equals $int.property";
        final var unitFour = GameStatePredicateParser.parseIntegerPredicate(testStringFour);
        assertTrue(unitFour.evaluate(gameState));
    }
}
