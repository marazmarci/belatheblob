package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.main.GameMain;


/**
 * A game over játékállapot képernyőjét reprezentáló osztály.
 * Egy piros félig átlátszó réteget fog rakni a képernyőre, ha majd megírom teljesen.
 */
@Prog3HF
public class GameOverScreen extends MenuScreen {


    public GameOverScreen(GameStateManager gsm) {
        super(gsm);
        //TODO button(s)
    }

    @Override
    public void render() {

        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1f, 0, 0, 0.5f);
        shapeRenderer.rect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);

        Gdx.gl20.glDisable(GL20.GL_BLEND);

    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    protected GameInputHandler createGameInputHandler() {
        return new GameOverInput();
    }


    @Prog3HF
    class GameOverInput extends MenuScreenInputHandler {

        @Override
        protected boolean handleKeyDown(int keyCode) {
            if (keyCode == Input.Keys.SPACE) {
                // TODO if (3 secs elapsed)
                gsm.popState();
            }
            return false;
        }
    }

}