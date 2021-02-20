package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.GameState;

public interface GameStateModification {

    GameState modify(final GameState gameState);
}
