package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.chapter.ChapterBuilder;
import dev.punchcafe.vngine.chapter.ChapterConfigCache;
import dev.punchcafe.vngine.save.GameSave;
import dev.punchcafe.vngine.node.gsm.LoadSavedGameState;
import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;
import lombok.Builder;

import java.util.List;

@Builder
public class LoadGameNode implements Node {
    private final ChapterBuilder<?> chapterBuilder;
    private final ChapterConfigCache chapterConfigCache;
    private final GameSave gameSave;

    @Override
    public String getId() {
        return "LOAD_GAME_NODE";
    }

    @Override
    public NodeGameStateChange getNodeGameStateChange() {
        final var loadGameState = LoadSavedGameState.builder()
                .savedGameState(gameSave.getSavedGameState())
                .chapterConfigOfSave(chapterConfigCache.get(gameSave.getNodeIdentifier().getChapterId()).orElseThrow())
                .build();
        return new NodeGameStateChange(List.of(loadGameState));
    }

    @Override
    public String getNarrativeId() {
        return null;
    }

    @Override
    public Node getNextNode() {
        // TODO: if no node id, use chapter
        return chapterBuilder.buildChapterAndStartAtNode(gameSave.getNodeIdentifier().getChapterId(),
                gameSave.getNodeIdentifier().getNodeId());
    }

    @Override
    public <T> T acceptVisitor(NodeVisitor<T> visitor) {
        return visitor.visitLoadGameNode(this);
    }
}
