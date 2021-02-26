package dev.punchcafe.vngine.node.oldnode;

import dev.punchcafe.vngine.old.OldGameState;
import dev.punchcafe.vngine.old.Narrative;
import dev.punchcafe.vngine.player.PlayerObserver;

/**
 * A {@link SceneNode} whose {@link SceneNode#prompt(PlayerObserver, OldGameState)} result is automatically decided by the
 * {@link OldGameState}.
 */
public final class FinalSceneNode implements SceneNode {

    private final String id;
    private final Narrative narrative;

    public FinalSceneNode(final String id,
                          final Narrative narrative) {
        this.id = id;
        this.narrative = narrative;
    }


    @Override
    public void modifyGameState(OldGameState gameState) {
    }

    @Override
    public Narrative getNarrative() {
        return this.narrative;
    }

    @Override
    public SceneNode prompt(PlayerObserver playerObserver, OldGameState gameState) {
        return null;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
