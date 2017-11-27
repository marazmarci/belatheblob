package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Bean extends Collectible {
	
	private static TextureRegion[] texs;
	
	public Bean() {
		textureName = "bean";
		tileSize = 16;
		initTexture();
		healthPoints = +1;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Bean.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Bean.texs;
	}

}
