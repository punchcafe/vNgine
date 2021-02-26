package ascii.example;

import dev.punchcafe.vngine.old.GameAdaptor;
import dev.punchcafe.vngine.old.OldGameState;
import dev.punchcafe.vngine.old.NarrativeReader;
import dev.punchcafe.vngine.player.PlayerObserver;
import dev.punchcafe.vngine.node.oldnode.SceneNode;

import static ascii.example.AsciiSceneNodeConstants.NODE_1;

public class AsciiGameAdaptor implements GameAdaptor {

    public static SceneNode generateSceneNodesAndReturnFirst(){
        return NODE_1;
    };

    public static NarrativeReader generateNarrativeReader(){
        return new AsciiNarrativeReader();
    }

    public static OldGameState generateNewGameState(){
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
    public OldGameState getGameState() {
        return generateNewGameState();
    }

    @Override
    public PlayerObserver getPlayerObserver() {
        return generateAsciiPlayerObserver();
    }
}
