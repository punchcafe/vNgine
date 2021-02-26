package dev.punchcafe.vngine.parse;

import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.predicate.GameStatePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class ExpressionParser {

    private static class ExpressionLink {

        public ExpressionLink(final GameStatePredicate gameStatePredicate, final Boolean isAnd) {
            this.predicate = gameStatePredicate;
            this.isAnd = isAnd;
        }

        GameStatePredicate predicate;
        Boolean isAnd;
    }

    public enum Token {
        LOGICAL_JOIN, LHS, RHS, OPERATION, BRACKET_OPEN, BRACKET_CLOSE
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

    /**
     * TODO: instead, let's split by link words!! search through by and AND and OR
     */
    public static GameStatePredicate parsePredicate2(final String expression) {
        final var brokenDownClause = splitByAndOrJoin(expression);
        validateAndOrJoins(brokenDownClause);
        if (brokenDownClause.size() == 1) {
            final var singleClause = brokenDownClause.get(0).trim();
            if (singleClause.startsWith("(") && singleClause.endsWith(")")) {
                return parsePredicate2(singleClause.substring(1, singleClause.length() - 1));
            }
            // TODO: check here for brackets in expression, if found must be a bool
            final var clauseCompoenents = singleClause.split(" ");
            final var parseStrategy = getParsingStrategyFromClause(clauseCompoenents[0],
                    clauseCompoenents[2],
                    clauseCompoenents[1]);
            return parseStrategy.apply(singleClause);
        } else {
            final var logics = new ArrayList<PredicateChain>();
            logics.add(new PredicateChain(parsePredicate2(brokenDownClause.get(0)), null));
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
                logics.add(new PredicateChain(parsePredicate2(brokenDownClause.get(i)), link));
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

    private static List<String> extractAndTrimBracketStartAndEnd(final String[] strings) {
        final var tokenisedWords = new ArrayList<String>();
        for (final var word : strings) {
            if (word.startsWith("(") && word.length() > 1) {
                tokenisedWords.add("(");
                tokenisedWords.add(word.substring(1).trim());
            } else if (word.endsWith(")") && word.length() > 1) {
                tokenisedWords.add(word.substring(0, word.length() - 1).trim());
                tokenisedWords.add(")");
            } else {
                tokenisedWords.add(word.trim());
            }
        }
        return tokenisedWords;
    }

    public static GameStatePredicate parsePredicate(final String expression) {
        final var characters = expression.trim().split(" ");
        final var tokenisedWords = new ArrayList<String>();
        for (final var word : characters) {
            if (word.startsWith("(") && word.length() > 1) {
                tokenisedWords.add("(");
                tokenisedWords.add(word.substring(1));
            } else if (word.endsWith(")") && word.length() > 1) {
                tokenisedWords.add(word.substring(0, word.length() - 1));
                tokenisedWords.add(")");
            } else {
                tokenisedWords.add(word);
            }
        }

        int bracketOpenIndexMarker = 0;
        int bracketScope = 0;
        final List<ExpressionLink> expressionLinks = new ArrayList<>();
        for (int i = 0; i < tokenisedWords.size(); ) {
            final var word = tokenisedWords.get(i);
            switch (word) {
                case "(":
                    if (bracketScope == 0) {
                        bracketOpenIndexMarker = i;
                    }
                    bracketScope++;
                    i++;
                    break;
                case ")":
                    if (bracketScope == 1) {
                        // About to exit bracket scope
                        final var bracketString = String.join(" ", tokenisedWords.subList(bracketOpenIndexMarker + 1, i));
                        if (i == tokenisedWords.size() - 1) {
                            // Last word
                            expressionLinks.add(new ExpressionLink(parsePredicate(bracketString), null));
                        } else {
                            // grab link word
                            final Boolean isAnd = getIsAndFromWord(tokenisedWords.get(i + 1));
                            expressionLinks.add(new ExpressionLink(parsePredicate(bracketString), isAnd));
                        }
                        // Extra because we've extracedted the link property
                        if (getIsAndFromWord(tokenisedWords.get(i + 1)) != null) {
                            i += 2;
                        } else {
                            i++;
                        }
                        bracketScope--;
                        continue;
                    }
                    bracketScope--;
                    i++;
                    break;
                default:
                    if (bracketScope != 0) {
                        // We are still within a bracket
                        i++;
                        continue;
                    }
                    // Parse a standard clause (can be extracted if need be)
                    final var clauseElements = tokenisedWords.subList(i, i + 3);
                    final var clauseString = String.join(" ", clauseElements);
                    final var parsingStrategy = getParsingStrategyFromClause(clauseElements.get(0),
                            clauseElements.get(2),
                            clauseElements.get(1));
                    final var isAnd = tokenisedWords.size() < i + 4 + 1 ? null : getIsAndFromWord(tokenisedWords.get(i + 4));
                    final var link = new ExpressionLink(parsingStrategy.apply(clauseString), isAnd);
                    expressionLinks.add(link);
                    if (isAnd == null) {
                        i = i + 3;
                    } else {
                        i = i + 4;
                    }
            }
        }
        return (gameState -> {
            if (expressionLinks.size() == 1) {
                return expressionLinks.get(0).predicate.evaluate(gameState);
            }
            boolean nextLinkIsAnd = expressionLinks.get(0).isAnd;
            boolean result = expressionLinks.get(0).predicate.evaluate(gameState);
            for (final var link : expressionLinks.subList(1, expressionLinks.size())) {
                final var predicate = link.predicate.evaluate(gameState);
                if (nextLinkIsAnd) {
                    result = result && predicate;
                } else {
                    result = result || predicate;
                }
                if (link.isAnd == null) {
                    // end of chain
                    continue;
                } else {
                    nextLinkIsAnd = link.isAnd;
                }
            }
            return result;
        });
    }

    private enum VariableType {
        INTEGER, STRING, BOOLEAN
    }

    /**
     * todo: use $bool. and $num. and $str. to allow distinctions
     *
     * @return
     */
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
                return ExpressionParser::parseIntegerPredicate;
            case BOOLEAN:
                return ExpressionParser::parseBooleanPredicate;
            case STRING:
                return ExpressionParser::parseClassificationPredicate;
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

    private static Boolean getIsAndFromWord(final String word) {
        switch (word.toUpperCase()) {
            case "AND":
                return true;
            case "OR":
                return false;
            case ")":
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static GameStatePredicate parseIntegerPredicate(final String atomicExpression) {
        final var tokens = atomicExpression.split(" ");
        switch (tokens[1].toUpperCase()) {
            case "MORE_THAN":
                return (gameState) -> evaluateIntegerVariable(tokens[0], gameState) > evaluateIntegerVariable(tokens[2], gameState);
            case "LESS_THAN":
                return (gameState) -> evaluateIntegerVariable(tokens[0], gameState) < evaluateIntegerVariable(tokens[2], gameState);
            case "EQUALS":
                return (gameState) -> evaluateIntegerVariable(tokens[0], gameState) == evaluateIntegerVariable(tokens[2], gameState);
            default:
                // TODO: make checked exception
                throw new UnsupportedOperationException();
        }
    }

    public static GameStatePredicate parseBooleanPredicate(final String atomicExpression) {
        final var tokens = atomicExpression.split(" ");
        switch (tokens[1].toUpperCase()) {
            case "IS":
                return (gameState) -> evaluateBooleanVariable(tokens[0], gameState)
                        == evaluateBooleanVariable(tokens[2], gameState);
            case "ISNT":
            case "ISN'T":
                return (gameState) -> evaluateBooleanVariable(tokens[0], gameState)
                        != evaluateBooleanVariable(tokens[2], gameState);
            default:
                // TODO: make checked exception
                throw new UnsupportedOperationException();
        }
    }

    public static GameStatePredicate parseClassificationPredicate(final String atomicExpression) {
        final var tokens = atomicExpression.split(" ");
        switch (tokens[1].toUpperCase()) {
            case "IS":
                return (gameState) ->
                        evaluateStringVariable(tokens[0], gameState).equals(evaluateStringVariable(tokens[2], gameState));
            case "ISNT":
            case "ISN'T":
                return (gameState) ->
                        !evaluateStringVariable(tokens[0], gameState).equals(evaluateStringVariable(tokens[2], gameState));
            default:
                // TODO: make checked exception
                throw new UnsupportedOperationException();
        }
    }

    private static int evaluateIntegerVariable(final String variable, GameState gameState) {
        if (variable.startsWith("$int.")) {
            return gameState.getIntegerProperty(variable.substring(5));
        } else {
            return Integer.parseInt(variable);
        }
    }

    private static boolean evaluateBooleanVariable(final String variable, GameState gameState) {
        if (variable.startsWith("$bool.")) {
            return gameState.getBooleanProperty(variable.substring(6));
        } else {
            return Boolean.parseBoolean(variable.toLowerCase());
        }
    }

    private static String evaluateStringVariable(final String variable, GameState gameState) {
        if (variable.startsWith("$str.")) {
            return gameState.getClassificationProperty(variable.substring(5));
        }
        if (variable.startsWith("'") && variable.endsWith("'")) {
            return variable.substring(1, variable.length() - 1);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
