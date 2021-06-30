package dev.punchcafe.vngine.pom.model.vngsl.composite;

import dev.punchcafe.vngine.pom.model.vngsl.PredicateExpression;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PredicateLink {

    public static PredicateLink firstLink(@NonNull final PredicateExpression expression){
        return new PredicateLink(null, expression);
    }

    public static PredicateLink newLink(@NonNull final PredicateExpression expression,
                                        @NonNull final AndOrOperation operation){
        return new PredicateLink(operation, expression);
    }

    private final AndOrOperation operation;
    private final PredicateExpression predicateExpression;

    public Optional<AndOrOperation> connectionOperation(){
        return Optional.ofNullable(operation);
    }
}
