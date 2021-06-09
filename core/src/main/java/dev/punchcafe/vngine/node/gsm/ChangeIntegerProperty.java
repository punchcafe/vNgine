package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class ChangeIntegerProperty implements GameStateModification {

    private final int valueChange;
    private final String propertyName;
    private final StateLevel stateLevel;

    @Override
    public void modify(GameState gameState) {
        switch (stateLevel) {
            case GAME:
                gameState.changeIntegerPropertyBy(propertyName, valueChange);
                break;
            case CHAPTER:
                gameState.getChapterState().changeIntegerPropertyBy(propertyName, valueChange);
                break;
        }
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitChangeIntegerProperty(this);
    }
}
