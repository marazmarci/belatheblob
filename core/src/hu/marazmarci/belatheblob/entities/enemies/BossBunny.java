package hu.marazmarci.belatheblob.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.states.levels.Level1;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;

public class BossBunny extends Enemy {
	
	private static TextureRegion[] texs;
	
	//TODO texture flip in direction
	
	public BossBunny() {
		textureName = "bossbunny";
		tileSize = 32;
		textureOffsetX = -2;
		textureOffsetY = 2;
		initTexture();
		BIT = BIT_BUNNY;
		HP = 6; //TODO 6
		damage = 2;
	}
	
	public void remove() {
		removed = true;
		GameMain.res.playSound("magic2");
		MapLayers ml = Level1.tiledMap.getLayers();
		ml.remove(ml.get("interactive_nyulontul"));
		ml.remove(ml.get("interactive_nyulontul_nocollision"));
		Array<Body> bodies = Level1.interactive.get("interactive_nyulontul");
		for (Body b : bodies) Level1.bodiesToRemove.add(b);
		
		body.getWorld().destroyBody(body);
		Level1.setDay();
	}

	public BossBunny spawn(World world, float x0, float y0) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		//PolygonShape shape = new PolygonShape();
		CircleShape shape = new CircleShape();
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;
		bdef.position.set(x0/PPM, y0/PPM);
		fdef.density = 0.4f;
		//shape.setAsBox(13/PPM, 13/PPM);
		shape.setRadius(13.5f/PPM);
		fdef.shape = shape;
		fdef.friction = 0.4f;
		fdef.filter.maskBits = BIT_BLOB | BIT_GROUND | BIT_WALL | BIT_CEIL;
		fdef.filter.categoryBits = BIT;
		body = world.createBody(bdef);
		body.createFixture(fdef).setUserData(this);
		return this;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		BossBunny.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return BossBunny.texs;
	}
}
