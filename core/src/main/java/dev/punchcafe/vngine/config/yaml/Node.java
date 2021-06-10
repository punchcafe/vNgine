package dev.punchcafe.vngine.config.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Node {

    public static Node parseFromPomNode(final dev.punchcafe.vngine.pom.model.Node pomNode) {
        return Node.builder()
                .id(pomNode.getId())
                .narrativeId(pomNode.getNarrativeId())
                .branches(convertListOfBranches(pomNode.getBranches()))
                .gameStateModifiers(pomNode.getGameStateModifiers())
                .type(NodeType.valueOf(pomNode.getType().toString()))
                .build();
    }

    private static List<Branch> convertListOfBranches(final List<dev.punchcafe.vngine.pom.model.Branch> branches){
        if(branches == null){
            return List.of();
        }
        return branches.stream()
                .map(Branch::mapFromPomBranch)
                .collect(Collectors.toList());
    }

    private String id;
    private NodeType type;
    @JsonProperty("narrative-id")
    private String narrativeId;
    @JsonProperty("game-state-modifiers")
    private List<String> gameStateModifiers;
    private List<Branch> branches;
}
