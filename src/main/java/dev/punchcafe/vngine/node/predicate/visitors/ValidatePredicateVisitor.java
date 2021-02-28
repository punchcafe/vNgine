package dev.punchcafe.vngine.node.predicate.visitors;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.predicate.GameStatePredicateVisitor;
import dev.punchcafe.vngine.node.predicate.SimpleValue;
import dev.punchcafe.vngine.node.predicate.bool.BooleanPredicate;
import dev.punchcafe.vngine.node.predicate.bool.BooleanVariableValue;
import dev.punchcafe.vngine.node.predicate.chain.PredicateChain;
import dev.punchcafe.vngine.node.predicate.integer.IntegerComparisonPredicate;
import dev.punchcafe.vngine.node.predicate.integer.IntegerVariableValue;
import dev.punchcafe.vngine.node.predicate.string.StringPredicate;
import dev.punchcafe.vngine.node.predicate.string.StringVariableValue;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ValidatePredicateVisitor implements GameStatePredicateVisitor<List<String>> {

    private GameState gameState;

    @Override
    public List<String> visitStringPredicate(StringPredicate stringPredicate) {
        final var errors = new ArrayList<String>();
        if (stringPredicate.getOperation() == null) {
            errors.add("NULL OPERATION");
        }
        errors.addAll(stringPredicate.getLeftHandSide().acceptVisitor(this));
        errors.addAll(stringPredicate.getRightHandSide().acceptVisitor(this));
        return errors;
    }

    @Override
    public List<String> visitIntegerPredicate(IntegerComparisonPredicate integerComparisonPredicate) {
        final var errors = new ArrayList<String>();
        if (integerComparisonPredicate.getComparison() == null) {
            errors.add("NULL COMPARISON");
        }
        errors.addAll(integerComparisonPredicate.getLeftHandSide().acceptVisitor(this));
        errors.addAll(integerComparisonPredicate.getRightHandSide().acceptVisitor(this));
        return errors;
    }

    @Override
    public List<String> visitBooleanPredicate(BooleanPredicate booleanPredicate) {
        final var errors = new ArrayList<String>();
        if (booleanPredicate.getOperation() == null) {
            errors.add("NULL OPERATION");
        }
        errors.addAll(booleanPredicate.getLeftHandSide().acceptVisitor(this));
        errors.addAll(booleanPredicate.getRightHandSide().acceptVisitor(this));
        return errors;
    }

    @Override
    public List<String> visitChainPredicate(PredicateChain predicateChain) {
        // TODO: add chain operation validation
        return predicateChain.getPredicateChainLinks().stream()
                .map(predicateChainLink ->
                        predicateChainLink.getPredicate().acceptVisitor(this))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> visitSimpleValue(SimpleValue<?> value) {
        if (value.getValue(null) == null) {
            return List.of("NULL VALUE");
        }
        return List.of();
    }

    @Override
    public List<String> visitIntegerVariableValue(IntegerVariableValue integerVariableValue) {
        if (!gameState.doesIntegerPropertyExist(integerVariableValue.getVariableName())) {
            return List.of(String.format("No INT variable with name %s", integerVariableValue.getVariableName()));
        }
        return List.of();
    }

    @Override
    public List<String> visitStringVariableValue(StringVariableValue stringVariableValue) {
        return List.of();
    }

    @Override
    public List<String> visitBooleanVariableValue(BooleanVariableValue booleanVariableValue) {
        if (!gameState.doesBooleanPropertyExist(booleanVariableValue.getVariableName())) {
            return List.of(String.format("No BOOL variable with name %s", booleanVariableValue.getVariableName()));
        }
        return List.of();
    }
}
