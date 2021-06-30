package dev.punchcafe.vngine.pom.parse.vngpl;

import dev.punchcafe.vngine.pom.InvalidVngplExpression;
import dev.punchcafe.vngine.pom.model.vngpl.PredicateExpression;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PredicateParser {
    private final List<ParsingStrategy> parsingStrategies;

    public PredicateParser(final List<ParsingStrategy> strategies){
        parsingStrategies = strategies.stream()
                .sorted(Comparator.comparing(ParsingStrategy::priority))
                .collect(Collectors.toUnmodifiableList());
    }

    public PredicateExpression parse(final String expression) {
        final var strategy = parsingStrategies.stream()
                .filter(parsingStrategy -> parsingStrategy.isApplicable(expression))
                .findFirst()
                .orElseThrow(InvalidVngplExpression::new);
        return strategy.parse(expression, this);
    }
}
