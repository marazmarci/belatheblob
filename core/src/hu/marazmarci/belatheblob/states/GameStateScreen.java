package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.main.GameMain;

/**
 * Egy képernyőn megjeleníthető játékállapotot reprezentáló osztály.
 */
public abstract class GameStateScreen implements Disposable {

    protected final GameStateManager gsm;
    protected final GameMain game;

    protected GameInputHandler input;

    // rendereléshez
	protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;
    protected OrthographicCamera hudCam;


	protected GameStateScreen(GameStateManager gsm) {
		this.gsm = gsm;
        this.game = gsm.getGameMain();
        this.spriteBatch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.hudCam = game.getHUDCamera();
        this.input = createGameInputHandler();
	}



    /**
     * Meghívja a {@link GameInputHandler} eseménykezelő függvényét.
     */
    public final void handleInput() {
        input.handleInput();
    }

    /**
     * A játékállapot képernyőre kerülésekor (kívülről) hívott függvény.
     * Meghívja az absztrakt doActivate() függvényt.
     */
    public final void activate() {
        System.out.println("[GameStateScreen activated] : " + this);
	    doActivate();
    }

    /**
     * A játékállapot képernyőre rajzolása
     */
    public abstract void render();

    /**
     * A játékállapot létrehozza egy példányt a saját input kezelő osztályából.
     *
     * @return az input kezelő példány
     */
    protected abstract GameInputHandler createGameInputHandler();


    /**
     * Frissíti a játékállapot belső állapotát
     *
     * @param deltaTime a legutóbbi update óta eltelt idő
     */
    public void update(float deltaTime) {

    }

    /**
     * A játékállapot képernyőre kerülésekor (GameStateScreen által delegáltan) hívott függvény.
     */
    public void doActivate() {

    }

    /**
     * A játékállapot deaktiválásakor (képernyőről való eltűnésekor) hívott függvény.
     * Itt kell a multimédiás erőforrásokat felszabadítani a dispose függvényük meghívásával
     */
	@Override
	public void dispose() {

    }

    /**
     * Megadja, hogy átlátszó-e ez ennek a játékállapotnak a képernyőre rajzolt rétege.
     * lásd: {@link GameOverScreen}
     *
     * @return átlátszó-e
     */
	public boolean isTransparent() {
	    return false;
    }

    /**
     * Lekezeli az ablak átméretezését
     *
     * @param w szélesség
     * @param h magasság
     */
    public void handleResize(int w, int h) {

    }

    /**
     * @return a játékállapot input-kezelője
     */
    public final GameInputHandler getGameInputHandler() {
        return input;
    }

    /**
     * @return az osztály neve (debug célokra)
     */
    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }

    /**
     * @return a játékállapot-kezelő
     */
    public GameStateManager getGameStateManager() {
        return gsm;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
