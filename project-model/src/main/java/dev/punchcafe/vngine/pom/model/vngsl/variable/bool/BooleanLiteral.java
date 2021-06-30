package dev.punchcafe.vngine.pom.model.vngsl.variable.bool;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BooleanLiteral implements BooleanVariable {

    private static String VNGS_SCHEMA_BOOLEAN_LITERAL_TEMPLATE = "%b";

    final boolean value;

    @Override
    public String asVngQL() {
        return String.format(VNGS_SCHEMA_BOOLEAN_LITERAL_TEMPLATE, this.value);
    }
}
