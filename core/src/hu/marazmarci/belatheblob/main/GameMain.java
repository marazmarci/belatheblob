package hu.marazmarci.belatheblob.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.entities.enemies.Enemy;
import hu.marazmarci.belatheblob.handlers.BoundedCamera;
import hu.marazmarci.belatheblob.handlers.ContentManager;
import hu.marazmarci.belatheblob.handlers.Difficulty;
import hu.marazmarci.belatheblob.handlers.GameStateManager;
import hu.marazmarci.belatheblob.states.MainMenuScreen;
import hu.marazmarci.belatheblob.states.levels.Level1;

// needs to be singleton
public class GameMain implements ApplicationListener {
	
	public enum GameVariant { DEFAULT, ANNA, LIVIA, MAG }
	
	public static GameVariant gameVariant = GameVariant.DEFAULT;
	
	public static boolean android = true;
	
	public static String TITLE;
	public static int WIDTH = 640;
	public static int HEIGHT = 320;
	public static final int SCALE = 2;
	public static final float STEP = 1 / 60f;
	private float accum = 0, deltaTime;
	
	public static int FPS;
	public static int desiredFPS = 60;
	public static float desiredDeltaTime = 1 / (float)desiredFPS;
	//private static IntArray avgFPS = new IntArray(false,10);
	static int[] avgFPSarray = new int[5];
	static byte avgFPSarrayPos = 0;
	static boolean avgFPSarrayReady = false;
	static int avgFPS = 60;
	//private static IntSet smoothFPS_array = new IntSet(10);
	//public static float smoothFPS = 0;
	
	public static ContentManager res;
	private static GameStateManager gsm;
	
	private static SpriteBatch spriteBatch;
	private static ShapeRenderer shapeRenderer;
	private static BoundedCamera boundedCam;
	public static OrthographicCamera hudCam;

	public static float time = 0;
	
	public static boolean lowPerformanceMode = false; // TODO remove
	public static boolean gameOver = false;
	public static boolean gameOverType = true; // TODO enum
	public static long gameOverFrame;

	private Difficulty difficulty = Difficulty.EASY;

	
	public GameMain(boolean android_) {
		super();
		android = android_; 
		WIDTH = android ? 640 : 640;
		HEIGHT = android ? 320 : 320;
		
		desiredFPS = 60;
		desiredDeltaTime = 1/(float)desiredFPS;
	}
	
	public void create() {
		
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
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
		
		gsm = new GameStateManager(this);

		gsm.pushState(new MainMenuScreen(gsm));

	}
	
	public void render () {

		//TODO ennek nagy részét átrakni a Level osztályba!

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
		
		deltaTime = Gdx.graphics.getDeltaTime();
		if (!gameOver)
		    time += deltaTime;

        // TODO accumulated step (kis lépésekkel, mondjuk 60 FPS-nél kb iterációnként 2.5 update hívással)

        //gsm.update(STEP);
        gsm.updateAndRender(STEP);

		/*if (FPS > 50) {
			gsm.update(STEP);
			Level1.world.step(STEP, lowPerformanceMode?4:6,2);
		} else {
			accum += deltaTime;
			
			gsm.update(deltaTime);
			
			while (accum >= STEP) {
				Level1.world.step(STEP, lowPerformanceMode?4:6,2);
				accum-=STEP;
			}
		}*/
		/*accum += deltaTime;
		
		while (accum >= STEP) {
			gsm.update(STEP);
			accum-=STEP;
		}*/
		
		//gsm.update(deltaTime); //gsm.update(deltaTime > STEP ? STEP : deltaTime);

        //gsm.render();

	}
	
	public void dispose() {
		
	}
	
	public SpriteBatch getSpriteBatch() { return spriteBatch; }
	//public BoundedCamera getCamera() { return boundedCam; }
	public OrthographicCamera getHUDCamera() { return hudCam; }
	public ShapeRenderer getShapeRenderer() { return shapeRenderer; }
	public static GameStateManager getGameStateManager() {
	    return gsm;
    }


	public void resize(int w, int h) {
		System.out.println("RESIZE: ["+w+";"+h+"]");
        boundedCam.setToOrtho(false, WIDTH, HEIGHT);
		WIDTH = w/SCALE;
		HEIGHT = h/SCALE;
		gsm.handleResize(w, h);
	}
	public void pause() {}
	public void resume() {}
	
	public static void initTitle() {
		TITLE = "Béla the Blob"+(gameVariant == GameVariant.ANNA?" - Anna Edition": (gameVariant == GameVariant.LIVIA? " - Lívia Edition" : (gameVariant == GameVariant.MAG? " - Mag Edition" : "")) );
	}

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Prog3HF
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        Enemy.jumpTowardsBlob = difficulty == Difficulty.HARD;
    }

}
