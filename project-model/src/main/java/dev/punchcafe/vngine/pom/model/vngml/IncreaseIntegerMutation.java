package dev.punchcafe.vngine.pom.model.vngml;

import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerGameVariable;
import dev.punchcafe.vngine.pom.model.vngpl.variable.integer.IntegerLiteral;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncreaseIntegerMutation implements GameStateMutationExpression {
    private IntegerGameVariable variableToModify;
    private IntegerLiteral increaseBy;

    @Override
    public <T> T acceptVisitor(GameStateMutationExpressionVisitor<T> visitor) {
        return visitor.visitIncreaseIntegerMutation(this);
    }

    @Override
    public String asVngQL() {
        return "increase " + variableToModify.asVngQL() + " by " + increaseBy.asVngQL();
    }
}
