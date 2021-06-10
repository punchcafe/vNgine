package dev.punchcafe.vngine.config;

import dev.punchcafe.vngine.node.gsm.*;

import java.util.List;
import java.util.regex.Pattern;

import static dev.punchcafe.vngine.config.GameStateParseUtils.parseStateLevelFromVarPrefix;
import static java.util.stream.Collectors.toList;

public class GameStateModifierParser {

    private static Pattern SET_STRING_VARIABLE_PATTERN = Pattern.compile("^ *set +(\\$|@)str\\.([^ ]+)+ +to +'([^']+)' *$");
    private static Pattern SET_BOOLEAN_VARIABLE_PATTERN = Pattern.compile("^ *set +(\\$|@)bool\\.([^ ]+)+ +to +(true|false) *$");
    private static Pattern INCREASE_INT_VARIABLE_PATTERN = Pattern.compile("^ *increase (\\$|@)int\\.([^ ]+) by (\\d+)$");
    private static Pattern DECREASE_INT_VARIABLE_PATTERN = Pattern.compile("^ *decrease (\\$|@)int\\.([^ ]+) by (\\d+)$");

    public static NodeGameStateChange parse(final List<String> expressions) {
        final var modifiers =  expressions.stream()
                .map(GameStateModifierParser::parse)
                .collect(toList());
        return NodeGameStateChange.builder().modifications(modifiers).build();
    }

    public static GameStateModification parse(final String expression) {
        final var setStringPropertyMatcher = SET_STRING_VARIABLE_PATTERN.matcher(expression);
        final var setBooleanPropertyMatcher = SET_BOOLEAN_VARIABLE_PATTERN.matcher(expression);
        final var increaseIntegerPropertyMatcher = INCREASE_INT_VARIABLE_PATTERN.matcher(expression);
        final var decreaseIntegerPropertyMatcher = DECREASE_INT_VARIABLE_PATTERN.matcher(expression);
        if (setStringPropertyMatcher.matches()) {
            return SetStringProperty.builder()
                    .stateLevel(parseStateLevelFromVarPrefix(setStringPropertyMatcher.group(1)))
                    .propertyName(setStringPropertyMatcher.group(2))
                    .propertyValue(setStringPropertyMatcher.group(3))
                    .build();
        } else if (setBooleanPropertyMatcher.matches()){
            return SetBooleanProperty.builder()
                    .stateLevel(parseStateLevelFromVarPrefix(setBooleanPropertyMatcher.group(1)))
                    .propertyName(setBooleanPropertyMatcher.group(2))
                    .booleanValue(Boolean.parseBoolean(setBooleanPropertyMatcher.group(3)))
                    .build();
        } else if(increaseIntegerPropertyMatcher.matches()){
            final int value = Integer.parseInt(increaseIntegerPropertyMatcher.group(3));
            if(value < 0){
                throw new RuntimeException();
            }
            return ChangeIntegerProperty.builder()
                    .stateLevel(parseStateLevelFromVarPrefix(increaseIntegerPropertyMatcher.group(1)))
                    .propertyName(increaseIntegerPropertyMatcher.group(2))
                    .valueChange(value)
                    .build();

        } else if (decreaseIntegerPropertyMatcher.matches()){
            final int value = Integer.parseInt(decreaseIntegerPropertyMatcher.group(3));
            if(value < 0){
                throw new RuntimeException();
            }
            return ChangeIntegerProperty.builder()
                    .stateLevel(parseStateLevelFromVarPrefix(decreaseIntegerPropertyMatcher.group(1)))
                    .propertyName(decreaseIntegerPropertyMatcher.group(2))
                    .valueChange(value * -1)
                    .build();
        }
        throw new RuntimeException();
    }
}
