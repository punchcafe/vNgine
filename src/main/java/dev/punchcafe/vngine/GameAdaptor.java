package dev.punchcafe.vngine;

import dev.punchcafe.vngine.node.SceneNode;

/**
 * Any platform-specific implementation of {@link GameAdaptor} may be passed to the {@link GameController} constructor
 * to allow easy cross-platform usage.
 */
public interface GameAdaptor {

    SceneNode getFirstNode();

    NarrativeReader getNarrativeReader();

    GameState getGameState();

    PlayerObserver getPlayerObserver();

}
