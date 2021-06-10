package dev.punchcafe.vngine.game;

import dev.punchcafe.vngine.chapter.ChapterBuilder;
import dev.punchcafe.vngine.chapter.ChapterConfigCache;
import dev.punchcafe.vngine.config.PomConversionUtils;
import dev.punchcafe.vngine.config.yaml.ChapterConfig;
import dev.punchcafe.vngine.config.yaml.GameConfig;
import dev.punchcafe.vngine.config.yaml.VariableTypes;
import dev.punchcafe.vngine.player.PlayerObserver;
import dev.punchcafe.vngine.pom.model.ProjectObjectModel;
import dev.punchcafe.vngine.pom.narrative.NarrativeReader;
import dev.punchcafe.vngine.pom.narrative.NarrativeService;
import dev.punchcafe.vngine.state.GameState;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Setter
@Getter
public class GameBuilder<N> {
    private NarrativeService<N> narrativeService;
    private NarrativeReader<N> narrativeReader;
    private PlayerObserver playerObserver;
    // For the game builder, once the narrative services are made we won't bother with it on the POM
    private ProjectObjectModel<?> projectObjectModel;

    public Game<N> build() {
        final GameConfig config = PomConversionUtils.parseFromPom(projectObjectModel);

        // Configure game state
        final Map<VariableTypes, List<Map.Entry<String, VariableTypes>>> gameStateVariableMap =
                config.getGameStateVariables().entrySet().stream().collect(groupingBy(Map.Entry::getValue));

        final List<String> integerVariableNames = ofNullable(gameStateVariableMap.get(VariableTypes.INT))
                .orElse(List.of())
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        final List<String> booleanVariableNames = ofNullable(gameStateVariableMap.get(VariableTypes.BOOL))
                .orElse(List.of())
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        final List<String> stringVariableNames = ofNullable(gameStateVariableMap.get(VariableTypes.STR))
                .orElse(List.of())
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        final var gameState = new GameState(integerVariableNames, booleanVariableNames, stringVariableNames);

        final var chapterConfigCache = ChapterConfigCache.builder()
                .chapterConfigMap(config.getChapters().stream().collect(toMap(ChapterConfig::getChapterId, identity())))
                .firstChapter(config.getFirstChapter())
                .build();

        final var chapterBuilder = ChapterBuilder.<N>builder()
                .gameState(gameState)
                .narrativeReader(narrativeReader)
                .narrativeService(narrativeService)
                .playerObserver(playerObserver)
                .chapterConfigCache(chapterConfigCache)
                .build();

        return Game.<N>builder()
                .gameState(gameState)
                .narrativeReader(narrativeReader)
                .narrativeService(narrativeService)
                .chapterBuilder(chapterBuilder)
                .chapterConfigCache(chapterConfigCache)
                .build();
    }
}
