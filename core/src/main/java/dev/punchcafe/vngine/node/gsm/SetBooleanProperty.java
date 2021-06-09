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
    private final String propertyName;
    private final boolean booleanValue;
    private final StateLevel stateLevel;

    @Override
    public void modify(GameState gameState) {
        switch (stateLevel) {
            case GAME:
                gameState.setBooleanProperty(propertyName, booleanValue);
                break;
            case CHAPTER:
                gameState.getChapterState().setBooleanProperty(propertyName, booleanValue);
                break;
        }
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitSetBooleanProperty(this);
    }
}
