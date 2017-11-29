package hu.marazmarci.belatheblob.handlers.input;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

@Deprecated
public class InputStateHolder {

    private static ArrayList<Vector2> touchCoordinates;
    private static boolean touching;
    private static boolean prevTouching;

    private static boolean[] keys = new boolean[256];
    private static boolean[] prevKeys = new boolean[256];


    public static void update() {
        System.arraycopy(keys, 0, prevKeys, 0, 256);
    }


    public static boolean isTouchDown() {
        return touching;
    }

    public static boolean isTouched() {
        return touching && !prevTouching;
    }

    public static boolean isTouchReleased() {
        return !touching && prevTouching;
    }


    public static void setKey(int key, boolean b) {
        keys[key] = b;
    }

    public static boolean isDown(int key) {
        return keys[key];
    }

    public static boolean isPressed(int key) {
        return keys[key] && !prevKeys[key];
    }

    public static boolean isReleased(int key) {
        return !keys[key] && prevKeys[key];
    }


}
