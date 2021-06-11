package dev.punchcafe.vngine.chapter;

import dev.punchcafe.vngine.pom.narrative.NarrativeReader;
import dev.punchcafe.vngine.pom.narrative.NarrativeService;
import dev.punchcafe.vngine.node.Node;
import dev.punchcafe.vngine.node.PlayerDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.StateDeterminedNextNodeStrategy;
import dev.punchcafe.vngine.node.StoryNode;
import dev.punchcafe.vngine.node.gsm.ValidationVisitor;
import dev.punchcafe.vngine.node.predicate.visitors.ValidatePredicateVisitor;
import dev.punchcafe.vngine.config.GameStateModifierParser;
import dev.punchcafe.vngine.config.GameStatePredicateParser;
import dev.punchcafe.vngine.config.yaml.Branch;
import dev.punchcafe.vngine.config.yaml.ChapterConfig;
import dev.punchcafe.vngine.player.PlayerObserver;
import dev.punchcafe.vngine.state.GameState;
import lombok.Builder;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
 * Class responsible for being able to bootstrap a chapter config and load it into memory.
 *
 * @param <N>
 */
@Builder
public class ChapterBuilder<N> {
    private final NarrativeService<N> narrativeService;
    private final NarrativeReader<N> narrativeReader;
    private final PlayerObserver playerObserver;
    private final GameState gameState;
    private final ChapterConfigCache chapterConfigCache;

    /**
     * Build a chapter from the chapter config, and passes the first story node
     *
     * @param chapterConfig
     * @return
     */
    public Node buildChapter(final ChapterConfig chapterConfig) {
        final var nodes = buildChapterNodeCache(chapterConfig);
        return determineFirstNode(nodes, chapterConfig);
    }

    public Node buildChapterAndStartAtNode(final String chapterConfigId, final String startingNodeId) {
        final var nodes = buildChapterNodeCache(chapterConfigCache.get(chapterConfigId).get());
        return Optional.ofNullable(nodes.get(startingNodeId))
                .orElseThrow(() -> new IllegalArgumentException(String.format("No node found with ID: %s", startingNodeId)));
    }

    private Map<String, Node> buildChapterNodeCache(final ChapterConfig chapterConfig) {
        final var modelNodes = chapterConfig.getNodes().stream()
                .map(configNode -> this.convertToNodeWithoutLinks(configNode, chapterConfig.getChapterId()))
                .collect(toMap(StoryNode::getId, identity()));
        wireNodeEdges(modelNodes, chapterConfig);
        final var compileErrors = validateNodes(modelNodes.values(), gameState, narrativeService);
        if (compileErrors.size() > 0) {
            final var errorString = compileErrors.stream().collect(joining("\n"));
            throw new RuntimeException(String.format("Encountered the following errors: \n %s", errorString));
        }
        return modelNodes.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> (Node) entry.getValue()));
    }

    private Node determineFirstNode(final Map<String, Node> nodeCache, final ChapterConfig chapterConfig) {
        if (chapterConfig.getFirstNodeId() != null && !chapterConfig.getFirstNodeId().equals("")) {
            return Optional.ofNullable(nodeCache.get(chapterConfig.getFirstNodeId()))
                    .orElseThrow(() -> new NoSuchElementException("No element with that id"));
        } else {
            return Optional.ofNullable(nodeCache.get(chapterConfig.getNodes().get(0).getId()))
                    .orElseThrow(() -> new NoSuchElementException("No element with that id"));
        }
    }

    private void wireNodeEdges(final Map<String, StoryNode> nodeCache, final ChapterConfig chapterConfig) {
        for (final var yamlNode : chapterConfig.getNodes()) {
            final var modelNode = nodeCache.get(yamlNode.getId());
            switch (yamlNode.getType()) {
                case PLAYER:
                    modelNode.setNextNodeStrategy(convertPlayerDeterminedBranches(
                            ofNullable(yamlNode.getBranches()).orElse(List.of()),
                            playerObserver,
                            nodeCache));
                    break;
                case AUTOMATIC:
                    modelNode.setNextNodeStrategy(convertStateDeterminedBranches(
                            ofNullable(yamlNode.getBranches()).orElse(List.of()),
                            gameState,
                            nodeCache));
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }

    private StoryNode convertToNodeWithoutLinks(final dev.punchcafe.vngine.config.yaml.Node node,
                                                final String chapterId) {
        return StoryNode.builder()
                .id(node.getId())
                .chapterId(chapterId)
                .narrativeId(node.getNarrativeId())
                .nodeGameStateChange(GameStateModifierParser.parse(node.getGameStateModifiers()))
                .build();
    }

    private PlayerDeterminedNextNodeStrategy convertPlayerDeterminedBranches(final List<Branch> branches,
                                                                             final PlayerObserver playerObserver,
                                                                             final Map<String, StoryNode> nodeCache) {
        return PlayerDeterminedNextNodeStrategy
                .builder()
                .playerObserver(playerObserver)
                .branches(branches.stream().map(branch ->
                        PlayerDeterminedNextNodeStrategy.Branch.builder()
                                .node(retrieveNode(branch.getNodeId(), nodeCache))
                                .prompt(branch.getPrompt())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private StateDeterminedNextNodeStrategy convertStateDeterminedBranches(final List<Branch> branches,
                                                                           final GameState gameState,
                                                                           final Map<String, StoryNode> nodeCache) {
        return StateDeterminedNextNodeStrategy
                .builder()
                .gameState(gameState)
                .branches(branches.stream().map(branch ->
                        StateDeterminedNextNodeStrategy.Branch.builder()
                                .node(retrieveNode(branch.getNodeId(), nodeCache))
                                .predicate(GameStatePredicateParser.parsePredicate(branch.getPredicateExpression()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private Node retrieveNode(final String nodeId, final Map<String, StoryNode> storyNodeCache) {
        // Assume never have duplicates, and that that has been validated earlier
        final var storyNode = storyNodeCache.get(nodeId);
        if (storyNode != null) {
            return storyNode;
        }
        final var chapterNodeConfig = chapterConfigCache.get(nodeId)
                .orElseThrow(() -> new RuntimeException("node not found"));
        return new ChapterNode(chapterNodeConfig, this);
    }


    private List<String> validateNodes(final Collection<StoryNode> storyNodes,
                                       final GameState gameState,
                                       final NarrativeService narrativeService) {

        final var predicateValidationVisitor = new ValidatePredicateVisitor(gameState);
        final var stateModifierValidationVisitor = new ValidationVisitor(gameState);
        final var allNarrativeIds = new HashSet<>(narrativeService.allNarrativeIds());
        //TODO: make sure chapter exists
        //TODO: make sure node exists

        final List<String> compileErrors = new ArrayList<>();

        for (var node : storyNodes) {

            final var stateChangeValidations = node.getNodeGameStateChange().getModifications().stream()
                    .map(stateChange -> stateChange.acceptVisitor(stateModifierValidationVisitor))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

            if (stateChangeValidations.size() > 0) {
                compileErrors.add(String.format("Encountered errors with state modifications on node %s: %s",
                        node.getId(),
                        stateChangeValidations.toString()));
            }

            if (!allNarrativeIds.contains(node.getNarrativeId())) {
                compileErrors.add(String.format("Node: %s references unknown narrative: %s", node.getId(), node.getNarrativeId()));
            }

            final var nextNodeStrategy = node.getNextNodeStrategy();
            if (nextNodeStrategy instanceof StateDeterminedNextNodeStrategy) {
                final var castNode = (StateDeterminedNextNodeStrategy) nextNodeStrategy;
                final var nodeValidations = castNode.getBranches().stream()
                        .map(StateDeterminedNextNodeStrategy.Branch::getPredicate)
                        .map(gameStatePredicate -> gameStatePredicate.acceptVisitor(predicateValidationVisitor))
                        .flatMap(List::stream)
                        .collect(Collectors.toList());
                if (!nodeValidations.isEmpty()) {
                    compileErrors.add(String.format("Encountered errors with branch predicates on node %s: %s",
                            node.getId(),
                            nodeValidations.toString()));
                }
            }
        }
        return compileErrors;
    }
}
