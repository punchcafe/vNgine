package dev.punchcafe.vngine.pom.model.vngpl.variable.string;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StringLiteral implements StringVariable {

    private static String VNGS_SCHEMA_STRING_LITERAL_TEMPLATE = "'%s'";

    final String value;

    @Override
    public String asVngQL() {
        return String.format(VNGS_SCHEMA_STRING_LITERAL_TEMPLATE, this.value);
    }
}
