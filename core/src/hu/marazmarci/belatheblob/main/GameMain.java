package hu.marazmarci.belatheblob.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hu.marazmarci.belatheblob.handlers.*;
import hu.marazmarci.belatheblob.states.Level1;

public class GameMain implements ApplicationListener {
	
	public enum GameVariant { DEFAULT, ANNA, LIVIA, MAG }
	
	public static GameVariant gameVariant = GameVariant.DEFAULT;
	
	public static boolean android = true;
	
	public static String TITLE;
	public static int WIDTH = 640;
	public static int HEIGHT = 320;
	public static final int SCALE = 2;
	public static final float STEP = 1 / 60f;
	private float accum = 0, dt;
	
	public static int FPS;
	public static int desiredFPS = 60;
	public static float desiredDeltaTime = 1/(float)desiredFPS;
	//private static IntArray avgFPS = new IntArray(false,10);
	static int[] avgFPSarray = new int[5];
	static byte avgFPSarrayPos = 0;
	static boolean avgFPSarrayReady = false;
	static int avgFPS = 60;
	//private static IntSet smoothFPS_array = new IntSet(10);
	//public static float smoothFPS = 0;
	
	public static ContentManager res;
	private GameStateManager gsm;
	
	private SpriteBatch sb;
	private BoundedCamera boundedCam;
	private OrthographicCamera hudCam;
	
	public static float time = 0;
	
	public static boolean lowPerformanceMode = false; // TODO remove
	public static boolean gameOver = false;
	public static boolean gameOverType = true; // TODO enum
	public static long gameOverFrame;
	
	public GameMain(boolean android_) {
		super();
		android = android_; 
		WIDTH = android ? 640 : 640;
		HEIGHT = android ? 320 : 320;
		
		desiredFPS = 60;
		desiredDeltaTime = 1/(float)desiredFPS;
	}
	
	public void create () {
		
		sb = new SpriteBatch();
		boundedCam = new BoundedCamera();
		boundedCam.setToOrtho(false, WIDTH, HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, WIDTH, HEIGHT);
		
		//Gdx.graphics.setVSync(false);
		
		try {
			res = new ContentManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		
		gsm = new GameStateManager(this);
	}
	
	public void render () {
		FPS = Gdx.graphics.getFramesPerSecond();
		if (FPS == 61) FPS = 60;
		
		if (Level1.frame> 60 && Level1.frame%30==0) {
			avgFPSarray[avgFPSarrayPos++] = FPS;
			if (avgFPSarrayPos==5) {
				avgFPSarrayPos=0;
				avgFPSarrayReady = true;
			}
			if (avgFPSarrayReady) avgFPS = (avgFPSarray[0] + avgFPSarray[1] + avgFPSarray[2] + avgFPSarray[3] + avgFPSarray[4]) / 5;
			lowPerformanceMode = avgFPS < 43;
		}
		
		Gdx.graphics.setTitle(TITLE + " -- FPS: " + FPS);
		
		dt = Gdx.graphics.getDeltaTime();
		if (!gameOver) time += dt;
		if (FPS > 50) {
			gsm.update(STEP);
			Level1.world.step(STEP, lowPerformanceMode?4:6,2);
		} else {
			accum += dt;
			
			gsm.update(dt);
			
			while (accum >= STEP) {
				Level1.world.step(STEP, lowPerformanceMode?4:6,2);
				accum-=STEP;
			}
		}
		/*accum += dt;
		
		while (accum >= STEP) {
			gsm.update(STEP);
			accum-=STEP;
		}*/
		
		//gsm.update(dt); //gsm.update(dt > STEP ? STEP : dt);
		gsm.render();
		MyInput.update();
	}
	
	public void dispose() {
		
	}
	
	public SpriteBatch getSpriteBatch() { return sb; }
	public BoundedCamera getCamera() { return boundedCam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }


	public void resize(int w, int h) {
		System.out.println("RESIZE: ["+w+";"+h+"]");
		
		WIDTH = w/SCALE; HEIGHT = h/SCALE;
		
		Level1.width = w;  Level1.height = h;
		
		boundedCam.setToOrtho(false, WIDTH, HEIGHT);
		//TODO univerzális referencia kéne ide
		Level1.b2dCam.setToOrtho(false, WIDTH/hu.marazmarci.belatheblob.handlers.B2DVars.PPM, HEIGHT/hu.marazmarci.belatheblob.handlers.B2DVars.PPM);
		//hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		//gsm.play.resize();
	}
	public void pause() {}
	public void resume() {}
	
	public static void initTitle() {
		TITLE = "Béla the Blob"+(gameVariant == GameVariant.ANNA?" - Anna Edition": (gameVariant == GameVariant.LIVIA? " - Lívia Edition" : (gameVariant == GameVariant.MAG? " - Mag Edition" : "")) );
	}
}
