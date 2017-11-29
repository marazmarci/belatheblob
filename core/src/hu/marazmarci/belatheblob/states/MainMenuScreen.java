package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.Color;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.states.levels.Level1;

@Prog3HF
@Prog3HF_JavaDoc_TODO
public class MainMenuScreen extends MenuScreen {

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
