package hu.marazmarci.belatheblob.states.menu;

import com.badlogic.gdx.graphics.Color;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.handlers.Difficulty;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.states.levels.Level1;

import java.io.File;

/**
 * A beállítások menüjét reprezentáló osztály
 */
@Prog3HF
public class SettingsMenuScreen extends MenuScreen {

    public SettingsMenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        Button bunnyButton = new Button(this, 110,200,400,50,-20,"Difficulty", Color.GREEN, new Runnable() {
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
                return game.getDifficulty() == Difficulty.EASY ? Color.GREEN : Color.BLUE;
            }
            @Override
            public String getText() {
                return "Difficulty: " + game.getDifficulty().toString();
            }
        };


        Button resetButton = new Button(this, 110,100,400,50,-10,"Reset spawn", Color.RED, new Runnable() {
            @Override
            public void run() {
                try {
                    new File(Level1.positionSaveFilePath).delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addGuiElement(bunnyButton);
        addGuiElement(resetButton);

        //TODO settings buttons

    }

}
