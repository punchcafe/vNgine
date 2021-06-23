package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.chapter.ChapterBootstrapperNode;

public interface NodeVisitor<T> {

    T visitStoryNode(final StoryNode storyNode);

    T visitChapterNode(final ChapterBootstrapperNode chapterBootstrapperNode);

    T visitLoadGameNode(final LoadGameBootstrapperNode loadGameBootstrapperNode);
}
