package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.state.GameState;
import dev.punchcafe.vngine.state.StateContainer;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ValidationVisitor implements GameStateModificationVisitor<List<String>> {

    private final GameState gameState;

    @Override
    public List<String> visitChangeIntegerProperty(final ChangeIntegerProperty changeIntegerProperty) {
        if(!gameState.doesIntegerPropertyExistWithLevel(changeIntegerProperty.getPropertyName(),
                changeIntegerProperty.getStateLevel())){
            return List.of(String.format("INT property %s of level %s does not exist",
                    changeIntegerProperty.getPropertyName(),
                    changeIntegerProperty.getStateLevel()));
        }
        return List.of();
    }

    public List<String> visitSetBooleanProperty(final SetBooleanProperty changeBooleanProperty) {
        if(!gameState.doesBooleanPropertyExistWithLevel(changeBooleanProperty.getPropertyName(),
                changeBooleanProperty.getStateLevel())){
            return List.of(String.format("BOOL property %s of level %s does not exist",
                    changeBooleanProperty.getPropertyName(),
                    changeBooleanProperty.getStateLevel()));
        }
        return List.of();
    }

    public List<String> visitSetStringProperty(final SetStringProperty setStringProperty) {
        if(!gameState.doesStringPropertyExistWithLevel(setStringProperty.getPropertyName(),
                setStringProperty.getStateLevel())){
            return List.of(String.format("STR property %s of level %s does not exist",
                    setStringProperty.getPropertyName(),
                    setStringProperty.getStateLevel()));
        }
        return List.of();
    }

    @Override
    public List<String> visitChangeChapterState(ChangeChapterState changeChapterState) {
        throw new UnsupportedOperationException();
    }
};