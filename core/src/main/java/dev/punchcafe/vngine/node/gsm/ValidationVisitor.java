package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.state.StateContainer;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ValidationVisitor implements GameStateModificationVisitor<List<String>> {

    private final StateContainer gameState;

    @Override
    public List<String> visitChangeIntegerProperty(final ChangeIntegerProperty changeIntegerProperty){
        if(!gameState.doesIntegerPropertyExist(changeIntegerProperty.getPropertyName())){
            return List.of(String.format("INT property %s does not exist", changeIntegerProperty.getPropertyName()));
        }
        return List.of();
    }

    public List<String> visitSetBooleanProperty(final SetBooleanProperty changeBooleanProperty){
        if(!gameState.doesBooleanPropertyExist(changeBooleanProperty.getPropertyName())){
            return List.of(String.format("BOOL property %s does not exist", changeBooleanProperty.getPropertyName()));
        }
        return List.of();
    }

    public List<String> visitSetStringProperty(final SetStringProperty changeIntegerProperty){
        return List.of();
    }

    @Override
    public List<String> visitChangeChapterState(ChangeChapterState changeChapterState) {
        throw new UnsupportedOperationException();
    }
};