package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Mushroom extends Collectible {
	
	private static TextureRegion[] texs;
	
	public Mushroom() {
		textureName = "mushroom";
		tileSize = 24;
		yOffset = -9;
		initTexture();
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Mushroom.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Mushroom.texs;
	}

}
