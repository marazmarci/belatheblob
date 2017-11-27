package hu.marazmarci.belatheblob.handlers;

import hu.marazmarci.belatheblob.entities.B2DSprite;
import hu.marazmarci.belatheblob.states.Level1;

public class SpriteRemovalEntry {
	
	B2DSprite sprite;
	int frames;
	int allFrames;

	public SpriteRemovalEntry(B2DSprite sprite, int frames) {
		this.sprite = sprite;
		this.frames = frames;
		this.allFrames = frames;
	}
	
	public boolean update() {
		if (frames>0) { frames--; sprite.red = frames/(float)allFrames; return false;}
		else if (frames==0) {
			sprite.remove();
			Level1.sprites.removeValue(sprite, true);
			Level1.hiddenSprites.removeValue(sprite, true);
			//Level1.removeSprite(sprite);
			//System.out.println("SpriteRemovalEntry FINISHED!!!!!!!!!!!!!!!");
		}
		return true;
	}
	
}
