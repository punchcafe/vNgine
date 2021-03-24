package dev.punchcafe.vngine.parse.narrative;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Narrative {

    @JsonProperty("id")
    private String narrativeId;
    @JsonProperty("name")
    private String characterName;
    @JsonProperty("dialogue")
    private String dialogue;
}
