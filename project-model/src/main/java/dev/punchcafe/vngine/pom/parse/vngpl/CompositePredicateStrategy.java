package dev.punchcafe.vngine.pom.parse.vngpl;

import dev.punchcafe.vngine.pom.model.vngpl.PredicateExpression;

public class CompositePredicateStrategy implements ParsingStrategy {
    @Override
    public PredicateExpression parse(String message, PredicateParser predicateParser) {
        // Look for and/ors while not in scope of bracket, extract, send on.
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isApplicable(String message) {
        return message.trim().startsWith("(") || message.contains(" AND ") || message.contains( " OR ");
    }

    @Override
    public Integer priority() {
        return 1;
    }
}
