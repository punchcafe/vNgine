package dev.punchcafe.vngine.parse.narrative;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NarrativeConfig {

    @JsonProperty("narrative")
    private List<Narrative> narrative;
}
