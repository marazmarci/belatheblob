package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;
import hu.marazmarci.belatheblob.gui.BoundingBox;
import hu.marazmarci.belatheblob.gui.GuiElement;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputAdapter;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;

import java.util.ArrayList;

@Prog3HF
@Prog3HF_JavaDoc_TODO
public abstract class MenuScreen extends GameStateScreen {

    @Prog3HF_JavaDoc_TODO
    private ArrayList<GuiElement> guiElements = new ArrayList<GuiElement>();

    @Prog3HF_JavaDoc_TODO
    public MenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        shapeRenderer.setProjectionMatrix(hudCam.combined);

    }

    @Prog3HF_JavaDoc_TODO
    protected ArrayList<GuiElement> getGuiElements() {
        return guiElements;
    }

    @Prog3HF_JavaDoc_TODO
    protected void addGuiElement(GuiElement guiElement) {
        guiElements.add(guiElement);
    }

    @Override
    @Prog3HF_JavaDoc_TODO
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (GuiElement guiElement : guiElements)
            guiElement.render();
    }

    @Override
    @Prog3HF_JavaDoc_TODO
    public void onCreate() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
    }

    @Override
    protected GameInputHandler createGameInputHandler() {
        return new MenuScreenInputHandler();
    }


    @Prog3HF_JavaDoc_TODO
    class MenuScreenInputHandler extends GameInputAdapter {

        @Override
        @Prog3HF_JavaDoc_TODO
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
            Vector3 tp = new Vector3(screenX, screenY, 0);
            BoundingBox box = new BoundingBox(100,100,400,50);
            //System.out.println(tp + " -> " + GameMain.translateTouchPoint(tp) + "   " + box.isPointInside(tp));
            System.out.println(tp + " -> " + hudCam.unproject(tp) + "   " + box.isPointInside(tp));
            return true;
        }
    }


}
