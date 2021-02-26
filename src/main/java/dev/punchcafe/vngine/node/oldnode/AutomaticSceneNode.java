package dev.punchcafe.vngine.node.oldnode;

import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.Narrative;
import dev.punchcafe.vngine.PlayerObserver;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A {@link SceneNode} whose {@link SceneNode#prompt(PlayerObserver, GameState)} result is automatically decided by the
 * {@link GameState}.
 */
public final class AutomaticSceneNode implements SceneNode {

    private final String id;
    private final Narrative narrative;
    private final Function<GameState, String> determineNextNodeIdStrategy;
    private final Map<String, SceneNode> idToSceneNodeMap;
    private final Consumer<GameState> gameStateModifier;

    public AutomaticSceneNode(final String id,
                              final Narrative narrative,
                              final Function<GameState, String> determineNextNodeIdStrategy,
                              final List<SceneNode> subsequentScenes,
                              final Consumer<GameState> gameStateModifier) {
        this.id = id;
        this.narrative = narrative;
        this.determineNextNodeIdStrategy = determineNextNodeIdStrategy;
        this.idToSceneNodeMap = subsequentScenes.stream().collect(Collectors.toMap(SceneNode::getId, Function.identity()));
        this.gameStateModifier = gameStateModifier;
    }


    @Override
    public void modifyGameState(GameState gameState) {
        if(this.gameStateModifier != null){
            this.gameStateModifier.accept(gameState);
        }
    }

    @Override
    public Narrative getNarrative() {
        return this.narrative;
    }

    @Override
    public SceneNode prompt(PlayerObserver playerObserver, GameState gameState) {
        return this.idToSceneNodeMap.get(determineNextNodeIdStrategy.apply(gameState));
    }

    @Override
    public String getId() {
        return this.id;
    }
}
