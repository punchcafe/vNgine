package dev.punchcafe.vngine.save;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NodeIdentifier {
    private String nodeId;
    private String chapterId;
}
