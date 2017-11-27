package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import hu.marazmarci.belatheblob.handlers.BoundedCamera;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.main.GameMain;

public abstract class GameState implements Disposable {

	GameStateManager gsm;
	GameMain game;
	
	SpriteBatch sb;
	BoundedCamera cam;
	OrthographicCamera hudCam;
	
	GameState(GameStateManager gsm) {
		this.gsm = gsm;
        this.game = gsm.game();
        this.sb = game.getSpriteBatch();
        this.cam = game.getCamera();
        this.hudCam = game.getHUDCamera();
	}
	
	public abstract void handleInput(float dt);
	public abstract void update(float dt);
	public abstract void render();

	@Override
	public abstract void dispose();
	
}
