package dev.punchcafe.vngine.pom.model.vngsl.variable.string;

import dev.punchcafe.vngine.pom.model.vngsl.variable.Variable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringLiteral implements Variable {

    private static String VNGS_SCHEMA_STRING_LITERAL_TEMPLATE = "'%s'";

    final String value;

    @Override
    public String asVngQL() {
        return String.format(VNGS_SCHEMA_STRING_LITERAL_TEMPLATE, this.value);
    }
}
