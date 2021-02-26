package dev.punchcafe.vngine.node.oldnode;

import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.Narrative;
import dev.punchcafe.vngine.PlayerObserver;

/**
 * This node is meant to act as a simple node to edit game state, then pass on to the next {@link SceneNode}. The next
 * node is static and will always be the same.
 */
public class FunctionalSceneNode implements SceneNode {

    private final String id;
    private final Narrative narrative;
    private final SceneNode nextScene;

    public FunctionalSceneNode(final String id, final Narrative narrative, final SceneNode nextScene){
        this.id = id;
        this.narrative = narrative;
        this.nextScene = nextScene;
    }

    @Override
    public Narrative getNarrative() {
        return narrative;
    }

    @Override
    public SceneNode prompt(PlayerObserver playerObserver, GameState gameState) {
        return nextScene;
    }

    @Override
    public String getId() {
        return id;
    }
}
