package dev.punchcafe.vngine.pom.model.vngpl.variable.integer;

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
