package dev.punchcafe.vngine.pom;

import dev.punchcafe.vngine.pom.model.vngpl.PredicateExpression;
import dev.punchcafe.vngine.pom.model.vngpl.variable.GameVariableLevel;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerGameVariable;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerLiteral;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerVariable;

import java.util.regex.Pattern;

/**
 * Utility class for parsing the vNgine Predicate Language into it's POM model.
 */
public class VngPLParser {

    private static Pattern INTEGER_VARIABLE_PATTERN = Pattern.compile("^(@|\\$)int\\.([^ ]+)+$");
    private static Pattern STRING_VARIABLE_PATTERN = Pattern.compile("^(@|\\$)str\\.([^ ]+)+$");
    private static Pattern BOOLEAN_VARIABLE_PATTERN = Pattern.compile("^(@|\\$)bool\\.([^ ]+)+$");

    public PredicateExpression parseExpression(final String expression){
        return null;
    }

    /**
     * @param atomicIntegerPredicate predicate of form "some_variable more_than other_variable"
     * @return
     */
    public static IntegerVariable parseAtomicIntegerPredicate(final String atomicIntegerPredicate){
        if(!stringStartsWithChapterOrGlobalVariableSymbol(atomicIntegerPredicate)){
            try {
                return new IntegerLiteral(Integer.parseInt(atomicIntegerPredicate.trim()));
            } catch (NumberFormatException ex){
                throw new InvalidVngplExpression();
            }
        }
        final var matcher = INTEGER_VARIABLE_PATTERN.matcher(atomicIntegerPredicate);
        if(!matcher.matches()){
            throw new InvalidVngplExpression();
        }
        final var variableScope = parseGameVariableLevel(matcher.group(1).charAt(0));
        return new IntegerGameVariable(variableScope, matcher.group(2));
    }

    public static GameVariableLevel parseGameVariableLevel(final char symbol){
        if(symbol == '@'){
            return GameVariableLevel.CHAPTER;
        } else if (symbol == '$') {
            return GameVariableLevel.GAME;
        }
        else throw new InvalidVngplExpression();
    }

    public static boolean stringStartsWithChapterOrGlobalVariableSymbol(final String string){
        final var trimmedString = string.trim();
        return trimmedString.startsWith("@") || trimmedString.startsWith("$");
    }
}
