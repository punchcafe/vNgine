package dev.punchcafe.vngine.chapter;

import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.node.gsm.ChangeChapterState;
import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;
import dev.punchcafe.vngine.config.yaml.ChapterConfig;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * A chapter can act as a node, but provides a logical way to manage large games.
 * A chapter can bootstrap new nodes into existence, and continue like a regular node.
 * <p>
 * TODO: Instead of a game builder having the onus of building all nodes, instead it only needs to get all the
 * chapter configuration values (although we can allow it to do it by file directories if need be). All chapter nodes
 * should be instantiated, but the don't bootstrap the chapter until getNextNode is called. Hopefully, since it keeps
 * no internal track of next node, it should be able to clear memory as we pass thru chapters.
 */
@AllArgsConstructor
public class Chapter implements Node {

    private final ChapterConfig chapterConfig;
    private final ChapterBuilder chapterBuilder;

    @Override
    public String getId() {
        return chapterConfig.getChapterId();
    }

    /**
     * Sets the chapter state to the new state.
     *
     * @return
     */
    @Override
    public NodeGameStateChange getNodeGameStateChange() {
        return new NodeGameStateChange(List.of(new ChangeChapterState(chapterConfig)));
    }

    @Override
    public String getNarrativeId() {
        return null;
    }

    @Override
    public Node getNextNode() {
        return chapterBuilder.buildChapter(chapterConfig);
    }
}
