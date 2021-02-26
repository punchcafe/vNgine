package dev.punchcafe.vngine.parse;

import dev.punchcafe.vngine.node.gsm.*;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

public class GameStateModifierParser {

    private static Pattern SET_STRING_VARIABLE_PATTERN = Pattern.compile("^ *set +\\$str\\.([^ ]+)+ +to +'([^']+)' *$");
    private static Pattern SET_BOOLEAN_VARIABLE_PATTERN = Pattern.compile("^ *set +\\$bool\\.([^ ]+)+ +to +(true|false) *$");
    private static Pattern INCREASE_INT_VARIABLE_PATTERN = Pattern.compile("^ *increase \\$int\\.([^ ]+) by (\\d+)$");
    private static Pattern DECREASE_INT_VARIABLE_PATTERN = Pattern.compile("^ *decrease \\$int\\.([^ ]+) by (\\d+)$");

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
                    .propertyName(setStringPropertyMatcher.group(1))
                    .propertyValue(setStringPropertyMatcher.group(2))
                    .build();
        } else if (setBooleanPropertyMatcher.matches()){
            return SetBooleanProperty.builder()
                    .propertyName(setBooleanPropertyMatcher.group(1))
                    .booleanValue(Boolean.parseBoolean(setBooleanPropertyMatcher.group(2)))
                    .build();
        } else if(increaseIntegerPropertyMatcher.matches()){
            final int value = Integer.parseInt(increaseIntegerPropertyMatcher.group(2));
            if(value < 0){
                throw new RuntimeException();
            }
            return ChangeIntegerProperty.builder()
                    .propertyName(increaseIntegerPropertyMatcher.group(1))
                    .valueChange(value)
                    .build();

        } else if (decreaseIntegerPropertyMatcher.matches()){
            final int value = Integer.parseInt(decreaseIntegerPropertyMatcher.group(2));
            if(value < 0){
                throw new RuntimeException();
            }
            return ChangeIntegerProperty.builder()
                    .propertyName(decreaseIntegerPropertyMatcher.group(1))
                    .valueChange(value * -1)
                    .build();
        }
        throw new RuntimeException();
    }
}
