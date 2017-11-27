package hu.marazmarci.belatheblob.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class DoorAnimation extends B2DSprite {
	
	private static TextureRegion[] texs;
	
	public DoorAnimation() {
		textureName = "dooranim";
		tileSize = 61;
		tileSizeY = 85;
		shape = false;
		animationTime = 1/4f;
		//TODO bodySize, bodySizeY
		initTexture();
		hasBody = false;
	}
	
	public B2DSprite spawn(World world, float x0, float y0) {
		pos = new Vector2(x0,y0);
		return this;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		DoorAnimation.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return DoorAnimation.texs;
	}
	
}
