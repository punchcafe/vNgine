package ascii.example;

import dev.punchcafe.vngine.old.GameController;

public class AsciiGamePlayer {
    public static void main(String[] args){
        final var gameAdaptor = new AsciiGameAdaptor();
        final var game = new GameController(gameAdaptor);
        game.play();
    }
}
