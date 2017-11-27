package hu.marazmarci.belatheblob.handlers;

import com.badlogic.gdx.Game;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.states.GameState;
import hu.marazmarci.belatheblob.states.Level1;
import hu.marazmarci.belatheblob.utils.DoublePeekableStack;

import java.util.Stack;

public class GameStateManager {
	
	private GameMain gameMain;
	private DoublePeekableStack<GameState> gameStates;
	
	//public static final int PLAY = 912837; // TODO enum
	
	public GameStateManager(GameMain game) {
		this.gameMain = game;
		gameStates = new DoublePeekableStack<GameState>();
		//pushState(PLAY);
	}
	
	public GameMain game() {
	    return gameMain;
	}
	
	public void update(float dt) {
		//if (dt>0.2) dt = 0.2f;
		gameStates.peek().update(dt);
		//level.update(dt);
	}
	
	public void render() {
	    GameState top = gameStates.peek();
	    if (top.isTransparent())
	        gameStates.peekSecond().render();
		top.render();
		//level.render();
	}
	
	/*private GameState getState(int state) {
		if (state == PLAY) return level = new Level1(this);
		return null;
	}*/
	
	/*public void setState(int state) {
		popState();
		pushState(state);
	}*/

	public void setState(GameState state) {
	    popState();
	    pushState(state);
    }
	
    /*public void pushState(int state) {
		gameStates.push(getState(state));
	}*/

    public void pushState(GameState state) {
        gameStates.push(state);
    }
	
	public void popState() {
		gameStates.pop().dispose();
	}
	

}
