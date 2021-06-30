package vngpl;

import dev.punchcafe.vngine.pom.InvalidVngplExpression;
import dev.punchcafe.vngine.pom.VngPLParser;
import dev.punchcafe.vngine.pom.model.vngpl.variable.GameVariableLevel;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerGameVariable;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerLiteral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VngPredicateLangParserTest {

    @Test
    void shouldParseIntegerGameVariable(){
        final var expression = "$int.my_variable";
        final var expectedObject = new IntegerGameVariable(GameVariableLevel.GAME, "my_variable");
        assertEquals(VngPLParser.parseAtomicIntegerVariable(expression), expectedObject);
    }

    @Test
    void shouldNotParseIntegerGameVariableWhichDoesntMatch(){
        final var expression = "$int.my_variable  someextra";
        assertThrows(InvalidVngplExpression.class, () -> VngPLParser.parseAtomicIntegerVariable(expression));
    }

    @Test
    void shouldConvertIntegerGameVariableToVngpl(){
        final var expression = "$int.my_variable";
        final var object = new IntegerGameVariable(GameVariableLevel.GAME, "my_variable");
        assertEquals(object.asVngQL(), expression);
    }

    @Test
    void shouldParseIntegerLiteral(){
        final var expression = "15";
        final var expectedObject = new IntegerLiteral(15);
        assertEquals(VngPLParser.parseAtomicIntegerVariable(expression), expectedObject);
    }

    @Test
    void shouldNotParseJibberish(){
        final var expression = "fihufeiru";
        assertThrows(InvalidVngplExpression.class, () -> VngPLParser.parseAtomicIntegerVariable(expression));
    }
}
