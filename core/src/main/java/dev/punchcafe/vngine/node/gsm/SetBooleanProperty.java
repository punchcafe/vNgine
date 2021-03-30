package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class SetBooleanProperty implements GameStateModification {
    final private String propertyName;
    final private boolean booleanValue;

    @Override
    public void modify(GameState gameState) {
        gameState.setBooleanProperty(propertyName, booleanValue);
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitSetBooleanProperty(this);
    }
}
