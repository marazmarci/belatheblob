package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Cheese extends Collectible {
	
	private static TextureRegion[] texs;

	public Cheese() {
		textureName = "sajt";
		tileSize = 32;
		shape = false;
		initTexture();
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Cheese.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Cheese.texs;
	}

}
