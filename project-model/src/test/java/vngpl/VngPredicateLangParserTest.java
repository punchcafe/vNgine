package vngpl;

import dev.punchcafe.vngine.pom.VngPLParser;
import dev.punchcafe.vngine.pom.model.vngpl.variable.GameVariableLevel;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerGameVariable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VngPredicateLangParserTest {

    @Test
    void shouldParseIntegerGameVariable(){
        final var expression = "$int.my_variable";
        final var expectedObject = new IntegerGameVariable(GameVariableLevel.GAME, "my_variable");
        assertEquals(VngPLParser.parseAtomicIntegerPredicate(expression), expectedObject);
    }

    @Test
    void shouldConvertIntegerGameVariableToVngpl(){
        final var expression = "$int.my_variable";
        final var object = new IntegerGameVariable(GameVariableLevel.GAME, "my_variable");
        assertEquals(object.asVngQL(), expression);
    }
}
