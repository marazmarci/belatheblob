package hu.marazmarci.belatheblob.handlers.input;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.states.menu.MenuScreen;

/**
 * Adapter osztály a {@link GameInputHandler} osztályhoz, üres implementációkkal.
 * Ha ebből származtatunk le egy input-kezelő osztályt, akkor csak a számunkra
 *   szükséges metódusokat kell felüldefiniálnunk.
 *   pl: {@link MenuScreen.MenuScreenInputHandler}
 */
@Prog3HF
public abstract class GameInputAdapter extends GameInputHandler {


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
    protected void handleMouseMoved(int screenX, int screenY) {

    }
}
