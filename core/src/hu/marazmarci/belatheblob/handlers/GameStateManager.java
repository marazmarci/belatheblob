package hu.marazmarci.belatheblob.handlers;

import com.badlogic.gdx.Gdx;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.states.GameStateScreen;
import hu.marazmarci.belatheblob.states.MenuScreen;
import hu.marazmarci.belatheblob.utils.DoublePeekableStack;

@Prog3HF
@Prog3HF_JavaDoc_TODO
public class GameStateManager {

	private GameMain gameMain;
	private DoublePeekableStack<GameStateScreen> gameStates;

	
	public GameStateManager(GameMain game) {
		this.gameMain = game;
		gameStates = new DoublePeekableStack<GameStateScreen>();
	}
	
	public GameMain getGameMain() {
	    return gameMain;
	}
	
	public void update(float deltaTime) {
		gameStates.peek().handleInputAndUpdate(deltaTime);
	}
	
	public void render() {
	    GameStateScreen top = gameStates.peek();
	    if (top.isTransparent())
	        gameStates.peekSecond().render();
		top.render();
	}

	public void replaceState(GameStateScreen gameState) {
	    popState();
	    pushState(gameState);
    }

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

}
