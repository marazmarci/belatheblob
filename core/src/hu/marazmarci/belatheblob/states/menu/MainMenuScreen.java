package hu.marazmarci.belatheblob.states.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.entities.enemies.Bunny;
import hu.marazmarci.belatheblob.gui.Button;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.states.levels.Level1;

/**
 * A főmenüt reprezentáló osztály.
 */
@Prog3HF
public class MainMenuScreen extends MenuScreen {

    private static final float MENU_BUNNY_SPEED = 1.5f;

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

        bunny = new MenuBunny(spriteBatch, bunnyMouseX, 30);

        Button settingsButton = new Button(this, 110,200,400,50,20,"Settings", Color.ORANGE, new Runnable() {
            public void run() {
                gsm.pushState(new SettingsMenuScreen(gsm));
            }
        });

        Button playButton = new Button(this, 110,100,400,50,0,"Start Game", Color.GREEN, new Runnable() {
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



        @Override
        public void handleMouseMoved(int screenX, int screenY) {
            bunnyMouseX = unprojectTouch(screenX, 0).x;
            super.handleMouseMoved(screenX, screenY);
        }

        @Override
        protected void handleTouchHeld(IntMap<Vector3> actualTouchPoints) {
            //TODO bunny jump
            Vector3 touchPoint = actualTouchPoints.get(0);
            if (touchPoint != null)
                bunnyMouseX = unprojectTouch(touchPoint.x, 0).x;
        }
    }


    @Override
    public void doActivate() {
        super.doActivate();
        bunny.getPos().x = -50;
        new Bunny().initTexture();
    }
}
