package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.Color;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.states.levels.Level1;

/**
 * A főmenüt reprezentáló osztály.
 */
@Prog3HF
public class MainMenuScreen extends MenuScreen {

    /**
     * Létrehozza a főmenüt, és elhelyezi a gombokat.
     * @param gameStateManager a játékállapot-kezelő
     */
    public MainMenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        Button playButton = new Button(gsm, 100,100,400,50,"Start Game", Color.GREEN, new Runnable() {
            public void run() {
                gsm.pushState(new Level1(gsm));
            }
        });

        Button settingsButton = new Button(gsm, 100,200,400,50,"Settings", Color.RED, new Runnable() {
            public void run() {
                gsm.pushState(new SettingsMenuScreen(gsm));
            }
        });

        addGuiElement(playButton);
        addGuiElement(settingsButton);
    }


}
