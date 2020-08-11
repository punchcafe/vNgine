package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.Narrative;
import dev.punchcafe.vngine.PlayerObserver;

/**
 * A {@link SceneNode} whose {@link SceneNode#prompt(PlayerObserver, GameState)} result is automatically decided by the
 * {@link GameState}.
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
    public void modifyGameState(GameState gameState) {
    }

    @Override
    public Narrative getNarrative() {
        return this.narrative;
    }

    @Override
    public SceneNode prompt(PlayerObserver playerObserver, GameState gameState) {
        return null;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
