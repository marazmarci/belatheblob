package hu.marazmarci.belatheblob.handlers;

import com.badlogic.gdx.Gdx;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.states.GameOverScreen;
import hu.marazmarci.belatheblob.states.GameStateScreen;
import hu.marazmarci.belatheblob.states.menu.MenuScreen;
import hu.marazmarci.belatheblob.utils.DoublePeekableStack;

/**
 * Játékállapot-menedzser osztály.
 * A feladata, hogy a játékállapotok közötti váltásokat leegyszerűsítse.
 */
@Prog3HF
public class GameStateManager {

	private GameMain gameMain;

	/**
     * A képernyőn megjelenő játékállapotokat tároló stack.
     * Mindig a legfelső kerül kirajzolásra.
     * Kivételes esetekben a felülről második is látszódhat a háttérben.
     *   lásd: {@link GameOverScreen}
     */
	private DoublePeekableStack<GameStateScreen> gameStates;


	public GameStateManager(GameMain game) {
		this.gameMain = game;
		gameStates = new DoublePeekableStack<GameStateScreen>();
	}


    /**
     * @return A játék fő moduljának példánya
     */
	public GameMain getGameMain() {
	    return gameMain;
	}


    /**
     * Frissíti és kirajzolja a legfelül lévő játékállapot-réteget,
     *   és opcionálisan (ha a legfelső átlátszó) a felső alattit is.
     *
     * @param deltaTime a legutóbbi update óta eltelt idő
     */
    public void updateAndRender(float deltaTime) {
        GameStateScreen top = gameStates.peek(), second = null;
        boolean topIsTransparent = top.isTransparent();
        if (topIsTransparent)
            (second = gameStates.peekSecond()).update(deltaTime);
        top.handleInput();
        top.update(deltaTime);
        if (topIsTransparent)
            second.render();
        top.render();
        /* sorrend:
           (1. second update)
            2. top handleInput
            3. top update
           (4. second render)
            5. top render
         */
    }

    /**
     * Kicseréli a legfelső játékállapotot egy újra.
     *
     * @param gameState az új játékállapot
     */
	public void replaceState(GameStateScreen gameState) {
	    popState();
	    pushState(gameState);
    }


    /**
     * Hozzáad egy új játékállapotot, ami a legfelső képernyő-réteg lesz.
     *
     * @param gameState az új játékállapot
     */
    public void pushState(GameStateScreen gameState) {
        gameStates.push(gameState);
        activateTopScreen();
    }


    /**
     * Eltávolítja a játékállapot képernyő-rétegek közül a legfelsőt.
     * Ha a legalsó réteg is eltávolításra került, kilép a játékból.
     */
    public void popState() {
		gameStates.pop().dispose();
		if (gameStates.size() > 0) {
            activateTopScreen();
        } else {
            gameStates.push(new MenuScreen(this) {
                public void update(float deltaTime) {
                    Gdx.app.exit();
                }
            });
        }
	}


    /**
     * Lekezeli az ablak átméretezését
     *
     * @param w szélesség
     * @param h magasság
     */
	public void handleResize(int w, int h) {
        for (GameStateScreen gameState : gameStates)
            gameState.handleResize(w, h);
    }


    /**
     * Előkészíti az új legfelső réteget a használatra.
     */
    private void activateTopScreen() {
        GameStateScreen gameState = gameStates.peek();
        gameState.activate();
        Gdx.input.setInputProcessor(gameState.getGameInputHandler().repeatLastMouseMovedEvent());
    }

}
