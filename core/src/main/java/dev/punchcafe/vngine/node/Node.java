package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;

public interface Node {
    String getId();
    NodeGameStateChange getNodeGameStateChange();
    String getNarrativeId();
    Node getNextNode();
}
