package dev.punchcafe.vngine.expression;

import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.predicate.GameStatePredicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;

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

    /**
     *
     * TODO: instead, let's split by link words!! search through by and AND and OR
     *
     */

    public static GameStatePredicate parsePredicate(final String expression) {
        final var characters = expression.trim().split(" ");
        final var tokenisedWords = new ArrayList<String>();
        for (final var word : characters) {
            if (word.startsWith("(") && word.length() > 1) {
                tokenisedWords.add("(");
                tokenisedWords.add(word.substring(1));
            } else if (word.endsWith(")") && word.length() > 1) {
                tokenisedWords.add(word.substring(0,word.length() -1));
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
                        if(getIsAndFromWord(tokenisedWords.get(i + 1)) != null){
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
                    final var clauseElements = tokenisedWords.subList(i, i+3);
                    final var clauseString = String.join(" ", clauseElements);
                    final var parsingStrategy = getParsingStrategyFromClause(clauseElements.get(0),
                            clauseElements.get(2),
                            clauseElements.get(1));
                    final var isAnd = tokenisedWords.size() < i + 4 + 1 ? null : getIsAndFromWord(tokenisedWords.get(i+4));
                    final var link = new ExpressionLink(parsingStrategy.apply(clauseString), isAnd);
                    expressionLinks.add(link);
                    if(getIsAndFromWord(tokenisedWords.get(i+4)) == null){
                        i = i + 3;
                    } else {
                        i = i + 4;
                    }
            }
        }
        return (gameState -> {
            if(expressionLinks.size() == 1){
                return expressionLinks.get(0).predicate.evaluate(gameState);
            }
            boolean nextLinkIsAnd = expressionLinks.get(0).isAnd;
            boolean result = expressionLinks.get(0).predicate.evaluate(gameState);
            for(final var link : expressionLinks.subList(1, expressionLinks.size())){
                final var predicate = link.predicate.evaluate(gameState);
                if(nextLinkIsAnd){
                    result = result && predicate;
                } else {
                    result = result || predicate;
                }
                if(link.isAnd == null){
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
        if(lhsVarType != rhsVarType){
            throw new RuntimeException("Non matching operand types");
        }
        if(!opVarType.contains(lhsVarType)){
            throw new RuntimeException("unsupported operation");
        }

        switch (lhsVarType){
            case INTEGER:
                return ExpressionParser::parseIntegerPredicate;
            case BOOLEAN:
                return ExpressionParser::parseBooleanPredicate;
            case STRING:
                return ExpressionParser::parseClassificationPredicate;
        }
        return null;
    }

    private static VariableType establishVariableType(final String variable){
        if((variable.startsWith("'") && variable.endsWith("'")) || variable.startsWith("$str.")){
            return VariableType.STRING;
        }
        if((variable.equals("true") || variable.equals("false")) || variable.startsWith("$bool.")){
            return VariableType.BOOLEAN;
        }
        if(variable.startsWith("$int.")){
            return VariableType.INTEGER;
        }
        try{
            Integer.parseInt(variable);
            return VariableType.INTEGER;
        } catch (Exception ex) {
            throw new UnsupportedOperationException();
        }
    }

    private static Set<VariableType> establishPossibleVariableTypesFromOp(final String op){
        switch (op.toLowerCase()){
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
