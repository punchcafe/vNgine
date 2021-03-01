package dev.punchcafe.vngine.node.gsm;

import dev.punchcafe.vngine.game.GameState;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
public class ValidationVisitor implements GameStateModificationVisitor<List<String>> {

    private final GameState gameState;

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
};