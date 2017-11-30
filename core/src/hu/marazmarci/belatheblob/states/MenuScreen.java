package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    private BitmapFont bigFont;

    /** A felhasználói felület elemeit tároló lista. */
    private ArrayList<GuiElement> guiElements = new ArrayList<GuiElement>();

    public MenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);
        bigFont = new BitmapFont();
        bigFont.getData().setScale(2);
        bigFont.setColor(Color.WHITE);
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

    /**
     * @return a szövegkirajzoláshoz használt objektum
     */
    public BitmapFont getBigFont() {
        return bigFont;
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (GuiElement guiElement : guiElements)
            guiElement.render();
    }

    @Override
    public void doActivate() {
        spriteBatch.setProjectionMatrix(hudCam.combined);
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
            unprojectTouch(touchPoint);
            //GameMain.translateTouchPoint(touchPoint);
            for (GuiElement guiElement : guiElements)
                if (guiElement.handleTouch(touchPoint))
                    return;
        }


        @Override
        public void handleMouseMoved(int screenX, int screenY) {
            Vector3 tempTouchPoint = unprojectTouch(screenX, screenY);
            //GameMain.translateTouchPoint(touchPoint);
            for (GuiElement guiElement : guiElements)
                guiElement.handleOnMouseOver(tempTouchPoint.x, tempTouchPoint.y);
            //debug output:
            /*
            Vector3 tp = new Vector3(screenX, screenY, 0);
            BoundingBox box = new BoundingBox(100,100,400,50);
            //System.out.println(tp + " -> " + GameMain.translateTouchPoint(tp) + "   " + box.isPointInside(tp));
            System.out.println(tp + " -> " + hudCam.unproject(tp) + "   " + box.isPointInside(tp));
            */
        }

        // hogy ne kelljen a mouseMoved-ban az unproject hívásnál mindig újat létrehozni
        private Vector3 temp = new Vector3();


        protected Vector3 unprojectTouch(float screenX, float screenY) {
            return unprojectTouch(temp.set(screenX,screenY,0));
        }

        protected Vector3 unprojectTouch(Vector3 touchPoint) {
            return hudCam.unproject(touchPoint);
        }


    }


}
