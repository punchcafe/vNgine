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
    private String id;
    private NodeType type;
    @JsonProperty("narrative-id")
    private String narrativeId;
    @JsonProperty("game-state-modifiers")
    private List<String> gameStateModifiers;
    private List<Branch> branches;
}
