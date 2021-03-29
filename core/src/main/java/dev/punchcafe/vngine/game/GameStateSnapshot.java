package dev.punchcafe.vngine.game;

import com.google.common.collect.ImmutableMap;
import dev.punchcafe.vngine.NoSuchPropertyException;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Builder
public class GameStateSnapshot {

    private final ImmutableMap<String, Integer> integerPropertyMap;
    private final ImmutableMap<String, Boolean> booleanPropertyMap;
    private final ImmutableMap<String, String> stringPropertyMap;
}
