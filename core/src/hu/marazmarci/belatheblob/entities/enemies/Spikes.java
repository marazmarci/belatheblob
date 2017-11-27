package hu.marazmarci.belatheblob.entities.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;

public class Spikes extends Enemy {
	
	private static TextureRegion[] texs;
	
	public Spikes() {
		textureName = "spikes";
		tileSize = 32;
		initTexture();
		BIT = BIT_ENEMY;
		removable = false;
		damage = 3;
	}

	public Spikes spawn(World world, float x0, float y0) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		bdef.type = BodyType.StaticBody;
		bdef.fixedRotation = true;
		bdef.position.set(x0/PPM, y0/PPM);
		fdef.density = 0.4f;
		shape.setRadius(10/PPM);
		fdef.shape = shape;
		fdef.friction = 0.4f;
		fdef.isSensor = true;
		fdef.filter.maskBits = BIT_BLOB;
		fdef.filter.categoryBits = BIT;
		body = world.createBody(bdef);
		body.createFixture(fdef).setUserData(this);
		return this;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Spikes.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Spikes.texs;
	}
}
