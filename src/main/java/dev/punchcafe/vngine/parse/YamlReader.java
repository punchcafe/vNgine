package dev.punchcafe.vngine.parse;

import dev.punchcafe.vngine.player.SimplePlayerObserver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.punchcafe.vngine.game.GameState;
import dev.punchcafe.vngine.player.PlayerObserver;
import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.node.PlayerDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.StateDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.parse.yaml.Branch;
import dev.punchcafe.vngine.parse.yaml.GameConfig;
import dev.punchcafe.vngine.parse.yaml.VariableTypes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class YamlReader {
    public static void main(String[] args) throws IOException {
        final var mapper = new ObjectMapper(new YAMLFactory());
        final var config = mapper.readValue(new File("src/main/resources/game-config/chapter_1.yaml"), GameConfig.class);

        // TODO: make generic
        final PlayerObserver playerObserver = new SimplePlayerObserver();

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
                .map(YamlReader::convertToNodeWithoutLinks)
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
        System.out.println("Done!");
    }

    private static Node convertToNodeWithoutLinks(final dev.punchcafe.vngine.parse.yaml.Node node) {
        return Node.builder()
                .id(node.getId())
                .narrativeId(node.getNarrativeId())
                .nodeGameStateChange(GameStateModifierParser.parse(node.getGameStateModifiers()))
                .build();
    }

    private static PlayerDeterminedNextNodeStrategy convertPlayerDeterminedBranches(final List<Branch> branches,
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

    private static StateDeterminedNextNodeStrategy convertStateDeterminedBranches(final List<Branch> branches,
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
