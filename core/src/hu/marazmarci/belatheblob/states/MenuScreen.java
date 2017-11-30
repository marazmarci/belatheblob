package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.gui.GuiElement;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputAdapter;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;

import java.util.ArrayList;

/**
 * Absztrakt menüképernyő osztály.
 * Kezeli a {@link GuiElement}-ek tárolását és kirajzolását.
 */
@Prog3HF
public abstract class MenuScreen extends GameStateScreen {

    /** A felhasználói felület elemeit tároló lista. */
    private ArrayList<GuiElement> guiElements = new ArrayList<GuiElement>();

    public MenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);
    }


    /**
     * @return a felhasználói felület elemeinek listája
     */
    ArrayList<GuiElement> getGuiElements() {
        return guiElements;
    }

    /**
     * @param guiElement a hozzáadni kívánt GUI-elem
     */
    void addGuiElement(GuiElement guiElement) {
        guiElements.add(guiElement);
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (GuiElement guiElement : guiElements)
            guiElement.render();
    }

    @Override
    public void doActivate() {
        shapeRenderer.setProjectionMatrix(hudCam.combined);
        Gdx.gl.glClearColor(0, 0, 0, 0);
    }

    @Override
    protected GameInputHandler createGameInputHandler() {
        return new MenuScreenInputHandler();
    }


    /**
     * Egy menüképernyő bemenetét kezelő osztály.
     */
    protected class MenuScreenInputHandler extends GameInputAdapter {

        /**
         * Kezeli az érintéseket/kattintásokat.
         * A touchPoint paramétert átadja az összes GUI-eleme handleTouch függvényének.
         *
         * @param touchPoint az érintés/kattintás koordinátái
         */
        @Override
        protected void handleTouchDown(Vector3 touchPoint) {
            hudCam.unproject(touchPoint);
            //GameMain.translateTouchPoint(touchPoint);
            for (GuiElement guiElement : guiElements)
                if (guiElement.handleTouch(touchPoint))
                    return;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            //debug output:
            /*
            Vector3 tp = new Vector3(screenX, screenY, 0);
            BoundingBox box = new BoundingBox(100,100,400,50);
            //System.out.println(tp + " -> " + GameMain.translateTouchPoint(tp) + "   " + box.isPointInside(tp));
            System.out.println(tp + " -> " + hudCam.unproject(tp) + "   " + box.isPointInside(tp));
            */
            return true;
        }
    }


}
