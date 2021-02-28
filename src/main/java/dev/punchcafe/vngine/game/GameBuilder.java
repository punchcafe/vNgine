package dev.punchcafe.vngine.game;

import ascii.example.AsciiPlayerObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.punchcafe.vngine.narrative.NarrativeReader;
import dev.punchcafe.vngine.narrative.NarrativeService;
import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.node.PlayerDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.StateDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.predicate.visitors.ValidatePredicateVisitor;
import dev.punchcafe.vngine.parse.GameStateModifierParser;
import dev.punchcafe.vngine.parse.GameStatePredicateParser;
import dev.punchcafe.vngine.parse.yaml.Branch;
import dev.punchcafe.vngine.parse.yaml.GameConfig;
import dev.punchcafe.vngine.parse.yaml.VariableTypes;
import dev.punchcafe.vngine.player.PlayerObserver;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

@Setter
@Getter
//TODO: implement builder pattern
public class GameBuilder {
    private NarrativeService narrativeService;
    private NarrativeReader narrativeReader;
    private PlayerObserver playerObserver;
    private File nodeConfigurationFile;

    public Game build(){
        final var mapper = new ObjectMapper(new YAMLFactory());
        final GameConfig config;
        try {
            config = mapper.readValue(nodeConfigurationFile, GameConfig.class);
        } catch (Exception ex){
            throw new RuntimeException();
        }

        // Configure game state
        final Map<VariableTypes, List<Map.Entry<String,VariableTypes>>> gameStateVariableMap =
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
        //TODO: classifications
        final var gameState = new GameState(integerVariableNames, booleanVariableNames, null);

        final var initialModelNodes = config.getNodes().stream()
                .map(this::convertToNodeWithoutLinks)
                .collect(toMap(Node::getId, identity()));
        for (final var yamlNode : config.getNodes()) {
            final var modelNode = initialModelNodes.get(yamlNode.getId());
            switch (yamlNode.getType()) {
                case PLAYER:
                    modelNode.setNextNodeStrategy(convertPlayerDeterminedBranches(
                            ofNullable(yamlNode.getBranches()).orElse(List.of()),
                            playerObserver,
                            initialModelNodes));
                    break;
                case AUTOMATIC:
                    modelNode.setNextNodeStrategy(convertStateDeterminedBranches(
                            ofNullable(yamlNode.getBranches()).orElse(List.of()),
                            gameState,
                            initialModelNodes));
                    break;
                default:
                    throw new RuntimeException();
            }
        }

        final var predicateValidationVisitor = new ValidatePredicateVisitor(gameState);

        for(var node : initialModelNodes.values()){
            final var nextNodeStrategy = node.getNextNodeStrategy();
            if(nextNodeStrategy instanceof StateDeterminedNextNodeStrategy){
                final var castNode = (StateDeterminedNextNodeStrategy) nextNodeStrategy;
                final var nodeValidations = castNode.getBranches().stream()
                        .map(StateDeterminedNextNodeStrategy.Branch::getPredicate)
                        .map(gameStatePredicate -> gameStatePredicate.acceptVisitor(predicateValidationVisitor))
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
                if(!nodeValidations.isEmpty()){
                    throw new RuntimeException();
                }
            }
        }

        final var firstNode = initialModelNodes.get(config.getNodes().get(0).getId());
        System.out.println("Done!");
        return Game.builder()
                .gameState(gameState)
                .firstNode(firstNode)
                .narrativeReader(narrativeReader)
                .narrativeService(narrativeService)
                .build();
    }

    private Node convertToNodeWithoutLinks(final dev.punchcafe.vngine.parse.yaml.Node node) {
        return Node.builder()
                .id(node.getId())
                .narrativeId(node.getNarrativeId())
                .nodeGameStateChange(GameStateModifierParser.parse(node.getGameStateModifiers()))
                .build();
    }

    private PlayerDeterminedNextNodeStrategy convertPlayerDeterminedBranches(final List<Branch> branches,
                                                                                    final PlayerObserver playerObserver,
                                                                                    final Map<String, Node> nodeCache) {
        return PlayerDeterminedNextNodeStrategy
                .builder()
                .playerObserver(playerObserver)
                .branches(branches.stream().map(branch ->
                        PlayerDeterminedNextNodeStrategy.Branch.builder()
                                .node(ofNullable(nodeCache.get(branch.getNodeId())).orElseThrow())
                                .prompt(branch.getPrompt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private StateDeterminedNextNodeStrategy convertStateDeterminedBranches(final List<Branch> branches,
                                                                                  final GameState gameState,
                                                                                  final Map<String, Node> nodeCache) {
        return StateDeterminedNextNodeStrategy
                .builder()
                .gameState(gameState)
                .branches(branches.stream().map(branch ->
                        StateDeterminedNextNodeStrategy.Branch.builder()
                                .node(ofNullable(nodeCache.get(branch.getNodeId())).orElseThrow())
                                .predicate(GameStatePredicateParser.parsePredicate(branch.getPredicateExpression()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
