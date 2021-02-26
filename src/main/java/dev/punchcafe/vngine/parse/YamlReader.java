package dev.punchcafe.vngine.parse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.node.PlayerDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.StateDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.parse.yaml.Branch;
import dev.punchcafe.vngine.parse.yaml.GameConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class YamlReader {
    public static void main(String[] args) throws IOException {
        final var mapper = new ObjectMapper(new YAMLFactory());
        final var config = mapper.readValue(new File("src/main/resources/game-config/chapter_1.yaml"), GameConfig.class);
        final var initialModelNodes = config.getNodes().stream()
                .map(YamlReader::convertToNodeWithoutLinks)
                .collect(toMap(Node::getId, identity()));
        for (final var yamlNode : config.getNodes()) {
            final var modelNode = initialModelNodes.get(yamlNode.getId());
            switch (yamlNode.getType()) {
                case PLAYER:
                    modelNode.setNextNodeStrategy(convertPlayerDeterminedBranches(
                            ofNullable(yamlNode.getBranches()).orElse(List.of()),
                            initialModelNodes));
                    break;
                case AUTOMATIC:
                    modelNode.setNextNodeStrategy(convertStateDeterminedBranches(
                            ofNullable(yamlNode.getBranches()).orElse(List.of()),
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
                .nodeGameStateChange(GameStateModifierParser.parse(node.getGameStateModifiers()))
                .build();
    }

    private static PlayerDeterminedNextNodeStrategy convertPlayerDeterminedBranches(final List<Branch> branches,
                                                                                    final Map<String, Node> nodeCache) {
        return PlayerDeterminedNextNodeStrategy
                .builder()
                .branches(branches.stream().map(branch ->
                        PlayerDeterminedNextNodeStrategy.Branch.builder()
                                .node(ofNullable(nodeCache.get(branch.getNodeId())).orElseThrow())
                                .prompt(branch.getPrompt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private static StateDeterminedNextNodeStrategy convertStateDeterminedBranches(final List<Branch> branches,
                                                                                  final Map<String, Node> nodeCache) {
        return StateDeterminedNextNodeStrategy
                .builder()
                .branches(branches.stream().map(branch ->
                        StateDeterminedNextNodeStrategy.Branch.builder()
                                .node(ofNullable(nodeCache.get(branch.getNodeId())).orElseThrow())
                                .predicate(ExpressionParser.parsePredicate(branch.getPredicateExpression()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
