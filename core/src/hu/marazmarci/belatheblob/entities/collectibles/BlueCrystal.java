package hu.marazmarci.belatheblob.entities.collectibles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class BlueCrystal extends Crystal {
	
	private static TextureRegion[] texs;
	
	public BlueCrystal() {
		textureName = "bluecrystal";
		tileSize = 16;
		initTexture();
		healthPoints = +2;
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		BlueCrystal.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return BlueCrystal.texs;
	}
}
