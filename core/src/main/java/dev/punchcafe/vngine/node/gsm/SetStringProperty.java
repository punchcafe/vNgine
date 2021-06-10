package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class SetStringProperty implements GameStateModification {
    private final String propertyName;
    private final String propertyValue;
    private final StateLevel stateLevel;

    @Override
    public void modify(GameState gameState) {
        switch (stateLevel){
            case GAME:
                gameState.setStringProperty(propertyName, propertyValue);
                break;
            case CHAPTER:
                gameState.getChapterState().setStringProperty(propertyName, propertyValue);
                break;
        }
    }

    @Override
    public <T> T acceptVisitor(GameStateModificationVisitor<T> visitor) {
        return visitor.visitSetStringProperty(this);
    }
}
