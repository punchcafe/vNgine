package dev.punchcafe.vngine.node.oldnode;

import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.Narrative;
import dev.punchcafe.vngine.PlayerObserver;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO: implement Hybrid of player input and automatic? or rely on correct graph creation?

/**
 * A {@link SceneNode} whose {@link SceneNode#prompt(PlayerObserver, GameState)} result depends on manual player input.
 *
 */
public final class PlayerInputSceneNode implements SceneNode {

    private final String id;
    private final Narrative narrative;
    private final Map<String, SceneNode> idToSceneNodeMap;
    private final Function<String, String> playerChoiceToNodeId;
    private final List<String> playerChoices;
    private final Consumer<GameState> modifyGameStateFunction;

    public PlayerInputSceneNode(final String id,
                                final Narrative narrative,
                                final Function<String, String> playerChoiceToNodeId,
                                final List<SceneNode> subsequentScenes,
                                final List<String> playerChoices,
                                final Consumer<GameState> modifyGameStateFunction) {
        this.id = id;
        this.narrative = narrative;
        this.idToSceneNodeMap = subsequentScenes.stream().collect(Collectors.toMap(SceneNode::getId, Function.identity()));
        this.playerChoiceToNodeId = playerChoiceToNodeId;
        this.playerChoices = playerChoices;
        this.modifyGameStateFunction = modifyGameStateFunction;
    }


    @Override
    public void modifyGameState(GameState gameState) {
        if(modifyGameStateFunction != null){
            modifyGameStateFunction.accept(gameState);
        }
    }

    @Override
    public Narrative getNarrative() {
        return this.narrative;
    }

    @Override
    public SceneNode prompt(PlayerObserver playerObserver, GameState gameState) {

        return this.idToSceneNodeMap.get(playerChoiceToNodeId.apply(playerObserver.getFromChoice(playerChoices)));
    }

    @Override
    public String getId() {
        return this.id;
    }
}