package hu.marazmarci.belatheblob.handlers;

import com.badlogic.gdx.Gdx;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.states.GameStateScreen;
import hu.marazmarci.belatheblob.states.MenuScreen;
import hu.marazmarci.belatheblob.utils.DoublePeekableStack;

public class GameStateManager {

	private GameMain gameMain;
	private DoublePeekableStack<GameStateScreen> gameStates;
	private Difficulty difficulty = Difficulty.EASY;
	
	//public static final int PLAY = 912837; // TODO enum
	
	public GameStateManager(GameMain game) {
		this.gameMain = game;
		gameStates = new DoublePeekableStack<GameStateScreen>();
		//pushState(PLAY);
	}
	
	public GameMain getGameMain() {
	    return gameMain;
	}
	
	public void update(float deltaTime) {
		//if (dt>0.2) dt = 0.2f;
		gameStates.peek().handleInputAndUpdate(deltaTime);
		//level.update(dt);
	}
	
	public void render() {
	    GameStateScreen top = gameStates.peek();
	    if (top.isTransparent())
	        gameStates.peekSecond().render();
		top.render();
		//level.render();
	}

	/*public GameStateScreen getState() {
	    return gameStates.peek();
    }*/
	
	/*private GameStateScreen getState(int state) {
		if (state == PLAY) return level = new Level1(this);
		return null;
	}*/
	
	/*public void replaceState(int state) {
		popState();
		pushState(state);
	}*/

	public void replaceState(GameStateScreen gameState) {
	    popState();
	    pushState(gameState);
    }
	
    /*public void pushState(int state) {
		gameStates.push(getState(state));
	}*/

    public void pushState(GameStateScreen gameState) {
        gameStates.push(gameState);
        activateTopScreen();
    }
	
	public void popState() {
		gameStates.pop().dispose();
		if (gameStates.size() > 0) {
            activateTopScreen();
        } else {
            gameStates.push(new MenuScreen(this) {
                protected void update(float dt) {
                    Gdx.app.exit();
                }
            });
        }
	}

	public void handleResize(int w, int h) {
        gameStates.peek().handleResize(w, h);
    }


    private void activateTopScreen() {
        GameStateScreen gameState = gameStates.peek();
        gameState.activate();
        Gdx.input.setInputProcessor(gameState.getGameInputHandler());
    }


    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
