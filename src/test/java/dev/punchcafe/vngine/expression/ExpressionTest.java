package dev.punchcafe.vngine.expression;

import dev.punchcafe.vngine.parse.ExpressionParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpressionTest {

    @Test
    void evaluates_simpleExpression_is_withValues(){
        final var testStringOne = "true is true";
        final var unitOne = ExpressionParser.parsePredicate(testStringOne);
        assertTrue(unitOne.evaluate(null));
    }

    @Test
    void evaluates_simpleExpression_is_withValuses(){
        final var testStringOne = "true is false";
        final var unitOne = ExpressionParser.parsePredicate(testStringOne);
        assertFalse(unitOne.evaluate(null));
    }

    @Test
    void evaluates_simpleExpression_iss_withValues(){
        final var testStringOne = "(true is true) and (true is false)";
        final var unitOne = ExpressionParser.parsePredicate(testStringOne);
        assertFalse(unitOne.evaluate(null));
    }

    @Test
    void evaluates_simpleExpressions_iss_withValues(){
        final var testStringOne = "(true is true) and (true is (false isnt true)) and (1 more_than 2)";
        final var unitOne = ExpressionParser.parsePredicate(testStringOne);
        assertFalse(unitOne.evaluate(null));
    }

    @Test
    void evaluates_simpleExpressions_iss_withValueszzz(){
        final var testStringOne = "(true is true) and (true is false) and (1 more_than 2)";
        final var unitOne = ExpressionParser.parsePredicate2(testStringOne);
        assertFalse(unitOne.evaluate(null));
    }

    @Test
    void evaluates_simpleExpressions_issss_withValues(){
        final var testStringOne = "(true is true) and (true is (false isnt true)) and (1 more_than 2)";
        final var unitOne = ExpressionParser.splitByAndOrJoin(testStringOne);
        System.out.println("lol");
    }

}
