package hu.marazmarci.belatheblob.handlers.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.main.GameMain;


/**
 *  Input kezelő absztrakt osztály.
 *  A feladata egy közbülső feldolgozási lépést hozzáadni a bemenet feldolgozásához,
 *    ami jelentősen meg tudja könnyíteni annak menetét.
 *  Egész pontosan azt csinálja, hogy minden input esemény érkezésekor eltárolja
 *    az aktuálisan lenyomott gombokat és érintési pontokat, majd minden update
 *    iterációban kívülről meghívódik ennek az osztálynak a handleInput() függvénye,
 *    ami meghívja a leszármazott osztály gomb- és érintés-nyomvatartást kezelő
 *    függvényeit.
 */
@Prog3HF
public abstract class GameInputHandler implements InputProcessor {

    /** Aktuálisan lenyomott kattintások/érintések koordinátáit tároló map. (map key: int, az érintés sorszáma) */
    private static final IntMap<Vector3> touchPoints = new IntMap<Vector3>(10);

    /** Aktuálisan lenyomott gombok kódját tároló lista. */
    private static final IntArray heldKeys = new IntArray(10);


    /**
     * Meghívja a leszármazott osztály gomb- és érintés-nyomvatartást kezelő függvényeit.
     */
    public final void handleInput() {
        handleKeysHeld(heldKeys); // event call
        handleTouchHeld(touchPoints); // event call
    }



    /* *********************
     * ** Keyboard events **
     * ********************* */


    /**
     * A libGDX hívja meg egy gomb lenyomásakor.
     * Eltárolja a gomb új állapotát, majd értesíti a leszármazott
     *   osztályt annak handleKeyDown függvényének meghívásával.
     * Ha a leszármazott osztály nem kezelte le az eseményt, és az Escape
     *   gomb lett lenyomva, akkor befejezi az aktuális játékállapot.
     *
     * @param keyCode billentyűkód
     * @return kezeltük-e az eseményt (itt mindig true)
     */
    @Override
    public final boolean keyDown(int keyCode) {
        heldKeys.add(keyCode);
        boolean handled = handleKeyDown(keyCode); // event call
        if (!handled && keyCode == Input.Keys.ESCAPE)
            GameMain.getGameStateManager().popState();
        return true;
    }


    /**
     * A libGDX hívja meg egy gomb felengedésekor.
     * Eltárolja a gomb új állapotát, majd értesíti a leszármazott osztályt
     *   az eseményről annak handleKeyUp függvényének meghívásával.
     *
     * @param keyCode billentyűkód
     * @return kezeltük-e az eseményt (itt mindig true)
     */
    @Override
    public final boolean keyUp(int keyCode) {
        heldKeys.removeValue(keyCode);
        handleKeyUp(keyCode); // event call
        return true;
    }







    /* ******************
     * ** Touch events **
     * ****************** */


    /**
     * A libGDX hívja meg kattintás/érintés kezdetekor.
     * Eltárolja a gomb új állapotát, majd értesíti a leszármazott osztályt
     *   az eseményről annak handleTouchDown függvényének meghívásával.
     *
     * @param screenX x koordináta
     * @param screenY y koordináta
     * @param pointer az érintés sorszáma ha érintés, egyébként 0
     * @param button ha kattintás, az egérgomb azonosítója
     * @return kezeltük-e az eseményt (itt mindig true)
     */
    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            touchPoints.ensureCapacity(pointer + 1);
            Vector3 newTouchPoint = new Vector3(screenX, screenY, 0);
            //GameMain.translateTouchPoint(newTouchPoint);
            touchPoints.put(pointer, newTouchPoint);
            handleTouchDown(newTouchPoint); // event call
        }
        return true;
    }


    /**
     * A libGDX hívja meg kattintás/érintés felengedésekor.
     * Eltárolja a gomb új állapotát, majd értesíti a leszármazott osztályt
     *   az eseményről annak handleTouchUp függvényének meghívásával.
     *
     * @param screenX x koordináta
     * @param screenY y koordináta
     * @param pointer az érintés sorszáma ha érintés, egyébként 0
     * @param button ha kattintás, az egérgomb azonosítója
     * @return kezeltük-e az eseményt (itt mindig true)
     */
    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Buttons.LEFT) {
            Vector3 touchPoint = touchPoints.remove(pointer);
            if (touchPoint != null) {
                touchPoint.set(screenX, screenY, 0); // TODO ez kell?
                //GameMain.translateTouchPoint(touchPoint);
                handleTouchUp(touchPoint); // event call
            }
        }
        return true;
    }


    /**
     * A libGDX hívja meg kattintás/érintés húzásakor.
     * Eltárolja a gomb új állapotát, majd értesíti a leszármazott osztályt
     *   az eseményről annak handleTouchDragged függvényének meghívásával.
     *
     * @param screenX x koordináta
     * @param screenY y koordináta
     * @param pointer az érintés sorszáma ha érintés, egyébként 0
     * @return kezeltük-e az eseményt (itt mindig true)
     */
    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 touchPoint = touchPoints.get(pointer);
        if (touchPoint != null) {
            touchPoint.set(screenX, screenY, 0);
            //GameMain.translateTouchPoint(touchPoint);
            handleTouchDragged(touchPoint); // event call
        }
        return true;
    }




    /**
     * A leszármazott osztály által implementálandó, gomb lenyomást
     *   kezelő event handler függvény.
     *
     * @param keyCode billentyűkód
     * @return lekezelte-e az eseményt (az Escape gomb külön kezelése miatt van rá szükség)
     */
    protected abstract boolean handleKeyDown(int keyCode);


    /**
     * A leszármazott osztály által implementálandó, gomb felengedést
     *   kezelő event handler függvény.
     *
     * @param keyCode billentyűkód
     */
    protected abstract void handleKeyUp(int keyCode);


    /**
     * A leszármazott osztály által implementálandó, gomb nyomvatartást
     *   kezelő event handler függvény.
     *
     * @param heldKeys lenyomott billentyűkódok listája
     */
    protected abstract void handleKeysHeld(IntArray heldKeys);




    /**
     * A leszármazott osztály által implementálandó, érintés/kattintás kezdetét
     *   kezelő event handler függvény.
     * Azért Vector3, és nem Vector2, mert a játék 2-szeres nagyítással van megjelenítve, és
     *   a képernyő-koordinátákat belső koordinátákká alakító hudCam.unproject függvénynek
     *   valamiért ilyen Vector3 paramétert kell átadni. 
     *
     * @param touchPoint az érintési pont koordinátái
     */
    protected abstract void handleTouchDown(Vector3 touchPoint);


    /**
     * A leszármazott osztály által implementálandó, érintés/kattintás felengedését
     *   kezelő event handler függvény.
     *
     * @param touchPoint az érintési pont koordinátái
     */
    protected abstract void handleTouchUp(Vector3 touchPoint);


    /**
     * A leszármazott osztály által implementálandó, érintés/kattintás húzását
     *   kezelő event handler függvény.
     *
     * @param touchPoint az érintési pont koordinátái
     */
    protected abstract void handleTouchDragged(Vector3 touchPoint);


    /**
     * A leszármazott osztály által implementálandó, érintés/kattintás felengedését
     *   kezelő event handler függvény.
     *
     * @param actualTouchPoints aktuális érintési pontok
     */
    protected abstract void handleTouchHeld(IntMap<Vector3> actualTouchPoints);





    /* *********************************
     * ** Default implementations for **
     * ** not frequently used methods **
     * ********************************* */

    /**
     * Egy karakterbillentyű lenyomásakor hívott függvény.
     *
     * @param character a beírt karakter
     * @return lekezelte-e az eseményt
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Az egérmutató ablak feletti mozgásakor hívott függvény.
     *
     * @param screenX x koordináta
     * @param screenY y koordináta
     * @return lekezelte-e az eseményt
     */
    @Override
    public final boolean mouseMoved(int screenX, int screenY) {
        lastMouseX = screenX;
        lastMouseY = screenY;
        handleMouseMoved(screenX, screenY);
        return false;
    }

    /**
     * Az egérgörgőt kezelő függvény.
     *
     * @param amount az iránytól függően ±1
     * @return lekezelte-e az eseményt
     */
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

    private static int lastMouseX = -420, lastMouseY;

    public final GameInputHandler repeatLastMouseMovedEvent() {
        if (lastMouseX != -420)
            handleMouseMoved(lastMouseX, lastMouseY);
        return this;
    }

    protected abstract void handleMouseMoved(int screenX, int screenY);

}


