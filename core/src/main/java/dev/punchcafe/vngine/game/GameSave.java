package dev.punchcafe.vngine.game;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameSave {
    private String nodeId;
    private GameStateSnapshot gameState;
}
