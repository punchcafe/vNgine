package dev.punchcafe.vngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameStateImplementation implements GameState {

    private final Map<String, Integer> integerPropertyMap = new HashMap<>();
    private final Map<String, Boolean> booleanPropertyMap = new HashMap<>();
    private final Map<String, String> classificationPropertyMap;


    public GameStateImplementation(final List<String> integerProperties,
                                   final List<String> booleanProperties,
                                   final Map<String, String> classificationDefaults){
        for(final var property : integerProperties){
            integerPropertyMap.put(property.toLowerCase(), 0);
        }
        for(final var property : booleanProperties){
            booleanPropertyMap.put(property.toLowerCase(), false);
        }
        this.classificationPropertyMap = new HashMap<>(classificationDefaults);
    }

    @Override
    public int getIntegerProperty(String property) {
        return Optional.ofNullable(integerPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
    }

    @Override
    public void changeIntegerPropertyBy(final String property, final int value){
        final var existingValue = Optional.ofNullable(integerPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
        integerPropertyMap.put(property.toLowerCase(), existingValue + value);
    }

    @Override
    public boolean getBooleanProperty(String property) {
        return Optional.ofNullable(booleanPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
    }

    @Override
    public void setBooleanProperty(final String property, final boolean value){
        final var existingValue = Optional.ofNullable(booleanPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
        booleanPropertyMap.put(property.toLowerCase(),  value);
    }

    @Override
    public void setClassificationProperty(String property, String value) {

    }

    @Override
    public String getClassificationProperty(String propertyName) {
        return null;
    }
}
