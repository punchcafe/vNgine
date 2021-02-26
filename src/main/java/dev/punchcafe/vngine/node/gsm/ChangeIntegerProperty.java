package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.old.OldGameState;
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
    public void modify(OldGameState gameState) {
        gameState.changeIntegerPropertyBy(propertyName, valueChange);
    }
}
