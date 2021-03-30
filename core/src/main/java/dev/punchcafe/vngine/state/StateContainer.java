package dev.punchcafe.vngine.state;

import com.google.common.collect.ImmutableMap;
import dev.punchcafe.vngine.NoSuchPropertyException;
import dev.punchcafe.vngine.game.GameStateSnapshot;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class StateContainer {

    protected final Map<String, Integer> integerPropertyMap = new HashMap<>();
    protected final Map<String, Boolean> booleanPropertyMap = new HashMap<>();
    protected final Map<String, String> stringPropertyMap = new HashMap<>();

    public StateContainer(@NonNull final List<String> integerProperties,
                        @NonNull final List<String> booleanProperties,
                        @NonNull final List<String> stringProperties) {
        for (final var property : integerProperties) {
            integerPropertyMap.put(property.toLowerCase(), 0);
        }
        for (final var property : booleanProperties) {
            booleanPropertyMap.put(property.toLowerCase(), false);
        }
        for (final var property : stringProperties) {
            stringPropertyMap.put(property.toLowerCase(), "none");
        }
    }

    public boolean doesIntegerPropertyExist(String propertyName) {
        return integerPropertyMap.get(propertyName) != null;
    }

    public boolean doesBooleanPropertyExist(String propertyName) {
        return booleanPropertyMap.get(propertyName) != null;
    }

    public boolean doesStringPropertyExist(String propertyName) {
        return stringPropertyMap.get(propertyName) != null;
    }

    public int getIntegerProperty(String property) {
        return Optional.ofNullable(integerPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
    }

    public void changeIntegerPropertyBy(final String property, final int value) {
        final var existingValue = Optional.ofNullable(integerPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
        integerPropertyMap.put(property.toLowerCase(), existingValue + value);
    }

    public boolean getBooleanProperty(String property) {
        return Optional.ofNullable(booleanPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
    }

    public void setBooleanProperty(final String property, final boolean value) {
        Optional.ofNullable(booleanPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
        booleanPropertyMap.put(property.toLowerCase(), value);
    }

    public void setStringProperty(String property, String value) {
        Optional.ofNullable(stringPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
        stringPropertyMap.put(property.toLowerCase(), value.toLowerCase());

    }

    public String getStringProperty(String propertyName) {
        return Optional.ofNullable(stringPropertyMap.get(propertyName))
                .orElseThrow(() -> new NoSuchPropertyException(propertyName));
    }
}
