package dev.punchcafe.vngine.state;

import lombok.NonNull;

import java.util.List;

public class ChapterState extends StateContainer {

    public ChapterState(@NonNull final List<String> integerProperties,
                        @NonNull final List<String> booleanProperties,
                        @NonNull final List<String> stringProperties) {
        super(integerProperties, booleanProperties, stringProperties);
    }
}
