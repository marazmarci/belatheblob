package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF_TODO;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.main.GameMain;

@Prog3HF_TODO
public class GameOverScreen extends GameStateScreen {


    public GameOverScreen(GameStateManager gsm) {
        super(gsm);

    }

    @Override
    protected void update(float dt) {

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
    public void onCreate() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public void handleResize(int w, int h) {

    }

    @Override
    protected GameInputHandler createGameInputHandler() {
        return new GameOverInput();
    }


    class GameOverInput extends GameInputHandler {

        @Override
        protected boolean handleKeyDown(int keyCode) {
            return false;
        }

        @Override
        protected void handleKeyUp(int keyCode) {

        }

        @Override
        protected void handleKeysHeld(IntArray heldKeys) {

        }

        @Override
        protected void handleTouchDown(Vector3 touchPoint) {

        }

        @Override
        protected void handleTouchUp(Vector3 touchPoint) {

        }

        @Override
        protected void handleTouchHeld(IntMap<Vector3> actualTouchPoints) {

        }

        @Override
        protected void handleTouchDragged(Vector3 touchPoint) {

        }
    }

}
