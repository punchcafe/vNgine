package dev.punchcafe.vngine.state;

import com.google.common.collect.ImmutableMap;
import dev.punchcafe.vngine.game.GameStateSnapshot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

public class GameState extends StateContainer {

    private ChapterState chapterState;

    public GameState(@NonNull final List<String> integerProperties,
                     @NonNull final List<String> booleanProperties,
                     @NonNull final List<String> stringProperties) {
        super(integerProperties, booleanProperties, stringProperties);
    }

    public void initialiseNewChapterState(final List<String> integerPropertyNames,
                                          final List<String> booleanPropertyNames,
                                          final List<String> stringPropertyNames){
        this.chapterState = new ChapterState(integerPropertyNames, booleanPropertyNames, stringPropertyNames);
    }

    public String getStringChapterProperty(final String propertyName){
        return chapterState.getStringProperty(propertyName);
    }

    public Integer getIntegerChapterProperty(final String propertyName){
        return chapterState.getIntegerProperty(propertyName);
    }

    public Boolean getBooleanChapterProperty(final String propertyName){
        return chapterState.getBooleanProperty(propertyName);
    }

    /*
    TODO: state
    public static GameState fromSnapshot(final GameStateSnapshot snapshot) {
        return new GameState(snapshot.getIntegerPropertyMap(),
                snapshot.getBooleanPropertyMap(),
                snapshot.getStringPropertyMap());
    }

    public GameStateSnapshot takeSnapshot() {
        return GameStateSnapshot.builder()
                .booleanPropertyMap(ImmutableMap.<String, Boolean>builder().putAll(this.booleanPropertyMap).build())
                .stringPropertyMap(ImmutableMap.<String, String>builder().putAll(this.stringPropertyMap).build())
                .integerPropertyMap(ImmutableMap.<String, Integer>builder().putAll(this.integerPropertyMap).build())
                .build();
    }

     */

}
