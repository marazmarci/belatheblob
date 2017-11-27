package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class RedCrystal extends Crystal {
	
	private static TextureRegion[] texs;

	public RedCrystal() {
		textureName = "redcrystal";
		tileSize = 16;
		initTexture();
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		RedCrystal.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return RedCrystal.texs;
	}

}
