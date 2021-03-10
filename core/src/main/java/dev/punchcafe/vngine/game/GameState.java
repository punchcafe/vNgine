package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.NoSuchPropertyException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameState {

    private final Map<String, Integer> integerPropertyMap = new HashMap<>();
    private final Map<String, Boolean> booleanPropertyMap = new HashMap<>();
    //private final Map<String, String> classificationPropertyMap;


    public GameState(final List<String> integerProperties,
                     final List<String> booleanProperties,
                     final Map<String, String> classificationDefaults){
        for(final var property : integerProperties){
            integerPropertyMap.put(property.toLowerCase(), 0);
        }
        for(final var property : booleanProperties){
            booleanPropertyMap.put(property.toLowerCase(), false);
        }
        //this.classificationPropertyMap = new HashMap<>(classificationDefaults);
    }

    public boolean doesIntegerPropertyExist(String propertyName){
        return integerPropertyMap.get(propertyName) != null;
    }

    public boolean doesBooleanPropertyExist(String propertyName){
        return booleanPropertyMap.get(propertyName) != null;
    }

    public int getIntegerProperty(String property) {
        return Optional.ofNullable(integerPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
    }

    public void changeIntegerPropertyBy(final String property, final int value){
        final var existingValue = Optional.ofNullable(integerPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
        integerPropertyMap.put(property.toLowerCase(), existingValue + value);
    }

    public boolean getBooleanProperty(String property) {
        return Optional.ofNullable(booleanPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
    }

    public void setBooleanProperty(final String property, final boolean value){
        final var existingValue = Optional.ofNullable(booleanPropertyMap.get(property.toLowerCase()))
                .orElseThrow(() -> new NoSuchPropertyException(property));
        booleanPropertyMap.put(property.toLowerCase(),  value);
    }

    public void setClassificationProperty(String property, String value) {

    }

    public String getClassificationProperty(String propertyName) {
        return null;
    }
}
