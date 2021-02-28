package dev.punchcafe.vngine.parse;

import dev.punchcafe.vngine.node.predicate.*;
import dev.punchcafe.vngine.node.predicate.bool.BooleanPredicate;
import dev.punchcafe.vngine.node.predicate.bool.BooleanVariableValue;
import dev.punchcafe.vngine.node.predicate.integer.IntegerComparisonPredicate;
import dev.punchcafe.vngine.node.predicate.integer.IntegerVariableValue;
import dev.punchcafe.vngine.node.predicate.string.StringPredicate;
import dev.punchcafe.vngine.node.predicate.string.StringVariableValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class GameStatePredicateParser {

    private enum VariableType {
        INTEGER, STRING, BOOLEAN
    }

    private static class PredicateChain {

        enum LogicalLink {
            OR, AND
        }

        GameStatePredicate predicate;
        LogicalLink link;

        public PredicateChain(final GameStatePredicate gameStatePredicate, final LogicalLink logicalLink) {
            this.predicate = gameStatePredicate;
            this.link = logicalLink;
        }
    }

    private static final String AND_TOKEN = " and ";
    private static final String OR_TOKEN = " or ";

    public static GameStatePredicate parsePredicate(final String expression) {
        final var brokenDownClause = splitByAndOrJoin(expression);
        validateAndOrJoins(brokenDownClause);
        if (brokenDownClause.size() == 1) {
            final var singleClause = brokenDownClause.get(0).trim();
            if (singleClause.startsWith("(") && singleClause.endsWith(")")) {
                return parsePredicate(singleClause.substring(1, singleClause.length() - 1));
            }
            // TODO: check here for brackets in expression, if found must be a bool
            final var clauseCompoenents = singleClause.split(" ");
            final var parseStrategy = getParsingStrategyFromClause(clauseCompoenents[0],
                    clauseCompoenents[2],
                    clauseCompoenents[1]);
            return parseStrategy.apply(singleClause);
        } else {
            final var logics = new ArrayList<PredicateChain>();
            logics.add(new PredicateChain(parsePredicate(brokenDownClause.get(0)), null));
            for (int i = 2; i < brokenDownClause.size(); i = i + 2) {
                final PredicateChain.LogicalLink link;
                switch (brokenDownClause.get(i - 1)) {
                    case OR_TOKEN:
                        link = PredicateChain.LogicalLink.OR;
                        break;
                    case AND_TOKEN:
                        link = PredicateChain.LogicalLink.AND;
                        break;
                    default:
                        throw new UnsupportedOperationException();
                }
                logics.add(new PredicateChain(parsePredicate(brokenDownClause.get(i)), link));
            }
            return gameState -> {
                var result = logics.get(0).predicate.evaluate(gameState);
                for (var link : logics.subList(1, logics.size())) {
                    switch (link.link) {
                        case OR:
                            result = result || link.predicate.evaluate(gameState);
                            break;
                        case AND:
                            result = result && link.predicate.evaluate(gameState);
                            break;
                        default:
                            throw new UnsupportedOperationException();
                    }
                }
                return result;
            };
        }
    }

    public static List<String> splitByAndOrJoin(final String expression) {
        final var sanitisedExpression = expression.toLowerCase();
        int bracketScope = 0;
        int lastParsedIndex = 0;
        final List<String> splitByBoolOperations = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            if (sanitisedExpression.charAt(i) == '(') {
                bracketScope++;
                continue;
            } else if (sanitisedExpression.charAt(i) == ')') {
                bracketScope--;
                continue;
            } else if (bracketScope != 0) {
                continue;
            }
            if (AND_TOKEN.equals(substringOrClamp(expression, i, i + AND_TOKEN.length()))) {
                splitByBoolOperations.add(expression.substring(lastParsedIndex, i));
                splitByBoolOperations.add(expression.substring(i, i + AND_TOKEN.length()));
                lastParsedIndex = i + AND_TOKEN.length();
            } else if (OR_TOKEN.equals(substringOrClamp(expression, i, i + OR_TOKEN.length()))) {
                splitByBoolOperations.add(expression.substring(lastParsedIndex, i));
                splitByBoolOperations.add(expression.substring(i, i + OR_TOKEN.length()));
                lastParsedIndex = i + OR_TOKEN.length();
            }
        }
        splitByBoolOperations.add(expression.substring(lastParsedIndex));
        return splitByBoolOperations;
    }

    private static void validateAndOrJoins(final List<String> strings) {
        if (strings.get(0).equals(AND_TOKEN) || strings.get(0).equals(OR_TOKEN)) {
            throw new UnsupportedOperationException();
        }

        if (strings.get(strings.size() - 1).equals(AND_TOKEN) || strings.get(strings.size() - 1).equals(OR_TOKEN)) {
            throw new UnsupportedOperationException();
        }
        if (strings.size() == 1) {
            return;
        }
        boolean lastWasAndOr = false;
        for (final var str : strings.subList(1, strings.size() - 1)) {
            if (str.equals(AND_TOKEN) || str.equals(OR_TOKEN)) {
                if (lastWasAndOr) {
                    throw new RuntimeException("illegal and ors");
                }
                lastWasAndOr = true;
            } else {
                lastWasAndOr = false;
            }
        }
    }

    private static String substringOrClamp(final String string, int beginIndex, int endIndex) {
        if (endIndex > string.length() - 1) {
            return string.substring(beginIndex, string.length() - 1);
        } else {
            return string.substring(beginIndex, endIndex);
        }
    }

    private static Function<String, GameStatePredicate> getParsingStrategyFromClause(final String lhs,
                                                                                     final String rhs,
                                                                                     final String op) {
        final var lhsVarType = establishVariableType(lhs);
        final var rhsVarType = establishVariableType(rhs);
        final var opVarType = establishPossibleVariableTypesFromOp(op);
        if (lhsVarType != rhsVarType) {
            throw new RuntimeException("Non matching operand types");
        }
        if (!opVarType.contains(lhsVarType)) {
            throw new RuntimeException("unsupported operation");
        }

        switch (lhsVarType) {
            case INTEGER:
                return GameStatePredicateParser::parseIntegerPredicate;
            case BOOLEAN:
                return GameStatePredicateParser::parseBooleanPredicate;
            case STRING:
                return GameStatePredicateParser::parseClassificationPredicate;
        }
        return null;
    }

    private static VariableType establishVariableType(final String variable) {
        if ((variable.startsWith("'") && variable.endsWith("'")) || variable.startsWith("$str.")) {
            return VariableType.STRING;
        }
        if ((variable.equals("true") || variable.equals("false")) || variable.startsWith("$bool.")) {
            return VariableType.BOOLEAN;
        }
        if (variable.startsWith("$int.")) {
            return VariableType.INTEGER;
        }
        try {
            Integer.parseInt(variable);
            return VariableType.INTEGER;
        } catch (Exception ex) {
            throw new UnsupportedOperationException();
        }
    }

    private static Set<VariableType> establishPossibleVariableTypesFromOp(final String op) {
        switch (op.toLowerCase()) {
            case "is":
            case "isnt":
            case "isn't":
                return Set.of(VariableType.BOOLEAN, VariableType.STRING);
            case "less_than":
            case "more_than":
            case "equal":
                return Set.of(VariableType.INTEGER);
        }
        throw new UnsupportedOperationException();
    }

    public static GameStatePredicate parseIntegerPredicate(final String atomicExpression) {
        final var tokens = atomicExpression.split(" ");
        switch (tokens[1].toUpperCase()) {
            case "MORE_THAN":
                return new IntegerComparisonPredicate(evaluateIntegerVariable(tokens[0]),
                        evaluateIntegerVariable(tokens[2]),
                        IntegerComparisonPredicate.Comparison.MORE_THAN);
            case "LESS_THAN":
                return new IntegerComparisonPredicate(evaluateIntegerVariable(tokens[0]),
                        evaluateIntegerVariable(tokens[2]),
                        IntegerComparisonPredicate.Comparison.LESS_THAN);
            case "EQUALS":
                return new IntegerComparisonPredicate(evaluateIntegerVariable(tokens[0]),
                        evaluateIntegerVariable(tokens[2]),
                        IntegerComparisonPredicate.Comparison.EQUALS);
            default:
                // TODO: make checked exception
                throw new UnsupportedOperationException();
        }
    }

    public static GameStatePredicate parseBooleanPredicate(final String atomicExpression) {
        final var tokens = atomicExpression.split(" ");
        switch (tokens[1].toUpperCase()) {
            case "IS":
                return new BooleanPredicate(evaluateBooleanVariable(tokens[0]),
                        evaluateBooleanVariable(tokens[2]),
                        BooleanPredicate.Operation.IS);
            case "ISNT":
            case "ISN'T":
                return new BooleanPredicate(evaluateBooleanVariable(tokens[0]),
                        evaluateBooleanVariable(tokens[2]),
                        BooleanPredicate.Operation.ISNT);
            default:
                // TODO: make checked exception
                throw new UnsupportedOperationException();
        }
    }

    public static GameStatePredicate parseClassificationPredicate(final String atomicExpression) {

        final var tokens = atomicExpression.split(" ");
        switch (tokens[1].toUpperCase()) {
            case "IS":
                return new StringPredicate(evaluateStringVariable(tokens[0]),
                    evaluateStringVariable(tokens[2]),
                    StringPredicate.Operation.IS);
            case "ISNT":
            case "ISN'T":
                return new StringPredicate(evaluateStringVariable(tokens[0]),
                        evaluateStringVariable(tokens[2]),
                        StringPredicate.Operation.ISNT);
            default:
                // TODO: make checked exception
                throw new UnsupportedOperationException();
        }
    }

    private static PredicateValue<Integer> evaluateIntegerVariable(final String variable) {
        if (variable.startsWith("$int.")) {
            return new IntegerVariableValue(variable.substring(5));
        } else {
            return new SimpleValue<>(Integer.parseInt(variable));
        }
    }

    private static PredicateValue<Boolean> evaluateBooleanVariable(final String variable) {
        if (variable.startsWith("$bool.")) {
            return new BooleanVariableValue(variable.substring(6));
        } else {
            return new SimpleValue<>(Boolean.parseBoolean(variable.toLowerCase()));
        }
    }

    private static PredicateValue<String> evaluateStringVariable(final String variable) {
        if (variable.startsWith("$str.")) {
            return new StringVariableValue(variable.substring(5));
        }
        if (variable.startsWith("'") && variable.endsWith("'")) {
            return new SimpleValue<>(variable.substring(1, variable.length() - 1));
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
