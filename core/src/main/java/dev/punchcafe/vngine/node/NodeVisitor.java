package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.chapter.ChapterNode;

public interface NodeVisitor<T> {

    T visitStoryNode(final StoryNode storyNode);

    T visitChapterNode(final ChapterNode chapterNode);

    T visitLoadGameNode(final LoadGameNode loadGameNode);
}
