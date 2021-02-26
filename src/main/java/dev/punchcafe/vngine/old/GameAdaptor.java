package dev.punchcafe.vngine.old;

import dev.punchcafe.vngine.node.oldnode.SceneNode;
import dev.punchcafe.vngine.player.PlayerObserver;

/**
 * Any platform-specific implementation of {@link GameAdaptor} may be passed to the {@link GameController} constructor
 * to allow easy cross-platform usage.
 */
public interface GameAdaptor {

    SceneNode getFirstNode();

    NarrativeReader getNarrativeReader();

    OldGameState getGameState();

    PlayerObserver getPlayerObserver();

}
