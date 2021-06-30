package vngpl;

import dev.punchcafe.vngine.pom.InvalidVngplExpression;
import dev.punchcafe.vngine.pom.model.vngpl.bifunction.BooleanBiFunction;
import dev.punchcafe.vngine.pom.model.vngpl.bifunction.IntegerBiFunction;
import dev.punchcafe.vngine.pom.model.vngpl.bifunction.StringBiFunction;
import dev.punchcafe.vngine.pom.model.vngpl.composite.AndOrOperation;
import dev.punchcafe.vngine.pom.model.vngpl.composite.CompositeExpression;
import dev.punchcafe.vngine.pom.model.vngpl.composite.PredicateLink;
import dev.punchcafe.vngine.pom.model.vngpl.variable.GameVariableLevel;
import dev.punchcafe.vngine.pom.model.vngpl.variable.bool.BoolGameVariable;
import dev.punchcafe.vngine.pom.model.vngpl.variable.bool.BooleanLiteral;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerLiteral;
import dev.punchcafe.vngine.pom.model.vngpl.variable.string.StringLiteral;
import dev.punchcafe.vngine.pom.parse.vngpl.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VngCompositePredicateTest {

    private PredicateParser parser;

    @BeforeEach
    void beforeEach() {
        parser = new PredicateParser(List.of(new CompositePredicateStrategy(),
                new BooleanPredicateStrategy(),
                new IntegerPredicateStrategy(),
                new StringPredicateStrategy()));
    }

    @Test
    void parseExampleOne() {
        final var expression = "true is false AND 2 equals 3 AND 'hello' isnt 'goodbye'";
        final var expectedResult = CompositeExpression.fromLinks(List.of(
                PredicateLink.firstLink(BooleanBiFunction.is(BooleanLiteral.TRUE, BooleanLiteral.FALSE)),
                PredicateLink.newLink(IntegerBiFunction.equals(new IntegerLiteral(2), new IntegerLiteral(3)),
                        AndOrOperation.AND),
                PredicateLink.newLink(StringBiFunction.isnt(new StringLiteral("hello"), new StringLiteral("goodbye")),
                        AndOrOperation.AND)));
        final var result = parser.parse(expression);
        assertEquals(result, expectedResult);
    }

}
