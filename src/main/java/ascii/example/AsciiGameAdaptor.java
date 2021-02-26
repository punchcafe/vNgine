package ascii.example;

import dev.punchcafe.vngine.GameAdaptor;
import dev.punchcafe.vngine.GameState;
import dev.punchcafe.vngine.NarrativeReader;
import dev.punchcafe.vngine.PlayerObserver;
import dev.punchcafe.vngine.node.oldnode.SceneNode;

import static ascii.example.AsciiSceneNodeConstants.NODE_1;

public class AsciiGameAdaptor implements GameAdaptor {

    public static SceneNode generateSceneNodesAndReturnFirst(){
        return NODE_1;
    };

    public static NarrativeReader generateNarrativeReader(){
        return new AsciiNarrativeReader();
    }

    public static GameState generateNewGameState(){
        return new AsciiGameState();
    }

    public static PlayerObserver generateAsciiPlayerObserver(){
        return new AsciiPlayerObserver();
    }

    @Override
    public SceneNode getFirstNode() {
        return generateSceneNodesAndReturnFirst();
    }

    @Override
    public NarrativeReader getNarrativeReader() {
        return generateNarrativeReader();
    }

    @Override
    public GameState getGameState() {
        return generateNewGameState();
    }

    @Override
    public PlayerObserver getPlayerObserver() {
        return generateAsciiPlayerObserver();
    }
}
