package dev.punchcafe.vngine.game.save;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavedGameState {
    private final StateSnapshot gameStateSnapshot;
    private final StateSnapshot chapterStateSnapshot;
}
