package hu.marazmarci.belatheblob.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;
import static hu.marazmarci.belatheblob.states.levels.Level1.random;

public class Bunny extends Enemy {
	
	public static TextureRegion[] texs;
	Color randomColor = new Color(1f,1f,1f,1f);
	boolean colored = false;
	
	//TODO texture flip in direction
	
	public Bunny() {
		textureName = "bunny";
		tileSize = 32;
		initTexture();
		BIT = BIT_BUNNY;
		if (random.nextInt(10)==5) {
			colored = true;
			damage = 2;
			randomColor.set(random.nextFloat()/1.6f+0.375f, random.nextFloat()/1.6f+0.375f, random.nextFloat()/1.6f+0.375f, 1f);
		}
	}

	public Bunny spawn(World world, float x0, float y0) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		bdef.type = BodyType.DynamicBody;
		bdef.fixedRotation = true;
		bdef.position.set(x0/PPM, y0/PPM);
		fdef.density = 0.4f;
		shape.setRadius(13.5f/PPM);
		fdef.shape = shape;
		fdef.friction = 0.4f;
		fdef.filter.maskBits = BIT_BLOB | BIT_GROUND | BIT_WALL | BIT_CEIL;
		fdef.filter.categoryBits = BIT;
		body = world.createBody(bdef);
		body.createFixture(fdef).setUserData(this);
		return this;
	}
	
	public void render(SpriteBatch sb) {
		if (colored) sb.setColor(randomColor);
		if (red!=0) sb.setColor(1, 1-red, 1-red, 1);
		//System.out.println("B2DSprite.render(); class = "+getClass());
		sb.draw(getTexture()[animation.currentFrame],//animation.getFrame(),
				getPosition().x - 2 - width / 2,
				getPosition().y + 2 - height / 2);
		//sb.draw(texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		if (colored || red!=0) sb.setColor(1,1,1,1);
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Bunny.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Bunny.texs;
	}
}
