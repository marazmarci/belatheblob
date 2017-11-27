package hu.marazmarci.belatheblob.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import hu.marazmarci.belatheblob.main.GameMain;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;

public class FreeBlock extends B2DSprite {
	
	private static TextureRegion[] texs;
	
	public FreeBlock(String type) {
		tileSize = 32;
		textureName = type;
		try {
			GameMain.res.loadTexture(type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initTexture(); //TODO a static texture módszerrel ez nem fog mûködni többféle blokkra
	}
	
	public B2DSprite spawn(World world, float x0, float y0) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyType.StaticBody;
		
		PolygonShape sh = new PolygonShape();
  		sh.setAsBox(tileSize/2/PPM, tileSize/2/PPM);
		
		fdef.shape = sh;
		fdef.filter.categoryBits = BIT_GROUND;
		fdef.filter.maskBits = BIT_BLOB;
			
		bdef.position.set(x0/PPM,y0/PPM);
			
		body = world.createBody(bdef);
		body.createFixture(fdef).setUserData("ground");
		
		return this;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		FreeBlock.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return FreeBlock.texs;
	}
	
}
