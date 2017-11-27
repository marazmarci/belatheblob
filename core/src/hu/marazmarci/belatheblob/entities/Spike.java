package hu.marazmarci.belatheblob.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Spike extends B2DSprite {
	
	private static TextureRegion[] texs;

	//TODO animáció implementálása ide!
	
	public Spike() {
		textureName = "spike";
		tileSize = 32;
		initTexture();
		hasBody = false;
	}
	
	public B2DSprite spawn(World world, float x0, float y0) {
		pos = new Vector2(x0,y0);
		return this;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Spike.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Spike.texs;
	}
}
