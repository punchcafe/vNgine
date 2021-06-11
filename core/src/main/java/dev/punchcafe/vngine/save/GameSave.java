package dev.punchcafe.vngine.save;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameSave {
    private NodeIdentifier nodeIdentifier;
    private SavedGameState savedGameState;
}
