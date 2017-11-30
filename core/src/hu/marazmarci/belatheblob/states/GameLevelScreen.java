package hu.marazmarci.belatheblob.states;

import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.handlers.GameStateManager;

/**
 * Egy olyan játékállapotot reprezentál, ami egy játszható pálya.
 */
@Prog3HF
public abstract class GameLevelScreen extends GameStateScreen {

    // ennek az elkészítéséhez túl sokat kellett volna a Level1 osztályon alakítani
    // későbbi fejlesztéseknél megcsinálom :)

    // TODO level-specific attributes

    public GameLevelScreen(GameStateManager gsm) {
        super(gsm);
    }


    // TODO level-specific methods

}
