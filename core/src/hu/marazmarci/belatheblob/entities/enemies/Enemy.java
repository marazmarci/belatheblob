package hu.marazmarci.belatheblob.entities.enemies;

import hu.marazmarci.belatheblob.Prog3HF;
import hu.marazmarci.belatheblob.entities.B2DSprite;
import hu.marazmarci.belatheblob.states.levels.Level1;

import static hu.marazmarci.belatheblob.states.levels.Level1.frame;
import static hu.marazmarci.belatheblob.states.levels.Level1.player;

public abstract class Enemy extends B2DSprite {

	public static boolean jumpTowardsBlob = false;
	
	private boolean autoJumpEnabled = false;
	//public boolean autoJumpMode = true; //true: random; false: a blob felé
	private int autoJumpInterval;
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
	    try{
            if(cooldown>0) cooldown--;
            super.update(dt);
            if (autoJumpEnabled) {
                //frame++;
                @Prog3HF // hát ez nem lett túl szép :P
                         // itt írtam meg, hogy Béla felé ugorjanak a nyulak
                float x;
                if (jumpTowardsBlob) {
                    float dx = player.getPosX() - body.getPosition().x;
                    if (dx < 0) x = autoJumpForceX;
                    else x = -autoJumpForceX;
                } else {
                    x = autoJumpForceX + Level1.random.nextFloat() * autoJumpForceXrandomness;
                }
                if (x<0 && !flipped) {
                    flipped = true;
                } else if (x>0 && flipped) {
                    flipped = false;
                }
                if (frame%autoJumpInterval == 0) body.applyForceToCenter(x, autoJumpForceY+Level1.random.nextFloat()*autoJumpForceYrandomness, true);
            }
	    } catch (Exception e) {
	        e.printStackTrace();
        }

	}
	
	public Enemy setAutoJump(boolean enabled, int interval) {
		//if (this instanceof BossBunny) System.out.println("Set BossBunny's autojump to "+interval+";  Enemy.java:49");
		this.autoJumpEnabled = enabled;
		this.autoJumpInterval = interval;
		return this;
	}

}
