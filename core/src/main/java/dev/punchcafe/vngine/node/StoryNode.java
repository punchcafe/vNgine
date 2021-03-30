package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class StoryNode implements Node {
    private final String id;
    private final NodeGameStateChange nodeGameStateChange;
    private String narrativeId;
    @Setter
    private NextNodeStrategy nextNodeStrategy;

    public Node getNextNode(){
        return nextNodeStrategy.getNextNode();
    }
}
