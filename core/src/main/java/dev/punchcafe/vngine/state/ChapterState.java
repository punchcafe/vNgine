package dev.punchcafe.vngine.state;

import lombok.NonNull;

import java.util.List;

public class ChapterState extends StateContainer {

    public ChapterState(@NonNull List<String> integerProperties, @NonNull List<String> booleanProperties, @NonNull List<String> stringProperties) {
        super(integerProperties, booleanProperties, stringProperties);
    }
}
