package dev.punchcafe.vngine.pom.model.vngsl.bifunction;

import dev.punchcafe.vngine.pom.model.vngsl.PredicateExpression;
import dev.punchcafe.vngine.pom.model.vngsl.variable.integer.IntegerVariable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerBiFunction implements PredicateExpression {

    enum Operation{
        LESS_THAN, MORE_THAN, EQUALS
    }

    public static IntegerBiFunction moreThan(final IntegerVariable lhs, final IntegerVariable rhs){
        return new IntegerBiFunction(lhs, rhs, Operation.MORE_THAN);
    }

    public static IntegerBiFunction lessThan(final IntegerVariable lhs, final IntegerVariable rhs){
        return new IntegerBiFunction(lhs, rhs, Operation.LESS_THAN);
    }

    public static IntegerBiFunction equals(final IntegerVariable lhs, final IntegerVariable rhs){
        return new IntegerBiFunction(lhs, rhs, Operation.EQUALS);
    }

    private final IntegerVariable lhs;
    private final IntegerVariable rhs;
    private final Operation operation;

    @Override
    public String asVngQL() {
        switch (this.operation) {
            case MORE_THAN:
                return lhs.asVngQL() + " more_than " + rhs.asVngQL();
            case LESS_THAN:
                return lhs.asVngQL() + " less_than " + rhs.asVngQL();
            case EQUALS:
                return lhs.asVngQL() + " more_than " + rhs.asVngQL();
        }
        return null;
    }
}
