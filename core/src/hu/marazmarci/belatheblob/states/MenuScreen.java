package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.gui.BoundingBox;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.gui.GuiElement;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.states.levels.Level1;

import java.util.ArrayList;

@Prog3HF
public abstract class MenuScreen extends GameStateScreen {

    ArrayList<GuiElement> guiElements = new ArrayList<GuiElement>();

    public MenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        shapeRenderer.setProjectionMatrix(hudCam.combined);


    }


    protected void addGuiElement(GuiElement guiElement) {
        guiElements.add(guiElement);
    }



    @Override
    protected void update(float dt) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (GuiElement guiElement : guiElements)
            guiElement.render();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void handleResize(int w, int h) {

    }

    @Override
    protected GameInputHandler createGameInputHandler() {
        return new MenuScreenInputHandler();
    }



    class MenuScreenInputHandler extends GameInputHandler {

        @Override
        protected boolean handleKeyDown(int keyCode) {
            return false;
        }

        @Override
        protected void handleKeyUp(int keyCode) {

        }

        @Override
        protected void handleKeysHeld(IntArray heldKeys) {

        }

        @Override
        protected void handleTouchDown(Vector3 touchPoint) {
            hudCam.unproject(touchPoint);
            //GameMain.translateTouchPoint(touchPoint);
            for (GuiElement guiElement : guiElements)
                if (guiElement.handleTouch(touchPoint))
                    return;
        }

        @Override
        protected void handleTouchUp(Vector3 touchPoint) {

        }

        @Override
        protected void handleTouchHeld(IntMap<Vector3> actualTouchPoints) {

        }

        @Override
        protected void handleTouchDragged(Vector3 touchPoint) {

        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {

            Vector3 tp = new Vector3(screenX, screenY, 0);

            BoundingBox box = new BoundingBox(100,100,400,50);

            //System.out.println(tp + " -> " + GameMain.translateTouchPoint(tp) + "   " + box.isPointInside(tp));
            System.out.println(tp + " -> " + hudCam.unproject(tp) + "   " + box.isPointInside(tp));

            return true;
        }
    }


}
