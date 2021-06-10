package dev.punchcafe.vngine.game.save;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class StateSnapshot {

    private final Map<String, Integer> integerPropertyMap;
    private final Map<String, Boolean> booleanPropertyMap;
    private final Map<String, String> stringPropertyMap;
}
