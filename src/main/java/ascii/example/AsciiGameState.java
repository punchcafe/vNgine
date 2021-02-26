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

    @Override
    public int getIntegerProperty(String property) {
        return 0;
    }

    @Override
    public String getClassificationProperty(String propertyName) {
        return null;
    }

    @Override
    public boolean getBooleanProperty(String propertyName) {
        return false;
    }

    @Override
    public void changeIntegerPropertyBy(String property, int changeValue) {

    }

    @Override
    public void setBooleanProperty(String property, boolean value) {

    }

    @Override
    public void setClassificationProperty(String property, String value) {

    }
}
