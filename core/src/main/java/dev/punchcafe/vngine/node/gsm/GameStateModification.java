package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.state.GameState;

public interface GameStateModification {

    void modify(final GameState gameState);

    <T> T acceptVisitor(GameStateModificationVisitor<T> visitor);
}
