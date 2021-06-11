package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.chapter.Chapter;
import dev.punchcafe.vngine.chapter.ChapterBuilder;
import dev.punchcafe.vngine.chapter.ChapterConfigCache;
import dev.punchcafe.vngine.game.save.CorruptGameSave;
import dev.punchcafe.vngine.game.save.GameSave;
import dev.punchcafe.vngine.node.LoadGameNode;
import dev.punchcafe.vngine.pom.narrative.NarrativeReader;
import dev.punchcafe.vngine.pom.narrative.NarrativeService;
import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.state.GameState;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PACKAGE)
public class Game<N> {
    private final GameState gameState;
    private final ChapterBuilder<N> chapterBuilder;
    private final ChapterConfigCache chapterConfigCache;
    private final NarrativeService<N> narrativeService;
    private final NarrativeReader<N> narrativeReader;
    private Node currentNode;

    public Game<N> startNewGame(){
        currentNode = new Chapter(chapterConfigCache.getFirstChapter(), chapterBuilder);
        return this;
    }

    public Game<N> loadGame(final GameSave gameSave){
        currentNode = LoadGameNode.builder()
                .chapterBuilder(chapterBuilder)
                .chapterConfigCache(chapterConfigCache)
                .gameSave(gameSave)
                .build();
        return this;
    }

    public Game<N> tick() {
        currentNode.getNodeGameStateChange().modify(gameState);
        if(currentNode.getNarrativeId() != null && !currentNode.getNarrativeId().trim().equals("")){
            final var narrative = narrativeService.getNarrative(currentNode.getNarrativeId());
            narrativeReader.readNarrative(narrative);
        }
        currentNode = currentNode.getNextNode();
        return this;
    }

    public void run(){
        while(currentNode != null){
            tick();
        }
    }

    public GameSave saveGame(){
        return null;
    }


}
