package dev.punchcafe.vngine.game.save;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameSave {
    private String nodeId;
    private String chapterId;
    private SavedGameState savedGameState;
}
