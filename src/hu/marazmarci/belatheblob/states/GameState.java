package hu.marazmarci.belatheblob.states;

import hu.marazmarci.belatheblob.handlers.BoundedCamera;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.main.Game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {

	protected GameStateManager gsm;
	protected Game game;
	
	protected SpriteBatch sb;
	protected BoundedCamera cam;
	protected OrthographicCamera hudCam;
	
	protected GameState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.game();
		sb = game.getSpriteBatch();
		cam = game.getCamera();
		hudCam = game.getHUDCamera();
	}
	
	public abstract void handleInput(float dt);
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
	
}
