package hu.marazmarci.belatheblob.handlers;

public class MyInput {
	
	public static int x;
	public static int y;
	public static boolean down;
	public static boolean pdown;

	public static boolean[] keys;
	public static boolean[] pkeys;
	
	public static final int NUM_KEYS = 17;
	
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
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update() {
		for (int i=0; i<NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static boolean isTouchDown() { return down; }
	public static boolean isTouched() { return down && !pdown; }
	public static boolean isTouchReleased() { return !down && pdown; }
	
	public static void setKey(int k, boolean b) { keys[k] = b; }
	public static boolean isDown(int k) { return keys[k]; }
	public static boolean isPressed(int k) { return keys[k] && !pkeys[k]; }
	public static boolean isReleased(int k) { return !keys[k] && pkeys[k]; }
}
