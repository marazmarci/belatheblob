package hu.marazmarci.belatheblob.states.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.Prog3HF_TODO;
import hu.marazmarci.belatheblob.entities.*;
import hu.marazmarci.belatheblob.entities.collectibles.*;
import hu.marazmarci.belatheblob.entities.enemies.BossBunny;
import hu.marazmarci.belatheblob.entities.enemies.Bunny;
import hu.marazmarci.belatheblob.entities.enemies.Enemy;
import hu.marazmarci.belatheblob.entities.enemies.Spikes;
import hu.marazmarci.belatheblob.gui.HUD;
import hu.marazmarci.belatheblob.handlers.*;
import hu.marazmarci.belatheblob.handlers.input.GameInputAdapter;
import hu.marazmarci.belatheblob.handlers.input.GameInputHandler;
import hu.marazmarci.belatheblob.handlers.input.MyInput;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.main.GameMain.*;
import hu.marazmarci.belatheblob.states.GameLevelScreen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Random;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;
import static hu.marazmarci.belatheblob.handlers.input.MyInput.*;
import static hu.marazmarci.belatheblob.main.GameMain.*;

public class Level1 extends GameLevelScreen {
	
	private static World world;
	private static Box2DDebugRenderer b2dr;
	private static BoundedCamera box2dCam;
	private static BoundedCamera cam;
	private static PolygonSpriteBatch polyBatch;
	public static TiledMap tiledMap;
	private static int tileMapWidth, tileMapHeight, tileSize, redCrystalsTotal = 85;
	private static OrthogonalTiledMapRenderer tmr;
	public static Random random = new Random();
	public static boolean debug = false, zokniBackground, playerVisible = true, enableTurboSpeed = false, turboSpeed = false; //true: halál, false: win
	public static BlobPlayer player;
	public static Array<B2DSprite> hiddenSprites, sprites;
	private static Array<SpriteRemovalEntry> spritesToRemove, spritesToRemoveFinished;
	public static Array<Body> bodiesToRemove;
	public static Array<Enemy> contactingEnemies;
	private static ArrayMap<String,Class<? extends B2DSprite>> spriteNameMap;
	public static ArrayMap<String,Array<Body>> interactive;
	public static Array<Task<?>> tasks, finishedTasks;
	private static Vector2 spawn = new Vector2(100,100);
	public static int width, height, collected;
	public static long frame, gameOverFrame = 0;
	public static Cat theCat;
	private static hu.marazmarci.belatheblob.gui.HUD hud;



	public static final String positionSaveFilePath = "savedPos.anna_a_legjobb_labvez";


    /**
     * Beolvas egy fájlból egy korábban elmentett pozíciót
     *
     * @param path a fájl elérési útvonala
     * @return a beolvasott pozíció
     */
	@Prog3HF
	private Vector2 readSavedPosition(String path) {
        Vector2 result = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (Vector2) ois.readObject();
        } catch (Exception ignored) { }
        return result;
    }


    /**
     * Kiírja egy fájlba a paraméterként kapott pozíciót.
     *
     * @param vector2 a fájlba írandó pozíciót tároló 2 dimenziós vektor
     * @param path a fájl elérési útvonala
     */
    @Prog3HF
    private void savePosition(Vector2 vector2, String path) {
        FileOutputStream FOS;
        try {
            FOS = new FileOutputStream(path);
            ObjectOutputStream ous = new ObjectOutputStream(FOS);
            ous.writeObject(vector2);
            ous.flush();
            ous.close();
            FOS = null;
            ous = null;
            System.gc();
        } catch (Exception ignored) { }
    }


	
	public Level1(GameStateManager gsm) {
		
		super(gsm);
		
		GameMain.time = 0;
		collected = 0;
		width = Gdx.graphics.getWidth(); height = Gdx.graphics.getHeight();
		
		sprites = new Array<B2DSprite>();
		hiddenSprites = new Array<B2DSprite>();
		spritesToRemove = new Array<SpriteRemovalEntry>();
		spritesToRemoveFinished = new Array<SpriteRemovalEntry>();
		tasks = new Array<Task<?>>();
		finishedTasks = new Array<Task<?>>();
		interactive = new ArrayMap<String, Array<Body>>();
		spriteNameMap = new ArrayMap<String,Class<? extends B2DSprite>>();
		bodiesToRemove = new Array<Body>();
		contactingEnemies = new Array<Enemy>();
		MyContactListener.blobOnGround = new Array<Body>();
		zokniBackground = false;
		frame = 0;
		
		
		//spriteNameMap.put("bluecrystals", BlueCrystal.class);
		spriteNameMap.put("melons", Melon.class);
		spriteNameMap.put("redcrystals", RedCrystal.class);
		spriteNameMap.put("CHEESES", Cheese.class);
		spriteNameMap.put("beans", Bean.class);
		//spriteNameMap.put("key1s", Key1.class);
		//spriteNameMap.put("key2s", Key2.class);
		//spriteNameMap.put("doors", Door.class);
		spriteNameMap.put("bunnies", Bunny.class);
		spriteNameMap.put("spikes", Spikes.class);
		spriteNameMap.put("spike", Spike.class);
		spriteNameMap.put("cats", Cat.class);
		spriteNameMap.put("grandma", Grandma.class);
		spriteNameMap.put("mushrooms", Mushroom.class);
		spriteNameMap.put("pill1", Pill1.class);
		spriteNameMap.put("pill2", Pill2.class);
		
	    //init box2d
	    world = new World(new Vector2(0,-4), true);
	    world.setContactListener(new MyContactListener());
	    b2dr = new Box2DDebugRenderer();
	    //b2dr.setDrawVelocities(true);
	    /*	BodyDef bdef = new BodyDef();
			FixtureDef fdef = new FixtureDef();
			PolygonShape shape = new PolygonShape();
			bdef.type = BodyType.DynamicBody;
	     */
		
		
		//load tiled map
		tiledMap = new TmxMapLoader().load("maps/level1.tmx");
		tmr = new OrthogonalTiledMapRenderer(tiledMap);
		tileSize = (Integer) tiledMap.getProperties().get("tilewidth");
		tileMapWidth = (Integer) tiledMap.getProperties().get("width");
		tileMapHeight = (Integer) tiledMap.getProperties().get("height");
		
		createMapLayer("ground", BIT_GROUND, BIT_BLOB | BIT_BUNNY | BIT_SPRITE);
		createMapLayer("wall", BIT_WALL, BIT_BLOB | BIT_BUNNY);
		createMapLayer("ceiling", BIT_CEIL, BIT_BLOB | BIT_BUNNY);
		createMapLayer("nocollision", 0, 0);
		createMapLayer("brainfuck", 0, 0);
		
		Iterator<MapLayer> it = tiledMap.getLayers().iterator();
		Array<MapLayer> mapLayers = new Array<MapLayer>();
		while (it.hasNext()) {
			MapLayer layer = it.next();
			if (layer.getName().startsWith("interactive")) mapLayers.add(layer);
		}
		MapLayers ml = tiledMap.getLayers();
		for (MapLayer layer : mapLayers) {
			if (ml.getIndex(layer)!=-1) createMapLayer(layer.getName(), BIT_WALL, BIT_BLOB | BIT_BUNNY | BIT_SPRITE); 
		}
		
		for(String s : spriteNameMap.keys())
			createSprites(s);
		
		createFloatingBlocks();
		
		MapProperties mp = tiledMap.getLayers().get("SPAWN").getObjects().get(0).getProperties();
		spawn.x = ((Float) mp.get("x")) + ((Float) mp.get("width"))/2;
		spawn.y = (Float) mp.get("y") + ((Float) mp.get("height"))/2;




		// READ POSITION

        @Prog3HF
        Vector2 readPosition = readSavedPosition(positionSaveFilePath);
        if (readPosition != null)
            spawn = readPosition;


		
		
		//init gfx
		box2dCam = new BoundedCamera();
		box2dCam.setToOrtho(false, GameMain.WIDTH / PPM, GameMain.HEIGHT / PPM);
	    cam = new BoundedCamera();
        cam.setToOrtho(false, WIDTH, HEIGHT);
	    cam.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
	  ( polyBatch = new PolygonSpriteBatch()).setProjectionMatrix(cam.combined);
	    shapeRenderer.setProjectionMatrix(cam.combined);
	  //( sr2 = new ShapeRenderer()).setProjectionMatrix(cam.combined);
	    spriteBatch.setProjectionMatrix(cam.combined);
		box2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
		//res.initBackgrounds(cam);
		Texture bgs = res.getTexture("bgs");
		ContentManager.mountains = new Background(new TextureRegion(bgs, 0, 240, 320, 240), cam, 0.2f);
		ContentManager.clouds = new Background(new TextureRegion(bgs, 0, 0, 320, 240), cam, 0.1f);
		//bgs.dispose();
		ContentManager.zokni = new TextureRegion(res.getTexture("zokni"));
		

		//init Marci's stuff
		
	    //Blob Béla megteremtése
		player = new BlobPlayer();
		player.spawn(world, spawn.x, spawn.y);
		//player.spawn(world, 160, 80); //start
		//player.spawn(world, 1040, 300); //door
		//player.spawn(world, 1200, 200);
		//player.spawn(world, 1350, 150);//sm
		player.setMaxScore(redCrystalsTotal);
		
		hud = new HUD();

		/*spawn bunnies
		for (int i=0; i<5; i++) {
			Bunny bunny = (Bunny) (new Bunny()).spawn(world, (60+i*100)/PPM, 140/PPM).setAutoJump(true, 50+random.nextInt(120));
			sprites.add(bunny);
		}*/
	}

    public static void setCat(Cat cat) {
        theCat = cat;
        cat.setWorld(world);
    }


    @Override
    public void render() {
		
		//try {Thread.sleep(20);}
		//catch (InterruptedException e) {}
		
		if (frame++ == 0) setDay();
		
		//gfx cuccok
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //TODO beszívott effekt, ha kihagyod :)
		//if (player.slinkiness==0) Gdx.gl.glClearColor(116/255f, 200/255f, 1f, 1f);
		player.calcAvgPos();
		cam.setPosition(player.getAvgPosX()*PPM /*TODO + GameMain.V_WIDTH / 4*/, player.getAvgPosY()*PPM + GameMain.HEIGHT / 4 - 50);
		cam.update();
		polyBatch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		//zokniBackground = true;
		
		if (!zokniBackground) {
			if (!GameMain.lowPerformanceMode) {
				hudCam.zoom = 0.5f;
				hudCam.update();
				spriteBatch.setProjectionMatrix(hudCam.combined);
				//backgrounds[0].render(spriteBatch);
				//spriteBatch.begin(); spriteBatch.setColor(116/255f, 200/255f, 1f, 1f);
				ContentManager.clouds.render(spriteBatch);
				ContentManager.mountains.render_(spriteBatch);
				hudCam.zoom = 1;
				hudCam.update();
			}
			
			spriteBatch.setProjectionMatrix(cam.combined);
			
		} else {
			spriteBatch.setProjectionMatrix(hudCam.combined);
			spriteBatch.begin();
			//System.out.println("GameMain.V_HEIGHT/2-500 = "+(GameMain.V_HEIGHT/2-500));
			//spriteBatch.draw(zokni, GameMain.V_WIDTH/2-500, GameMain.V_HEIGHT/2-500, 500, 500, 1000, 1000, 0.75f + 0.2f + (float)Math.sin(frame/30f)/5f, 0.75f + 0.2f + (float)Math.sin(frame/30f)/5f, frame);
			spriteBatch.draw(ContentManager.zokni, -180, -340, 500, 500, 1000, 1000, 0.75f + 0.2f + (float)Math.sin(frame/30f)/5f, 0.75f + 0.2f + (float)Math.sin(frame/30f)/5f, frame);
			//spriteBatch.draw(zokni, 1000, 1000, new Affine2().rotate(frame));
			spriteBatch.end();
			spriteBatch.setProjectionMatrix(cam.combined);
		}
		
		spriteBatch.begin();
		for (B2DSprite s : hiddenSprites)
		    if (player.dstLinear(s.getPosition()) < (GameMain.WIDTH + GameMain.HEIGHT)*0.8)
		        s.render(spriteBatch);
		spriteBatch.end();
		
		//draw Tiled map
		tmr.setView(cam);
		tmr.render();
		
		//draw objects
		spriteBatch.begin();
		for (B2DSprite s : sprites)
		    if (player.dstLinear(s.getPosition()) < (GameMain.WIDTH + GameMain.HEIGHT)*0.8)
		        s.render(spriteBatch); //if (frame%60 == 0 && s instanceof Bunny) System.out.println("Bunni Dystance: "+player.dstLinear(s.getPosition()));
		spriteBatch.end();
		
		if (playerVisible)
		    player.render(polyBatch, shapeRenderer);
		
		//draw box2d world
		if(debug) {
			box2dCam.setPosition(player.getAvgPosX() /*TODO + GameMain.V_WIDTH / 4 / PPM*/, player.getAvgPosY() + (GameMain.HEIGHT / 4 - 50)/PPM);
			box2dCam.update();
			b2dr.render(world, box2dCam.combined);
		}
		
		spriteBatch.setProjectionMatrix(hudCam.combined);
		shapeRenderer.setProjectionMatrix(hudCam.combined);
		hud.render(spriteBatch, shapeRenderer);
	}



	@Prog3HF_TODO
    @Deprecated
    public void handleInput_() {

        if (debug) {
            if (isPressed(BTN_SHIFT_LEFT))
                GameMain.lowPerformanceMode = !GameMain.lowPerformanceMode;
            else if (isPressed(BTN_CTRL_RIGHT))
                playerVisible = !playerVisible;
            else if (isPressed(BTN_CTRL_LEFT))
                enableTurboSpeed = !enableTurboSpeed;
        }

		/*float dtMultiply = 1;//GameMain.desiredDeltaTime/dt;
		if (dtMultiply<1) dtMultiply = 1;
		else if (dtMultiply>1) dtMultiply = (float) ((int)dtMultiply + Math.pow(dtMultiply-(int)dtMultiply,1/10f));
		System.out.println("==============");
		System.out.println("dt = "+dt);
		System.out.println("desiredDeltaTime = "+GameMain.desiredDeltaTime);
		System.out.println("dtMultiply = "+dtMultiply);
		System.out.println("==============");*/

        if (enableTurboSpeed)
            turboSpeed = isDown(BTN_CTRL_LEFT);

        if (isPressed(BTN_TAB) && isDown(BTN_SHIFT_RIGHT))
            debug = !debug;

        if (!gameOver) {
            int blobOnGround = MyContactListener.blobOnGround.size;
            float v = player.coreBall.getLinearVelocity().y;
            float angularImpulse = 0.0015f; /* * dtMultiply;*/ //régi érték:0.001
            float force = 0.085f /* * dtMultiply;*/ * (turboSpeed?5:1); //régi érték:0.08

            //boolean right = false, left = false, jump = false, jumpReleased = false, slinkinessPlus = false, slinkinessMinus = false;



            if (slinkinessPlus) {
                player.changeSlinkinessBy(-0.01f /* * dtMultiply;*/);
            } else if (slinkinessMinus) {
                player.changeSlinkinessBy(0.01f /* * dtMultiply;*/);
            }

            if (jump && !jumpPrev && (blobOnGround > 0 || turboSpeed)) {
                blobJump(1.25f);
                //if (turboSpeed) blobJump(0.25f);
            } else if (jumpPrev && !jump && blobOnGround == 0 && v > 1) {
                if(!turboSpeed) blobJump(-0.5f);
            }

            jumpPrev = jump;

            if (player.slinkiness > 0) {
                //force *= 1 + BlobPlayer.pow(player.slinkiness-0.84f,4f);
                force -= BlobPlayer.pow(player.slinkiness / 2.5f, 4); //TODO slinkiness hatása az ugrásra. eredeti értékek: player.slinkiness / 2.2f, 4f
                for (Body b : player.surfaceBalls) {
                    if (MyContactListener.blobOnGround.contains(b, true))
                        continue;
                    float force2 = player.slinkiness * force * (blobOnGround + 0.2f) / 25f;
                    b.applyForceToCenter((left ? -force2 : 0) + (right ? force2 : 0), 0, true);
                }
            }

            player.coreBall.applyForceToCenter((left ? -force : 0) + (right ? force : 0), 0, true);
            //blob.coreBody.applyTorque( (InputStateHolder.isDown(InputStateHolder.BTN_Y) ?  x : 0) + (InputStateHolder.isDown(InputStateHolder.BTN_X) ? -x : 0), true);
            player.coreBall.applyAngularImpulse((left ? angularImpulse : 0) + (right ? -angularImpulse : 0), true);
        } else {
            if (frame-gameOverFrame > 75 && MyInput.jump) {
                gameOver = false;
                gsm.replaceState(new Level1(gsm)); // TODO GameOverScreen
            }
        }

    }



    void blobJump(float f) {
		f *= 1-player.slinkiness/5f; //minél kisebb számmal osztod, annál gyengébb lesz Béla leeresztve
		int blobsize = MyContactListener.blobOnGround.size;
		for (Body b : MyContactListener.blobOnGround)
			b.applyForceToCenter(0, f-blobsize/20f, true);

		for (Body b : player.surfaceBalls)
			b.applyForceToCenter(0, f, true);
		
		player.coreBall.applyForceToCenter(0, f*2.5f, true);
		
		if (f>0) { //ha felfele kap erõt
			//boolean r = random.nextBoolean();
			player.pupilla1.set(random.nextInt(4)-2, random.nextInt(4)-2);
			player.pupilla2.set(random.nextInt(4)-2, random.nextInt(4)-2);
			
			//System.out.println("SOUND RANDOM = "+r);
			res.getSound("slimejump"+(player.slinkiness<0.5f?1:2)).play();
		}
	}

	@Override
	public void update(float deltaTime) {

		handleInput_();
		
		if (spritesToRemove.size>0) for (SpriteRemovalEntry s : spritesToRemove)
		    if(s.update())
		        spritesToRemoveFinished.add(s);

		if (spritesToRemoveFinished.size>0)
		    for (SpriteRemovalEntry s : spritesToRemoveFinished)
		        spritesToRemove.removeValue(s, true);
		
		if (bodiesToRemove.size>0) {
			for (Body b : bodiesToRemove)
			    world.destroyBody(b);
			bodiesToRemove.clear();
		}
		
		//if (tasks.size > 0)
			for (Task<?> t : tasks) {
				if (t.active) {
					if (t instanceof FadeTask<?>)
						((FadeTask<?>) t).update();
					if (t.when <= frame) {
						t.action();
						finishedTasks.add(t);
					}
				}
			}
		
		if (frame%1200 == 0 && finishedTasks.size>0) {
			finishedTasks.clear();
			for (Task<?> t : finishedTasks)
			    tasks.removeValue(t, true);
		}
		
		//TODO distance check
		for (B2DSprite s : sprites)
		    s.update(deltaTime);
		for (B2DSprite s : hiddenSprites)
		    s.update(deltaTime);
		
		if (gameOver && !gameOverType && frame%100==0) {
			blobJump(1.25f);
			player.setSlinkiness(random.nextFloat());
			player.coreBall.applyForceToCenter(random.nextFloat()*20-10, 0, true);
		}

        world.step(deltaTime, 6,2);

	}

	/*
	public void handleResize() {
		box2dCam.setToOrtho(false, GameMain.V_WIDTH/PPM, GameMain.V_HEIGHT/PPM);
	}
	*/

    private void createMapLayer(String layerName, int categoryBits, int maskBits) {
		
		MapLayers ml = tiledMap.getLayers();
		
		System.out.println("createMapLayer LOADING LAYER "+layerName+";  Level1.java:420");
		
		if (ml.get(layerName+"_anna")!=null) {
			if (GameMain.gameVariant == GameVariant.ANNA) {
				ml.remove(ml.get(layerName));
				ml.get(layerName+"_anna").setVisible(true);
				ml.get(layerName+"_anna").setName(layerName);
				System.out.println("createMapLayer Anna 1, layerName: "+layerName+";  Level1.java:425");
			} else {
				ml.remove(ml.get(layerName+"_anna"));
				System.out.println("createMapLayer Anna 2, layerName: "+layerName+";  Level1.java:425");
				//return;
			}
		}
		
		System.out.println("createMapLayer LOADING LAYER "+layerName+";  Level1.java:435");
		
		TiledMapTileLayer layer = (TiledMapTileLayer) ml.get(layerName);
		if (!layer.isVisible() && !layerName.equals("brainfuck") && !layerName.equals("interactive15")) return;     
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		String data = layer.getProperties().containsKey("data")?((String)layer.getProperties().get("data")):layerName;
		
		System.out.println(layerName);
		
		layer.setOpacity(1);
		
		System.out.println("createMapLayer LOADING LAYER "+layer.getName()+";  Level1.java:449");
		
		if ((categoryBits == 0 && maskBits == 0) || (!data.equals("ground") && !data.equals("wall") && !data.equals("ceiling")) ){
			System.out.println("layerName: "+layerName+", data: "+data+",  RETURN;   Level1.java:430");
			return;
		}
		
		System.out.println("createMapLayer LOADING LAYER "+layer.getName()+";  Level1.java:456");
		
		for(int y = 0; y < layer.getHeight(); y++) {
			for(int x = 0; x < layer.getWidth(); x++) {
				
				Cell cell = layer.getCell(x, y);
				
				//check if cell exists
				if (cell==null || cell.getTile()==null) continue;
				
				//System.out.println("CREATE MAP: current tile id = "+cell.getTile().getId());
				
				// onCreate box2d from cell
				bdef.type = BodyType.StaticBody;
				bdef.position.set((x+0.5f)*tileSize/PPM, (y+0.5f)*tileSize/PPM);
				ChainShape cs = new ChainShape();
				Vector2[] v = new Vector2[5];
				v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM); //bottom left
				v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM); //top left
				v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM); //top right
				v[3] = new Vector2(tileSize / 2 / PPM, -tileSize / 2 / PPM); //bottom right
				v[4] = v[0];
				cs.createChain(v);
				fdef.shape = cs;
				fdef.filter.categoryBits = (short)categoryBits;
				fdef.filter.maskBits = (short)maskBits;
				fdef.shape = cs;
				Body b = world.createBody(bdef);
				b.createFixture(fdef).setUserData(data);
				if (layerName.startsWith("interactive")) {
					if (interactive.get(layerName) == null) interactive.put(layerName, new Array<Body>());
					interactive.get(layerName).add(b);
				}
			}
		}
	}
	
	private void createFloatingBlocks() {
		MapLayer layer = tiledMap.getLayers().get("freeblocks");
		/*TiledMapTileSet tileSet = tiledMap.getTileSets().getTileSet("tiles");
		TiledMapTileSets tileSets = tiledMap.getTileSets();
		Iterator<TiledMapTileSet> it = tileSets.iterator();
		
		System.out.println("TILEDMAP TILESETS:");
		while(it.hasNext()) {
			TiledMapTileSet set = it.next();
			//System.out.println(" - "+set.getName());
			if (set.getName().equals("tiles")) {
				Iterator<TiledMapTile> it_ = set.iterator();
				//System.out.println("TILES in layer 'tiles':");
				while(it_.hasNext()) {
					TiledMapTile tile = it_.next();
					//System.out.println(" - "+tile.getId());
				}
				//System.out.println("set.getTile(32) == null: "+(set.getTile(32) == null));
			}
		}*/
		
		if (layer == null) return;
		
		MapObjects mo = layer.getObjects();
		
		for (MapObject obj : mo) {
			MapProperties prop = obj.getProperties();
			float x0 = ((Float)prop.get("x") + (Float)prop.get("width") / 2);
			float y0 = ((Float)prop.get("y") + (Float)prop.get("height") / 2);
			String type = (String) prop.get("type");
			//System.out.println("ASDASDASDASD tile type = "+type);
			//tileSet.iterator().next().getTextureRegion()
			//FloatingBlock floatingBlock = new FloatingBlock(tileSet.getTile(type).getTextureRegion());
			FloatingBlock floatingBlock = new FloatingBlock(type);
			sprites.add(floatingBlock.spawn(world, x0, y0));
		}
	}
	
	private void createSprites(String type) {
		
		MapLayers ml = tiledMap.getLayers();
		
		if (ml.get(type+"_anna")!=null) {
			if (GameMain.gameVariant == GameVariant.ANNA) {
				ml.remove(ml.get(type));
				ml.get(type+"_anna").setName(type);
			} else {
				ml.remove(ml.get(type+"_anna"));
				//return;
			}
		}
		
		MapLayer layer = ml.get(type);
		if (layer == null) return;
		
		MapObjects mo = layer.getObjects();
		
		for (MapObject obj : mo) {
			MapProperties mp = obj.getProperties();
			float x0 = ((Float)mp.get("x") + (Float)mp.get("width") / 2);
			float y0 = ((Float)mp.get("y") + (Float)mp.get("height") / 2);
			
			B2DSprite sprite = null;
			
			try {
				//System.out.println("class = "+spriteNameMap.get(type)+";  Level1.java:490");
				sprite = (B2DSprite) spriteNameMap.get(type).getDeclaredConstructors()[0].newInstance();
			} catch (Exception e) { e.printStackTrace(); }
			
			if (sprite instanceof Bunny && mp.containsKey("type") && mp.get("type").equals("boss")) {
				sprite = new BossBunny();
			}
			
			sprite.spawn(world, x0, y0);
			//System.out.println("Layer name = "+layer.getName());
			
			////if (sprite instanceof RedCrystal) redCrystalsTotal++;
			
			if (mp.get("hidden")!=null) {
				hiddenSprites.add(sprite);
			} else {
				sprites.add(sprite);
			}
			if (sprite instanceof Grandma && mp.containsKey("type")) {
				((Grandma)sprite).ID = false;
			}
			if (sprite instanceof Collectible && mp.containsKey("type") && ((String)mp.get("type")).equals("unlock")) {
				System.out.println("ADDED INTERACTIVE COLLECTIBLE: "+(String) mp.get("action"));
				/*Iterator<String> it = mp.getKeys();
				System.out.println("MapObject's properties: ");
				while (it.hasNext()) {
					String key = it.next();
					System.out.println(" - "+key+" => "+mp.get(key));
				}*/
				Collectible c = (Collectible) sprite;
				c.action = (String) mp.get("action");
				if (mp.containsKey("brainfuck")) {
					sprite.brainfuck = true;
				}
				int delay = 60;
				boolean asd = true;
				try {
					delay = Integer.parseInt((String) mp.get("delay"));
				} catch (Exception e) {
					asd = false;
				}
				if (asd) c.delay = delay;
				
				if (mp.containsKey("night")) c.beforeBoss = true;
				else if (mp.containsKey("day")) c.afterBoss = true;
				
				if (mp.containsKey("zokni")) c.zokni = true;
			}
			if (sprite instanceof Door) { //TODO ez csak ideiglenes!!!!
				System.out.println("DOOR SPAWN");
				DoorAnimation doorAnim = new DoorAnimation();
				sprites.add(doorAnim.spawn(world, x0, y0));
			} else if ((sprite instanceof Enemy || sprite instanceof BossBunny || sprite instanceof Bunny || sprite instanceof Spikes) && mp.containsKey("autojump")) {
				Enemy e = (Enemy) sprite;
				try {
					int autojumpInterval = Integer.parseInt((String) mp.get("autojump"));
					//System.out.println("AUTOJUMPINTERVAL = "+autojumpInterval);
					e.setAutoJump(true,autojumpInterval);
					e.autoJumpForceX = Float.parseFloat((String) mp.get("forceX"));
					e.autoJumpForceXrandomness = Float.parseFloat((String) mp.get("forceXrandomness"));
					e.autoJumpForceY = Float.parseFloat((String) mp.get("forceY"));
					e.autoJumpForceYrandomness = Float.parseFloat((String) mp.get("forceYrandomness"));
					//e.autoJumpMode = !((String) mp.get("mode")).equals("random"); //autoJumpMode := false: random; true: a blob felé
				} catch (Exception ex) {
                    //ex.printStackTrace(); hát ez most nem kell
                }
				finally {
					System.out.println("ADDED ENEMY! type: "+e.getClass());
					if (e instanceof BossBunny) {
						System.out.println("BOSSBUNNY SPAWN!!!!!!!!!!!!");
						System.out.println("e.autoJumpForceY = "+e.autoJumpForceY);
						System.out.println("e.autoJumpForceYrandomness = "+e.autoJumpForceYrandomness);
					}
				}
			}
			
			if (sprite instanceof Collectible && mp.containsKey("deslinkificator")) {
				((Collectible)sprite).deslinkificator = true;
				System.out.println("DESLINKIFICATOR!!!!!!!!!!!!");
			}
		}
	}
	
	public static void collect(Collectible c) {
		if (c.removed || gameOver) return;
		collected++;
		if (c instanceof RedCrystal) {
			player.addScore();
			System.out.println("=== SCORE: "+player.getScore()+"/"+player.getMaxScore()+" ===");
		}
		//(new Exception("=== SCORE: "+SCORE+" ===")).printStackTrace();
		removeSprite(c);
		c.removed = true;
		res.getSound("crystal").play();
		
		player.HP += c.healthPoints;
		if (player.HP > 20) player.HP = 20;
		
		if (c.action!=null) {
			System.out.println("REMOVE LAYER");
			res.getSound("magic1").play();
			tasks.add(new Task<Collectible>(c, frame + c.delay* GameMain.FPS/60L) {
				
				@Override
				public void run() {
					if (data.beforeBoss) {
						setNight();
						res.playBossMusic();
					}
					if (data.afterBoss)  {
						setDay();
					}
					
					if (data.zokni)	zokniBackground = true;
					
					System.out.println("data.deslinkificator = "+data.deslinkificator+";  Level1.java:599");
					if (data.deslinkificator) {
						player.disableUserSlinkinessChange = true;
						Level1.tasks.add(new FadeTask<Float>(player.slinkiness,frame+30) {

							@Override
							public void update() {
								//System.out.println("slinkiness update: "+(1f - startSlinkiness + (1f-startSlinkiness) * getVal()));
								player.setSlinkiness(data*(1-getVal()));
								
							}

							@Override
							public void run() {}

						});
					}
					
					res.playSound("magic2");
					
					MapLayers ml = tiledMap.getLayers();
					
					if (data.brainfuck) {
						if (ml.get("brainfuck").isVisible()) {
							ml.get("brainfuck").setVisible(false);
							ml.get("interactive15").setVisible(false);
							res.stopBrainfuckMusic();
						} else {
							ml.get("brainfuck").setVisible(true);
							ml.get("interactive15").setVisible(true);
							res.playBrainfuckMusic();
						}
					}
					
					if(ml.get(data.action)!=null) {
						ml.remove(ml.get(data.action));
						Array<Body> bodies = interactive.get(data.action);
						if (bodies!=null) for (Body b : bodies) bodiesToRemove.add(b);
						
						String layer2Name = data.action+"_nocollision";
						if(ml.get(layer2Name)!=null) {
							ml.remove(ml.get(layer2Name));
							bodies = interactive.get(layer2Name);
							if (bodies!=null) for (Body b : bodies) bodiesToRemove.add(b);
						}
					} else {
						System.out.println("COLLECTIBLE ACTION: CANT'T FIND LAYER: \""+data.action+"\"");
					}
				}
			});
			
		}
	}
	
	public static void removeSprite(B2DSprite sprite) {
		spritesToRemove.add(new SpriteRemovalEntry(sprite, 0));
	}
	
	public static void removeSpriteLater(B2DSprite sprite, int frames) {
		spritesToRemove.add(new SpriteRemovalEntry(sprite, frames));
	}
	
	public static void setNight() {Gdx.gl.glClearColor(0, 0, 0, 0);}
	public static void setDay() {Gdx.gl.glClearColor(116/255f, 200/255f, 1f, 1f);}


    /**
     * A játék állapotváltásakor vagy bezáródásakor hívott függvény.
     * Ez a felüldefiniált változat lementi béla pozícióját.
     */
	@Prog3HF
	@Override
    public void dispose() {
        savePosition(player.coreBall.getPosition().scl(PPM), positionSaveFilePath);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void handleResize(int w, int h) {
        Level1.width = w;  Level1.height = h;

        cam.setToOrtho(false, WIDTH, HEIGHT);
        //TODO univerzális referencia kéne ide
        box2dCam.setToOrtho(false, WIDTH / B2DVars.PPM, HEIGHT / B2DVars.PPM);
        //hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
    }

    @Override
    protected GameInputHandler createGameInputHandler() {
        return new Level1Input();
    }

    @Override
    public void doActivate() {

    }





    @Prog3HF
    class Level1Input extends GameInputAdapter {

        @Override
        protected boolean handleKeyDown(int keyCode) {
            switch(keyCode) {
                case Input.Keys.RIGHT: MyInput.setKey(MyInput.BTN_RIGHT, true); MyInput.right = true; break;
                case Input.Keys.LEFT: MyInput.setKey(MyInput.BTN_LEFT, true); MyInput.left = true; break;
                case Input.Keys.SPACE: MyInput.setKey(MyInput.BTN_SPACE, true); MyInput.jump = true; break;
                case Input.Keys.ENTER: MyInput.setKey(MyInput.BTN_ENTER, true); break;
                case Input.Keys.UP: MyInput.setKey(MyInput.BTN_UP, true); MyInput.slinkinessPlus = true; break;
                case Input.Keys.DOWN: MyInput.setKey(MyInput.BTN_DOWN, true); MyInput.slinkinessMinus = true; break;
                case Input.Keys.X: MyInput.setKey(MyInput.BTN_X, true); MyInput.right = true; break;
                case Input.Keys.Y: MyInput.setKey(MyInput.BTN_Y, true); MyInput.left = true; break;
                case Input.Keys.W: MyInput.setKey(MyInput.BTN_W, true); MyInput.slinkinessPlus = true; break;
                case Input.Keys.A: MyInput.setKey(MyInput.BTN_A, true); MyInput.left = true; break;
                case Input.Keys.S: MyInput.setKey(MyInput.BTN_S, true); MyInput.slinkinessMinus = true; break;
                case Input.Keys.D: MyInput.setKey(MyInput.BTN_D, true); MyInput.right = true; break;
                case Input.Keys.TAB: MyInput.setKey(MyInput.BTN_TAB, true); break;
                case Input.Keys.SHIFT_LEFT: MyInput.setKey(MyInput.BTN_SHIFT_LEFT, true); break;
                case Input.Keys.CONTROL_RIGHT: MyInput.setKey(MyInput.BTN_CTRL_RIGHT, true); break;
                case Input.Keys.CONTROL_LEFT: MyInput.setKey(MyInput.BTN_CTRL_LEFT, true); break;
                case Input.Keys.SHIFT_RIGHT: MyInput.setKey(MyInput.BTN_SHIFT_RIGHT, true); break;
                default: return false;
            }
			return true;
        }

        @Override
        protected void handleKeyUp(int keyCode) {
            switch(keyCode) {
                case Input.Keys.RIGHT: MyInput.setKey(MyInput.BTN_RIGHT, false); MyInput.right = false; break;
                case Input.Keys.LEFT: MyInput.setKey(MyInput.BTN_LEFT, false); MyInput.left = false; break;
                case Input.Keys.SPACE: MyInput.setKey(MyInput.BTN_SPACE, false); MyInput.jump = false; break;
                case Input.Keys.ENTER: MyInput.setKey(MyInput.BTN_ENTER, false); break;
                case Input.Keys.UP: MyInput.setKey(MyInput.BTN_UP, false); MyInput.slinkinessPlus = false; break;
                case Input.Keys.DOWN: MyInput.setKey(MyInput.BTN_DOWN, false); MyInput.slinkinessMinus = false; break;
                case Input.Keys.X: MyInput.setKey(MyInput.BTN_X, false); MyInput.right = false; break;
                case Input.Keys.Y: MyInput.setKey(MyInput.BTN_Y, false); MyInput.left = false; break;
                case Input.Keys.W: MyInput.setKey(MyInput.BTN_W, false); MyInput.slinkinessPlus = false; break;
                case Input.Keys.A: MyInput.setKey(MyInput.BTN_A, false); MyInput.left = false; break;
                case Input.Keys.S: MyInput.setKey(MyInput.BTN_S, false); MyInput.slinkinessMinus = false; break;
                case Input.Keys.D: MyInput.setKey(MyInput.BTN_D, false); MyInput.right = false; break;
                case Input.Keys.TAB: MyInput.setKey(MyInput.BTN_TAB, false); break;
                case Input.Keys.SHIFT_LEFT: MyInput.setKey(MyInput.BTN_SHIFT_LEFT, false); break;
                case Input.Keys.CONTROL_RIGHT: MyInput.setKey(MyInput.BTN_CTRL_RIGHT, false); break;
                case Input.Keys.CONTROL_LEFT: MyInput.setKey(MyInput.BTN_CTRL_LEFT, false); break;
                case Input.Keys.SHIFT_RIGHT: MyInput.setKey(MyInput.BTN_SHIFT_RIGHT, false); break;
            }
        }

        @Override
        protected void handleKeysHeld(IntArray heldKeys) {
            //TODO gomb nyomvatartásra folyamatosan végrahajtandó dolgokat ide
        }

        @Override
        protected void handleTouchDown(Vector3 touchPoint) {
            /*jump = false;
            slinkinessMinus = false;
            slinkinessPlus = false;
            left = false;
            right = false;*/

            int x = (int) touchPoint.x;
            int y = (int) touchPoint.y;

            if(x < width/3) {
                if (x < width/6) {
                    left = true;
                    right = false;
                } else {
                    right = true;
                    left = false;
                }
            } else if (x < 2*width/3) {
                if (y < height/2) {
                    slinkinessPlus = true;
                } else {
                    slinkinessMinus = true;
                }
            } else if (x > 2*width/3) {
                jump = true;
            }
        }

        @Override
        protected void handleTouchUp(Vector3 touchPoint) {

            int x = (int) touchPoint.x;
            int y = (int) touchPoint.y;

            if(x < width/3) {
                if (x < width/6) {
                    left = false;
                } else {
                    right = false;
                }
            } else if (x < 2*width/3) {
                if (y < height/2) {
                    slinkinessPlus = false;
                } else {
                    slinkinessMinus = false;
                }
            } else if (x > 2*width/3) {
                jump = false;
            }
        }

        @Override
        protected void handleTouchHeld(IntMap<Vector3> actualTouchPoints) {
            //TODO
        }

        @Override
        protected void handleTouchDragged(Vector3 touchPoint) {
            /*jump = false;
            slinkinessMinus = false;
            slinkinessPlus = false;
            left = false;
            right = false;*/

            //System.out.println("touch!");
            //System.out.println(width+"x"+height);



            int x = (int) touchPoint.x;
            int y = (int) touchPoint.y;

            if(x < width/3) {
                if (x < width/6) {
                    left = true;
                    right = false;
                } else {
                    right = true;
                    left = false;
                }
            } else if (x < 2*width/3) {
                if (y < height/2) {
                    slinkinessPlus = true;
                } else {
                    slinkinessMinus = true;
                }
            } else if (x >= 2*width/3) {
                jump = true;
            }

        }
    }

}
