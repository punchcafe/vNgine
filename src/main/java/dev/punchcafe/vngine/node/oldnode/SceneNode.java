package dev.punchcafe.vngine.node.oldnode;

import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.Narrative;
import dev.punchcafe.vngine.NarrativeReader;
import dev.punchcafe.vngine.PlayerObserver;

/**
 * The core class for individual nodes in a game. Nodes are designed to have atomic chunks of story, followed by a prompt
 * to determine transition to the next atomic story chunk.
 *
 * All implementations of {@link SceneNode} will be handled in the same way:
 *
 * - first, the modifyGameState function is invoked. This comes first because it represents the consequence
 * (or choice of action) for this node to have been reached. By default, this method does nothing.
 *
 * - next, all elements of the {@link SceneNode#getNarrative()} will be iterated over and read by the implementaion
 * speicific {@link NarrativeReader}.
 *
 * - Lastly, the prompt method will be called to get the next scene. If this is null, the game is over.
 */
public interface SceneNode {
    //TODO: add threading methods to individual scenenode imps (abstract class?) to allow for threading of nodes
    // AFTER creation, current constructor dependency requires backwards loading.
    // Maybe something like dataLoader?

    /**
     * Any node may modify the game state as a result of it being invoked. For example, a node being invoked may
     * indicate a player has chosen a good / bad route for one of their character relationships, and the
     * {@link GameState} should be incremented accordingly.
     *
     * @param gameState
     */
    default void modifyGameState(GameState gameState){};

    /**
     * This return a {@link Narrative} object, which contains all the story elements in the particular node before
     * requiring a prompt.
     *
     * @return
     */
    Narrative getNarrative();

    SceneNode prompt(PlayerObserver playerObserver, GameState gameState);

    /**
     * A method which will return the Scene's unique identifying id.
     *
     * @return
     */
    String getId();
}
