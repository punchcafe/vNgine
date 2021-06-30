package dev.punchcafe.vngine.pom.model.vngsl.bifunction;

import dev.punchcafe.vngine.pom.model.vngsl.PredicateExpression;
import dev.punchcafe.vngine.pom.model.vngsl.variable.integer.IntegerVariable;
import dev.punchcafe.vngine.pom.model.vngsl.variable.string.StringVariable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StringBiFunction implements PredicateExpression {

    enum Operation{
        IS, ISNT
    }

    public static StringBiFunction is(final StringVariable lhs, final StringVariable rhs){
        return new StringBiFunction(lhs, rhs, Operation.IS);
    }

    public static StringBiFunction isnt(final StringVariable lhs, final StringVariable rhs){
        return new StringBiFunction(lhs, rhs, Operation.ISNT);
    }

    private final StringVariable lhs;
    private final StringVariable rhs;
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
