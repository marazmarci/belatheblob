package hu.marazmarci.belatheblob.handlers.input;

@Deprecated
public class MyInput {
	
	private static int x;
	private static int y;
	private static boolean touching;
	private static boolean prevTouching;

	private static boolean[] keys;
	private static boolean[] prevKeys;

	enum MyKeys {
        BTN_X,
        BTN_Y,
        BTN_SPACE,
        BTN_UP,
        BTN_DOWN,
        BTN_LEFT,
        BTN_RIGHT,
        BTN_W,
        BTN_A,
        BTN_S,
        BTN_D,
        BTN_ENTER,
        BTN_TAB,
        BTN_SHIFT_LEFT,
        BTN_CTRL_RIGHT,
        BTN_CTRL_LEFT,
        BTN_SHIFT_RIGHT,
        NUM_KEYS
	}

	public static final int BTN_X = 0;
	public static final int BTN_Y = 1;
	public static final int BTN_SPACE = 2;
	public static final int BTN_UP = 3;
	public static final int BTN_DOWN = 4;
	public static final int BTN_LEFT = 5;
	public static final int BTN_RIGHT = 6;
	public static final int BTN_W = 7;
	public static final int BTN_A = 8;
	public static final int BTN_S = 9;
	public static final int BTN_D = 10;
	public static final int BTN_ENTER = 11;
	public static final int BTN_TAB = 12;
	public static final int BTN_SHIFT_LEFT = 13;
	public static final int BTN_CTRL_RIGHT = 14;
	public static final int BTN_CTRL_LEFT = 15;
	public static final int BTN_SHIFT_RIGHT = 16;

	public static boolean left = false, right = false, jump = false, jumpPrev = false, slinkinessPlus = false, slinkinessMinus = false;


	static {
		keys = new boolean[255];
		prevKeys = new boolean[255];
	}

	public static void update() {
        System.arraycopy(keys, 0, prevKeys, 0, 255);
	}

	public static boolean isTouchDown() { return touching; }
	public static boolean isTouched() { return touching && !prevTouching; }
	public static boolean isTouchReleased() { return !touching && prevTouching; }

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
