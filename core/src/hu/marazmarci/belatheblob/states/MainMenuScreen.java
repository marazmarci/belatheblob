package hu.marazmarci.belatheblob.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;
import hu.marazmarci.belatheblob.entities.enemies.Bunny;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.states.levels.Level1;

/**
 * A főmenüt reprezentáló osztály.
 */
@Prog3HF
@Prog3HF_JavaDoc_TODO
public class MainMenuScreen extends MenuScreen {

    private static final float MENU_BUNNY_SPEED = 2;

    private MenuBunny bunny;
    private float bunnyMouseX = 300;


    /**
     * Létrehozza a főmenüt, és elhelyezi a gombokat és
     *   mindegyiknek megadja a végrahajtandó műveletét.
     *
     * @param gameStateManager a játékállapot-kezelő
     */
    public MainMenuScreen(GameStateManager gameStateManager) {
        super(gameStateManager);

        bunny = new MenuBunny(bunnyMouseX, 30);

        Button settingsButton = new Button(gsm, 100,200,400,50,"Settings", Color.ORANGE, new Runnable() {
            public void run() {
                gsm.pushState(new SettingsMenuScreen(gsm));
            }
        });

        Button playButton = new Button(gsm, 100,100,400,50,"Start Game", Color.GREEN, new Runnable() {
            public void run() {
                gsm.pushState(new Level1(gsm));
            }
        });

        addGuiElement(playButton);
        addGuiElement(settingsButton);
    }

    @Override
    public void update(float deltaTime) {
        bunny.moveTowardsPoint(bunnyMouseX, deltaTime, MENU_BUNNY_SPEED);
    }


    @Override
    public void render() {
        super.render();
        bunny.render();
    }

    @Override
    protected GameInputHandler createGameInputHandler() {
        return new MainMenuScreenInputHandler();
    }

    class MainMenuScreenInputHandler extends MenuScreenInputHandler {

        // hogy ne kelljen a mouseMoved-ban az unproject hívásnál mindig újat létrehozni
        Vector3 temp = new Vector3();

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            bunnyMouseX = hudCam.unproject(temp.set(screenX,0,0)).x;
            return true;
        }

        @Override
        protected void handleTouchHeld(IntMap<Vector3> actualTouchPoints) {
            //TODO bunny jump
            Vector3 touchPoint = actualTouchPoints.get(0);
            if (touchPoint != null)
                bunnyMouseX = touchPoint.x;
        }
    }





    class MenuBunny {

        private Vector2 pos;
        private float time = 0;
        private float mouseDistance = 0;
        private boolean animationFrameSelector = false;

        MenuBunny(float x, float y) {
            pos = new Vector2(x, y);
        }

        void moveTowardsPoint(float mouseX, float deltaTime, float speed) {
            mouseDistance = mouseX - pos.x;
            pos.x += mouseDistance * deltaTime * speed;
            time += deltaTime;
            if (mouseDistance < 0) mouseDistance *= -1;
        }

        public void render() {
                    // 1 /(6f  + mouseDistance/10f)
            if (time > 10/(60f + mouseDistance)) {
                time = 0;
                animationFrameSelector = !animationFrameSelector;
            }
            sb.begin();
            sb.draw(Bunny.texs[animationFrameSelector ? 0 : 1], pos.x - 16, pos.y);
            sb.end();
        }

    }


    @Override
    public void doActivate() {
        super.doActivate();
        sb.setProjectionMatrix(hudCam.combined);
        new Bunny().initTexture();
    }
}
