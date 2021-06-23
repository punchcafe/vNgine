package dev.punchcafe.vngine.save;

import dev.punchcafe.vngine.chapter.ChapterBootstrapperNode;
import dev.punchcafe.vngine.node.LoadGameBootstrapperNode;
import dev.punchcafe.vngine.node.NodeVisitor;
import dev.punchcafe.vngine.node.StoryNode;

public class NodeIdentifierRetrievalVisitor implements NodeVisitor<NodeIdentifier> {

    @Override
    public NodeIdentifier visitStoryNode(StoryNode storyNode) {
        return NodeIdentifier.builder()
                .chapterId(storyNode.getChapterId())
                .nodeId(storyNode.getId())
                .build();
    }

    @Override
    public NodeIdentifier visitChapterNode(ChapterBootstrapperNode chapterBootstrapperNode) {
        return NodeIdentifier.builder()
                .nodeId(null)
                .chapterId(chapterBootstrapperNode.getId())
                .build();
    }

    @Override
    public NodeIdentifier visitLoadGameNode(LoadGameBootstrapperNode loadGameBootstrapperNode) {
        throw new RuntimeException("Unable to get save data from a load data node");
    }
}
