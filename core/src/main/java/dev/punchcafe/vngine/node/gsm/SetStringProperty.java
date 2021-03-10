package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.game.GameState;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class SetStringProperty implements GameStateModification {
    final private String propertyName;
    final private String propertyValue;

    @Override
    public void modify(GameState gameState) {
        gameState.setClassificationProperty(propertyName, propertyValue);
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitSetStringProperty(this);
    }
}