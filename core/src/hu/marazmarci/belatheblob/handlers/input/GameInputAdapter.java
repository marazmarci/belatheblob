package hu.marazmarci.belatheblob.handlers.input;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;

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
}
