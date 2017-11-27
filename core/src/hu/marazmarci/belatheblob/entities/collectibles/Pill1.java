package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Pill1 extends Collectible {
	
	private static TextureRegion[] texs;
	
	public Pill1() {
		textureName = "pill1";
		tileSize = 24;
		//yOffset = -9;
		initTexture();
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Pill1.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Pill1.texs;
	}

}
