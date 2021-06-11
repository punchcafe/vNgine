package dev.punchcafe.vngine.save;

import dev.punchcafe.vngine.chapter.ChapterNode;
import dev.punchcafe.vngine.node.LoadGameNode;
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
    public NodeIdentifier visitChapterNode(ChapterNode chapterNode) {
        return NodeIdentifier.builder()
                .nodeId(null)
                .chapterId(chapterNode.getId())
                .build();
    }

    @Override
    public NodeIdentifier visitLoadGameNode(LoadGameNode loadGameNode) {
        throw new RuntimeException("Unable to get save data from a load data node");
    }
}
