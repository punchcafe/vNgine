package dev.punchcafe.vngine.chapter;

import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.node.INode;
import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;
import dev.punchcafe.vngine.parse.yaml.ChapterConfig;

public class Chapter implements INode {

    private final ChapterConfig yamlconfig;
    private final GameState chapterState;
    private final ChapterBuilder chapterBuilder;
    private final Node entryNode;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public NodeGameStateChange getNodeGameStateChange() {
        // Set chapter state?
        return null;
    }

    @Override
    public String getNarrativeId() {
        return null;
    }

    @Override
    public INode getNextNode() {
        chapterBuilder.buildChapter(yamlconfig)
        // BUILD CHapter, and pass head node
        // Switch out game
        return null;
    }
}
