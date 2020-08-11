package ascii.example;

import dev.punchcafe.vngine.GameState;

public class AsciiGameState implements GameState {
    private boolean isHappy;

    public void setHappy(final boolean isHappy){
        this.isHappy = isHappy;
    }

    public boolean isHappy(){
        return this.isHappy;
    }
}
