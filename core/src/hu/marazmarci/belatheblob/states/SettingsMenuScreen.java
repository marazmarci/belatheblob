package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.Color;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_TODO;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.handlers.Difficulty;
import hu.marazmarci.belatheblob.handlers.GameStateManager;

/**
 * A beállítások menüjét reprezentáló osztály
 */
@Prog3HF
@Prog3HF_TODO
public class SettingsMenuScreen extends MenuScreen {

    @Prog3HF_TODO
    public SettingsMenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        Button bunnyButton = new Button(gsm, 100,200,400,50,"Bunnies", Color.GREEN, new Runnable() {
            @Override
            public void run() {
                Difficulty diff = game.getDifficulty();
                int index = diff.ordinal() + 1;
                if (index == Difficulty.values().length) index = 0;
                Difficulty newDiff = Difficulty.values()[index];
                game.setDifficulty(newDiff);
            }
        }) {
            @Override
            public Color getColor() {
                return game.getDifficulty() == Difficulty.EASY ? Color.GREEN : Color.PINK;
            }
        };

        addGuiElement(bunnyButton);

        //TODO settings buttons

    }

}
