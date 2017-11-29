package hu.marazmarci.belatheblob.handlers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_JavaDoc_TODO;
import hu.marazmarci.belatheblob.Prog3HF_TODO;
import hu.marazmarci.belatheblob.main.GameMain;

@Prog3HF
@Prog3HF_JavaDoc_TODO
public abstract class GameInputHandler implements InputProcessor {

    //private static final IntMap<TouchPoint> touchPoints = new IntMap<TouchPoint>(10);
    private static final IntMap<Vector3> touchPoints = new IntMap<Vector3>(10);
    private static final IntArray heldKeys = new IntArray(10);


    public final void handleInput() {
        for (int keyCode : heldKeys.items)
            handleKeysHeld(heldKeys);
    }



    /* *********************
     * ** Keyboard events **
     * ********************* */


    @Override
    public final boolean keyDown(int keyCode) {
        heldKeys.add(keyCode);
        boolean handled = handleKeyDown(keyCode);
        if (!handled && keyCode == Input.Keys.ESCAPE)
            GameMain.getGameStateManager().popState();
        return true;
    }


    @Override
    public final boolean keyUp(int keycode) {
        heldKeys.removeValue(keycode);
        handleKeyUp(keycode);
        return true;
    }







    /* ******************
     * ** Touch events **
     * ****************** */


    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoints.ensureCapacity(pointer + 1);
        Vector3 newTouchPoint = new Vector3(screenX, screenY, 0);
        //GameMain.translateTouchPoint(newTouchPoint);
        touchPoints.put(pointer, newTouchPoint);

        // event calls
        handleTouchDown(newTouchPoint);
        handleTouchHeld(touchPoints);

        return true;
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPoint = touchPoints.remove(pointer);
        if (touchPoint != null) {
            touchPoint.set(screenX, screenY, 0); // TODO ez valszeg nem kell
            //GameMain.translateTouchPoint(touchPoint);
            // event call
            handleTouchUp(touchPoint);
        }
        return true;
    }

    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 touchPoint = touchPoints.get(pointer).set(screenX, screenY, 0);
        //GameMain.translateTouchPoint(touchPoint);

        // event call
        handleTouchDragged(touchPoint);

        return true;
    }





    protected abstract boolean handleKeyDown(int keyCode);
    protected abstract void handleKeyUp(int keyCode);
    protected abstract void handleKeysHeld(IntArray heldKeys);



    //TODO touch parameters

    @Prog3HF_TODO
    protected abstract void handleTouchDown(Vector3 touchPoint);
    @Prog3HF_TODO
    protected abstract void handleTouchUp(Vector3 touchPoint);
    @Prog3HF_TODO
    protected abstract void handleTouchHeld(IntMap<Vector3> actualTouchPoints);
    @Prog3HF_TODO
    protected abstract void handleTouchDragged(Vector3 touchPoint);





    /* *********************************
     * ** Default implementations for **
     * ** not frequently used methods **
     * ********************************* */


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }




    /* ****************
     * ** Key states **
     * **************** */

    /*private static void setKeyState(int keycode, boolean state) {
        keys[keycode] = state;
    }

    private static boolean getKeyState(int keycode) {
        return keys[keycode];
    } */

}


