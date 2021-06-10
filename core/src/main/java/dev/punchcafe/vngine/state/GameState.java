package dev.punchcafe.vngine.state;

import dev.punchcafe.vngine.node.gsm.StateLevel;
import lombok.NonNull;

import java.util.List;
import java.util.function.Function;

public class GameState extends StateContainer {

    private ChapterState chapterState;

    public GameState(@NonNull final List<String> integerProperties,
                     @NonNull final List<String> booleanProperties,
                     @NonNull final List<String> stringProperties) {
        super(integerProperties, booleanProperties, stringProperties);
    }

    public void initialiseNewChapterState(final List<String> integerPropertyNames,
                                          final List<String> booleanPropertyNames,
                                          final List<String> stringPropertyNames) {
        this.chapterState = new ChapterState(integerPropertyNames, booleanPropertyNames, stringPropertyNames);
    }

    public ChapterState getChapterState() {
        return this.chapterState;
    }

    public boolean doesBooleanPropertyExistWithLevel(final String property,
                                                     @NonNull final StateLevel stateLevel) {
        return checkPropertyExistenceForStateLevel(property,
                stateLevel,
                this::doesBooleanPropertyExist,
                this.chapterState::doesBooleanPropertyExist);
    }

    public boolean doesIntegerPropertyExistWithLevel(final String property,
                                                     @NonNull final StateLevel stateLevel) {
        return checkPropertyExistenceForStateLevel(property,
                stateLevel,
                this::doesIntegerPropertyExist,
                this.chapterState::doesIntegerPropertyExist);
    }

    public boolean doesStringPropertyExistWithLevel(final String property,
                                                     @NonNull final StateLevel stateLevel) {
        return checkPropertyExistenceForStateLevel(property,
                stateLevel,
                this::doesStringPropertyExist,
                this.chapterState::doesStringPropertyExist);
    }

    private boolean checkPropertyExistenceForStateLevel(final String propertyName,
                                                        final StateLevel stateLevel,
                                                        final Function<String, Boolean> checkExistenceOnGameState,
                                                        final Function<String, Boolean> checkExistenceOnChapterState) {
        switch (stateLevel) {
            case GAME:
            default:
                return checkExistenceOnGameState.apply(propertyName);
            case CHAPTER:
                return checkExistenceOnChapterState.apply(propertyName);
        }
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
