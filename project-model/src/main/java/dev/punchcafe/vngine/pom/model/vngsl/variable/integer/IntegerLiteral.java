package dev.punchcafe.vngine.pom.model.vngsl.variable.integer;

import dev.punchcafe.vngine.pom.model.vngsl.variable.Variable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerLiteral implements IntegerVariable {

    private static String VNGS_SCHEMA_INTEGER_LITERAL_TEMPLATE = "%d";

    final int value;

    @Override
    public String asVngQL() {
        return String.format(VNGS_SCHEMA_INTEGER_LITERAL_TEMPLATE, value);
    }
}
