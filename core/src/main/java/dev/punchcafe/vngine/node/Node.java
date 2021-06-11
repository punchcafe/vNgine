package dev.punchcafe.vngine.node;

import dev.punchcafe.vngine.node.gsm.NodeGameStateChange;

public interface Node {
    String getId();
    NodeGameStateChange getNodeGameStateChange();
    //TODO: make optional
    String getNarrativeId();
    Node getNextNode();
}
