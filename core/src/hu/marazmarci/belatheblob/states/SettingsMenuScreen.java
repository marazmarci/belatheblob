package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.Color;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;
import hu.marazmarci.belatheblob.Prog3HF_TODO;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.gui.ButtonAction;
import hu.marazmarci.belatheblob.handlers.Difficulty;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.main.GameMain;

@Prog3HF
@Prog3HF_TODO
@Prog3HF_JavaDoc_TODO
public class SettingsMenuScreen extends MenuScreen {

    @Prog3HF_TODO
    @Prog3HF_JavaDoc_TODO
    public SettingsMenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        Button bunnyButton = new Button(gsm, 100,200,400,50,"Bunnies", Color.GREEN, new ButtonAction() {
            public void run(Button btn) {
                GameMain gameMain = gsm.getGameMain();
                Difficulty diff = gameMain.getDifficulty();
                int index = diff.ordinal() + 1;
                if (index == Difficulty.values().length) index = 0;
                Difficulty newDiff = Difficulty.values()[index];
                gameMain.setDifficulty(newDiff);
                btn.setColor(newDiff == Difficulty.EASY ? Color.GREEN : Color.PINK);
            }
        });

        addGuiElement(bunnyButton);

        //TODO settings buttons

    }

}
