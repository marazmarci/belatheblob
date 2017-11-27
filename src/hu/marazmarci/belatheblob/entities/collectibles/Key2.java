package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Key2 extends Key {
	
	private static TextureRegion[] texs;

	public Key2() {
		textureName = "key2";
		tileSize = 24;
		initTexture();
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Key2.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Key2.texs;
	}

}
