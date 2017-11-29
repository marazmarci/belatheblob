package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputAdapter;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.main.GameMain;


@Prog3HF
@Prog3HF_JavaDoc_TODO
public class GameOverScreen extends MenuScreen {


    public GameOverScreen(GameStateManager gsm) {
        super(gsm);
        //TODO button
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


    class GameOverInput extends GameInputAdapter {

        //TODO implementálni

    }

}
