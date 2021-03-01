package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.game.GameState;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class ChangeIntegerProperty implements GameStateModification {

    private final int valueChange;
    private final String propertyName;


    @Override
    public void modify(GameState gameState) {
        gameState.changeIntegerPropertyBy(propertyName, valueChange);
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitChangeIntegerProperty(this);
    }
}
