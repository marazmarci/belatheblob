package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Key1 extends Key {
	
	private static TextureRegion[] texs;
	
	public Key1() {
		textureName = "key1";
		tileSize = 24;
		initTexture();
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Key1.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Key1.texs;
	}

}
