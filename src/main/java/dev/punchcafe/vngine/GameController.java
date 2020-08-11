package dev.punchcafe.vngine;

import dev.punchcafe.vngine.node.SceneNode;

//TODO: make configurable by yaml
public class GameController {

    private final SceneNode firstNode;
    private final NarrativeReader narrativeReader;
    private final GameState gameState;
    private final PlayerObserver playerObserver;

    public GameController(final SceneNode firstNode, final NarrativeReader narrativeReader, final GameState gameState, final PlayerObserver playerObserver) {
        this.firstNode = firstNode;
        this.narrativeReader = narrativeReader;
        this.gameState = gameState;
        this.playerObserver = playerObserver;
    }

    public GameController(final GameAdaptor adaptor){
        this.firstNode = adaptor.getFirstNode();
        this.narrativeReader = adaptor.getNarrativeReader();
        this.gameState = adaptor.getGameState();
        this.playerObserver = adaptor.getPlayerObserver();
    }

    public void play() {
        var node = firstNode;
        while (node != null) {
            node.modifyGameState(gameState);
            narrativeReader.readNarrative(node.getNarrative());
            node = node.prompt(playerObserver, gameState);
        }
    }
}
