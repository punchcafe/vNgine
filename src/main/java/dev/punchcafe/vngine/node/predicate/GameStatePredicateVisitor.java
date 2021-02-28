package dev.punchcafe.vngine.node.predicate;

import dev.punchcafe.vngine.node.predicate.bool.BooleanPredicate;
import dev.punchcafe.vngine.node.predicate.bool.BooleanVariableValue;
import dev.punchcafe.vngine.node.predicate.chain.PredicateChain;
import dev.punchcafe.vngine.node.predicate.integer.IntegerComparisonPredicate;
import dev.punchcafe.vngine.node.predicate.integer.IntegerVariableValue;
import dev.punchcafe.vngine.node.predicate.string.StringPredicate;
import dev.punchcafe.vngine.node.predicate.string.StringVariableValue;

public interface GameStatePredicateVisitor<T> {

    T visitStringPredicate(final StringPredicate stringPredicate);
    T visitIntegerPredicate(final IntegerComparisonPredicate integerComparisonPredicate);
    T visitBooleanPredicate(final BooleanPredicate booleanPredicate);
    T visitChainPredicate(final PredicateChain predicateChain);

    T visitSimpleValue(final SimplePredicateValue<?> value);
    T visitIntegerVariableValue(final IntegerVariableValue integerVariableValue);
    T visitStringVariableValue(final StringVariableValue stringVariableValue);
    T visitBooleanVariableValue(final BooleanVariableValue stringVariableValue);
}
