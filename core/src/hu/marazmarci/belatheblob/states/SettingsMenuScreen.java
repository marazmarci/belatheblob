package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.Color;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_TODO;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.handlers.Difficulty;
import hu.marazmarci.belatheblob.handlers.GameStateManager;

@Prog3HF
public class SettingsMenuScreen extends MenuScreen {

    @Prog3HF_TODO
    public SettingsMenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        Button bunnyButton = new Button(gsm, 100,200,400,50,"Bunnies", Color.PINK, new Runnable() {
            public void run() {
                Difficulty difficulty = gsm.getDifficulty();
                int index = difficulty.ordinal() + 1;
                if (index == Difficulty.values().length) index = 0;
                Difficulty newDifficulty = Difficulty.values()[index];
                gsm.setDifficulty(newDifficulty);
            }
        }) {
            @Override
            public Color getColor() {
                return gsm.getDifficulty() == Difficulty.EASY ? Color.GREEN : Color.PINK;
            }
        };

        addGuiElement(bunnyButton);

        //TODO settings buttons

    }

    @Override
    public void render() {

        super.render();
    }
}
