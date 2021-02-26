package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Node {
    private final String id;
    private final NodeGameStateChange nodeGameStateChange;
    @Setter
    private NextNodeStrategy nextNodeStrategy;
}
