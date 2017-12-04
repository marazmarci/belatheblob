package hu.marazmarci.belatheblob.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.states.menu.MenuScreen;

/**
 * Egy menüképernyőn megjelenő vizuális, interaktivitásra képes objektumot reprezentál
 */
@Prog3HF
public abstract class GuiElement {

    protected MenuScreen menu;
    protected GameStateManager gsm;
    protected ShapeRenderer shapeRenderer;
    protected SpriteBatch spriteBatch;

    public GuiElement(MenuScreen menu) {
        this.menu = menu;
        this.gsm = menu.getGameStateManager();
        this.spriteBatch = menu.getSpriteBatch();
        this.shapeRenderer = menu.getShapeRenderer();
    }

    public abstract void render();

    // both receives unprojected touch coordinates:

    public abstract boolean onTouchDown(Vector3 touchPoint);
    public abstract boolean onMouseOver(float x, float y);



}
