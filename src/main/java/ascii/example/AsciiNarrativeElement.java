package ascii.example;

import dev.punchcafe.vngine.old.NarrativeElement;

import java.util.Collection;
import java.util.List;

public class AsciiNarrativeElement implements NarrativeElement {

    private String playerName;
    private String message;

    public AsciiNarrativeElement(final String playerName, final String message){
        this.playerName = playerName;
        this.message = message;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public String getMessage(){
        return this.message;
    }

    @Override
    public Collection<Object> getAllProps() {
        return List.of(playerName, message);
    }
}
