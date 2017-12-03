package hu.marazmarci.belatheblob.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.states.menu.MenuScreen;

/**
 * Egy a felhasználói felületen elhelyezkedő gombot reprezentáló osztály
 */
@Prog3HF
public class Button extends GuiElement {

    private final BitmapFont bigFont;
    private final BoundingBox boundingBox;
    private final BoundingBox outlineBoundingBox;
    private final Runnable action;
    private final int xAdjustment;
    private boolean showOutline;
    private String text;
    private Color color;

    public Button(MenuScreen menu, int x, int y, int width, int height, int xAdjustment, String text, Color color, Runnable action) {
        this(menu, new BoundingBox(x, y, width, height), xAdjustment, text, color, action);
    }

    public Button(MenuScreen menu, BoundingBox boundingBox, int xAdjustment, String text, Color color, Runnable action) {
        super(menu);
        this.boundingBox = boundingBox;
        this.outlineBoundingBox = boundingBox.clone();
        this.xAdjustment = xAdjustment;
        this.text = text;
        this.color = color;
        this.action = action;
        this.bigFont = menu.getBigFont();
        outlineBoundingBox.expand(3);
        //this.fontCache = new BitmapFontCache(bigFont);
    }


    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(getColor());
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        if (showOutline)
            shapeRenderer.rect(outlineBoundingBox.x, outlineBoundingBox.y, outlineBoundingBox.width, outlineBoundingBox.height);
        shapeRenderer.end();

        //fontCache.setColor(color.lerp(Color.WHITE, 0.5f));
        bigFont.setColor(getColor());

        spriteBatch.begin();
        //fontCache.draw(spriteBatch);
        bigFont.draw(spriteBatch, getText(), boundingBox.x + 125 + xAdjustment, boundingBox.y + 37.5f);
        spriteBatch.end();
    }


    /**
     * Ellenőrzi, hogy egy érintési pont a gomb téglalapjába esik-e.
     * Ha igen, lefuttatja a hozzá rendelt műveletet.
     *
     * @param touchPoint az érintési pont
     * @return a pont a gomb téglalapjába esik-e
     */
    @Override
    public boolean handleTouch(Vector3 touchPoint) {
        if (boundingBox.isPointInside(touchPoint)) {
            if (action != null)
                action.run();
            return true;
        }
        return false;
    }

    @Override
    public boolean handleOnMouseOver(float x, float y) {
        return showOutline = boundingBox.isPointInside(x, y);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


}
