package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.old.OldGameState;
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
    public void modify(OldGameState gameState) {
        gameState.setBooleanProperty(propertyName, booleanValue);
    }
}
