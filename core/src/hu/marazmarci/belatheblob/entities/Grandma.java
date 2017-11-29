package hu.marazmarci.belatheblob.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import hu.marazmarci.belatheblob.handlers.ContentManager;
import hu.marazmarci.belatheblob.handlers.Task;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.states.levels.Level1;

public class Grandma extends B2DSprite {
	
	private static TextureRegion[] texs;
	
	public boolean cat = false;
	public boolean talking = false;
	public boolean talkingDisabled = false;
	
	private long frame = 0;
	private final int animationLength = 10;
	
	private static String snd1 = "ilonka1";
	private static String snd2 = "ilonka2";
	
	public boolean ID = true; //true: elsõ mama
	
	public Grandma() {
		textureName = "grandma";
		tileSize = 48;
		tileSizeY = 80;
		//textureOffsetY = -4;
		shape = false;
		initTexture();
		hasBody = false;
	}
	
	public B2DSprite spawn(World world, float x0, float y0) {
		pos = new Vector2(x0,y0);
		return this;
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(texs[((talking?(frame%animationLength > animationLength/2):true)?0:1)+(cat?2:0)],
				getPosition().x - width / 2,
				getPosition().y - 4 - height / 2);
	}
	
	public void update(float dt) {
		frame++;
		if (frame%20==0 && !talking && !talkingDisabled) {
			//System.out.println("GRANDMA DISTANCE = " + Math.abs(pos.dst(Level1.player.coreBall.getPosition())));
			//System.out.println("GRANDMA DISTANCE = " + Level1.player.dstLinear(pos));
			if (Level1.player.dstLinear(pos) < 100) {
				talking = true;
				talkingDisabled = true;
				
				if (!ID && Level1.theCat!=null) {
					cat = true;
					Level1.removeSprite(Level1.theCat);
				}
				
				GameMain.res.playSoundUnique(ID?snd1:snd2, new Task<Grandma>(this, Level1.frame + (ID?650:190)* GameMain.FPS/60L ) {
				
				@Override
				public void run() {
					data.talking = false;
					ContentManager.playingSounds.removeValue(ID?snd1:snd2, true);
					GameMain.res.playSound("magic2");
					if (data.ID) {
						MapLayers ml = Level1.tiledMap.getLayers();
						ml.remove(ml.get("interactive_mamintul"));
						Array<Body> bodies = Level1.interactive.get("interactive_mamintul");
						for (Body b : bodies) Level1.bodiesToRemove.add(b);
					} else {
						//TODO A JÁTÉK VÉGE!!!!!!!!!!!!!
						GameMain.gameOver = true;
						GameMain.gameOverType = false;
						GameMain.res.playBrainfuckMusic();
					}
				}});
			}
		}
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Grandma.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Grandma.texs;
	}
}
