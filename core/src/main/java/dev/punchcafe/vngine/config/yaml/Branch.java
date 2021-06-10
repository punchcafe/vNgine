package dev.punchcafe.vngine.config.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Branch {
    private String nodeId;
    // Use for either automatic or player based since it's just a yaml DTO
    private String predicateExpression;
    private String prompt;
}
