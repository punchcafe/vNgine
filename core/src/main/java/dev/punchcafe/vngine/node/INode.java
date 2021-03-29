package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public interface INode {
    String getId();
    NodeGameStateChange getNodeGameStateChange();
    String getNarrativeId();
    INode getNextNode();
}
