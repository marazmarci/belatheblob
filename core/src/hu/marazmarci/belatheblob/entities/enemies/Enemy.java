package hu.marazmarci.belatheblob.entities.enemies;

import hu.marazmarci.belatheblob.entities.B2DSprite;
import hu.marazmarci.belatheblob.states.levels.Level1;

import static hu.marazmarci.belatheblob.states.levels.Level1.frame;

public abstract class Enemy extends B2DSprite {
	
	boolean autoJumpEnabled = false;
	public boolean autoJumpMode = true; //true: random; false: a blob felé
	int autoJumpInterval;
	public float autoJumpForceX = -3;
	public float autoJumpForceXrandomness = 6;
	public float autoJumpForceY = 3.5f;
	public float autoJumpForceYrandomness = 0;
	public int cooldown = 0;
	protected int damage = 1;
	public boolean removable = true;
	public int HP = 1;
	
	public int getDamage() {
		return damage;
	}
	
	public void update(float dt) {
		if(cooldown>0) cooldown--;
		super.update(dt);
		if (autoJumpEnabled) {
			//frame++;
			float x = autoJumpForceX+Level1.random.nextFloat()*autoJumpForceXrandomness;
			if (x<0 && !flipped) {
				flipped = true;
			} else if (x>0 && flipped) {
				flipped = false;
			}
			if (frame%autoJumpInterval == 0) body.applyForceToCenter(x, autoJumpForceY+Level1.random.nextFloat()*autoJumpForceYrandomness, true);
		}
	}
	
	public Enemy setAutoJump(boolean enabled, int interval) {
		//if (this instanceof BossBunny) System.out.println("Set BossBunny's autojump to "+interval+";  Enemy.java:49");
		this.autoJumpEnabled = enabled;
		this.autoJumpInterval = interval;
		return this;
	}

}
