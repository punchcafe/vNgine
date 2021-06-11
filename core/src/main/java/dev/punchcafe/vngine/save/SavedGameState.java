package dev.punchcafe.vngine.save;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavedGameState {
    private final StateSnapshot gameStateSnapshot;
    private final StateSnapshot chapterStateSnapshot;
}
