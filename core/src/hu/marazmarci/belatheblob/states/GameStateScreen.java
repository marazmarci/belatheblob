package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_TODO;
import hu.marazmarci.belatheblob.handlers.BoundedCamera;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.main.GameMain;

@Prog3HF
public abstract class GameStateScreen implements Disposable {

    protected final GameStateManager gsm;
    protected final GameMain game;
	
	protected SpriteBatch sb;
    protected ShapeRenderer shapeRenderer;
    protected OrthographicCamera hudCam;

    protected GameInputHandler input;

	
	GameStateScreen(GameStateManager gsm) {
		this.gsm = gsm;
        this.game = gsm.getGameMain();
        this.sb = game.getSpriteBatch();
        this.shapeRenderer = game.getShapeRenderer();
        this.hudCam = game.getHUDCamera();
        this.input = createGameInputHandler();
	}


    public void handleInputAndUpdate(float deltaTime) {
        input.handleInput();
        update(deltaTime);
    }

    public void activate() {
        System.out.println("[GameStateScreen activated] : " + this);
	    onCreate();
    }


    //protected abstract void handleInput();
    protected abstract void update(float dt);
	public abstract void render();
    public abstract void onCreate();
	@Override
	public abstract void dispose();
	public abstract boolean isTransparent();
    public abstract void handleResize(int w, int h);
    protected abstract GameInputHandler createGameInputHandler();

    public GameInputHandler getGameInputHandler() {
        return input;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
