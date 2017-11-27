package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Melon extends Collectible {
	
	private static TextureRegion[] texs;
	
	public Melon() {
		textureName = "dinnye";
		tileSize = 32;
		bodySize = 15.5f;
		initTexture();
		healthPoints = +2;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Melon.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Melon.texs;
	}
}
