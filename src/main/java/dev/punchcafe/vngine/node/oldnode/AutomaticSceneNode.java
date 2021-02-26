package dev.punchcafe.vngine.node.oldnode;

import dev.punchcafe.vngine.old.OldGameState;
import dev.punchcafe.vngine.old.Narrative;
import dev.punchcafe.vngine.player.PlayerObserver;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link SceneNode} whose {@link SceneNode#prompt(PlayerObserver, OldGameState)} result is automatically decided by the
 * {@link OldGameState}.
 */
public final class AutomaticSceneNode implements SceneNode {

    private final String id;
    private final Narrative narrative;
    private final Function<OldGameState, String> determineNextNodeIdStrategy;
    private final Map<String, SceneNode> idToSceneNodeMap;
    private final Consumer<OldGameState> gameStateModifier;

    public AutomaticSceneNode(final String id,
                              final Narrative narrative,
                              final Function<OldGameState, String> determineNextNodeIdStrategy,
                              final List<SceneNode> subsequentScenes,
                              final Consumer<OldGameState> gameStateModifier) {
        this.id = id;
        this.narrative = narrative;
        this.determineNextNodeIdStrategy = determineNextNodeIdStrategy;
        this.idToSceneNodeMap = subsequentScenes.stream().collect(Collectors.toMap(SceneNode::getId, Function.identity()));
        this.gameStateModifier = gameStateModifier;
    }


    @Override
    public void modifyGameState(OldGameState gameState) {
        if(this.gameStateModifier != null){
            this.gameStateModifier.accept(gameState);
        }
    }

    @Override
    public Narrative getNarrative() {
        return this.narrative;
    }

    @Override
    public SceneNode prompt(PlayerObserver playerObserver, OldGameState gameState) {
        return this.idToSceneNodeMap.get(determineNextNodeIdStrategy.apply(gameState));
    }

    @Override
    public String getId() {
        return this.id;
    }
}
