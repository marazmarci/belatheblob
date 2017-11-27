package hu.marazmarci.belatheblob.entities.collectibles;

import static hu.marazmarci.belatheblob.handlers.B2DVars.BIT_COLLECTIBLE;
import hu.marazmarci.belatheblob.entities.B2DSprite;

public abstract class Collectible extends B2DSprite {
	
	public String action;
	public int delay = 60;
	public int healthPoints = 0;
	public boolean beforeBoss = false;
	public boolean afterBoss = false;
	public boolean zokni = false;
	public boolean deslinkificator = false;

	public Collectible() {
		super();
		BIT = BIT_COLLECTIBLE;
	}

}
