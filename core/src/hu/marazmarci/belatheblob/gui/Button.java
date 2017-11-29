package hu.marazmarci.belatheblob.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.handlers.GameStateManager;

@Prog3HF
public class Button extends GuiElement {

    private BoundingBox boundingBox;
    private String text;
    private Color color;
    private ButtonAction action;
    private ShapeRenderer shapeRenderer;

    public Button(GameStateManager gsm, int x, int y, int width, int height, String text, Color color, ButtonAction action) {
        this(gsm, new BoundingBox(x, y, width, height), text, color, action);
    }

    public Button(GameStateManager gsm, BoundingBox boundingBox, String text, Color color, ButtonAction action) {
        super(gsm);
        this.shapeRenderer = gsm.getGameMain().getShapeRenderer();
        this.boundingBox = boundingBox;
        this.text = text;
        this.color = color;
        this.action = action;
    }

    @Override
    public void render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        shapeRenderer.end();
    }

    @Override
    public boolean handleTouch(Vector3 touchPoint) {
        if (boundingBox.isPointInside(touchPoint)) {
            if (action != null)
                action.run(this);
            return true;
        }
        return false;
    }

    public String getText() {
        return text;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
