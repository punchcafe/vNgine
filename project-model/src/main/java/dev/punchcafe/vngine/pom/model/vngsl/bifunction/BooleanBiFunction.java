package dev.punchcafe.vngine.pom.model.vngsl.bifunction;

import dev.punchcafe.vngine.pom.model.vngsl.PredicateExpression;
import dev.punchcafe.vngine.pom.model.vngsl.variable.bool.BooleanVariable;
import dev.punchcafe.vngine.pom.model.vngsl.variable.string.StringVariable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanBiFunction implements PredicateExpression {

    enum Operation{
        IS, ISNT
    }

    public static BooleanBiFunction is(final BooleanVariable lhs, final BooleanVariable rhs){
        return new BooleanBiFunction(lhs, rhs, Operation.IS);
    }

    public static BooleanBiFunction isnt(final BooleanVariable lhs, final BooleanVariable rhs){
        return new BooleanBiFunction(lhs, rhs, Operation.ISNT);
    }

    private final BooleanVariable lhs;
    private final BooleanVariable rhs;
    private final Operation operation;

    @Override
    public String asVngQL() {
        switch (this.operation) {
            case IS:
                return lhs.asVngQL() + " is " + rhs.asVngQL();
            case ISNT:
                return lhs.asVngQL() + " isn't " + rhs.asVngQL();
        }
        return null;
    }
}