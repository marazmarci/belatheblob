package hu.marazmarci.belatheblob.states;

import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.handlers.GameStateManager;

/**
 * Egy olyan játékállapotot reprezentál, ami konkrétan már egy játékpálya.
 */
@Prog3HF
public abstract class GameLevelScreen extends GameStateScreen {

    // TODO level-specific attributes

    public GameLevelScreen(GameStateManager gsm) {
        super(gsm);
    }


    // TODO level-specific methods

}
