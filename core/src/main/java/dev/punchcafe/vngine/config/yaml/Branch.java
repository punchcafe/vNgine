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
    public static Branch mapFromPomBranch(final dev.punchcafe.vngine.pom.model.Branch pomBranch ){
        return Branch.builder()
                .nodeId(pomBranch.getNodeId())
                .predicateExpression(pomBranch.getPredicateExpression())
                .prompt(pomBranch.getPrompt())
                .build();
    }
    @JsonProperty("node-id")
    private String nodeId;
    // Use for either automatic or player based since it's just a yaml DTO
    @JsonProperty("predicate-expression")
    private String predicateExpression;
    private String prompt;
}
