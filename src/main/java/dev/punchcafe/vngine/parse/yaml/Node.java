package dev.punchcafe.vngine.parse.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class Node {
    private String id;
    private NodeType type;
    @JsonProperty("game-state-modifiers")
    List<String> gameStateModifiers;
    List<Branch> branches;
}
