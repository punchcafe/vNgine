package dev.punchcafe.vngine.game.save;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeIdentifier {
    private String nodeId;
    private String chapterId;
}
