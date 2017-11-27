package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Pill2 extends Collectible {
	
	private static TextureRegion[] texs;
	
	public Pill2() {
		textureName = "pill2";
		tileSize = 24;
		//yOffset = -9;
		initTexture();
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Pill2.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Pill2.texs;
	}

}
