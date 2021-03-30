package dev.punchcafe.vngine.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.punchcafe.vngine.chapter.Chapter;
import dev.punchcafe.vngine.chapter.ChapterBuilder;
import dev.punchcafe.vngine.narrative.NarrativeReader;
import dev.punchcafe.vngine.narrative.NarrativeService;
import dev.punchcafe.vngine.node.StoryNode;
import dev.punchcafe.vngine.node.PlayerDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.StateDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.gsm.ValidationVisitor;
import dev.punchcafe.vngine.node.predicate.visitors.ValidatePredicateVisitor;
import dev.punchcafe.vngine.parse.GameStateModifierParser;
import dev.punchcafe.vngine.parse.GameStatePredicateParser;
import dev.punchcafe.vngine.parse.yaml.Branch;
import dev.punchcafe.vngine.parse.yaml.ChapterConfig;
import dev.punchcafe.vngine.parse.yaml.GameConfig;
import dev.punchcafe.vngine.parse.yaml.VariableTypes;
import dev.punchcafe.vngine.player.PlayerObserver;
import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Setter
@Getter
public class GameBuilder<N> {
    private NarrativeService<N> narrativeService;
    private NarrativeReader<N> narrativeReader;
    private PlayerObserver playerObserver;
    private File nodeConfigurationFile;

    public Game<N> build() {
        final var mapper = new ObjectMapper(new YAMLFactory());
        final GameConfig config;
        try {
            config = mapper.readValue(nodeConfigurationFile, GameConfig.class);
        } catch (Exception ex) {
            throw new RuntimeException();
        }

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

        final var chapterBuilder = ChapterBuilder.<N>builder()
                .gameState(gameState)
                .narrativeReader(narrativeReader)
                .narrativeService(narrativeService)
                .playerObserver(playerObserver)
                .chapterConfigCache(config.getChapters().stream().collect(toMap(ChapterConfig::getChapterId, identity())))
                .build();


        //TODO: make this customisable
        final var firstChapter = new Chapter(config.getChapters().get(0), chapterBuilder);

        return Game.<N>builder()
                .gameState(gameState)
                .firstNode(firstChapter)
                .narrativeReader(narrativeReader)
                .narrativeService(narrativeService)
                .build();
    }
}
