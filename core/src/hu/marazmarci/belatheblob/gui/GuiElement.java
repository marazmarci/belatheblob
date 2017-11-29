package hu.marazmarci.belatheblob.gui;

import com.badlogic.gdx.math.Vector3;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.handlers.GameStateManager;

@Prog3HF
public abstract class GuiElement {

    protected GameStateManager gsm;

    public GuiElement(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void render();
    public abstract boolean handleTouch(Vector3 touchPoint);



}
