package hu.marazmarci.belatheblob.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import hu.marazmarci.belatheblob.entities.collectibles.Collectible;
import hu.marazmarci.belatheblob.entities.enemies.BossBunny;
import hu.marazmarci.belatheblob.entities.enemies.Enemy;
import hu.marazmarci.belatheblob.main.Game;
import hu.marazmarci.belatheblob.states.Level1;

import static hu.marazmarci.belatheblob.main.Game.res;

public class MyContactListener implements ContactListener {
	
	//private boolean playerOnGround = false;
	public static Array<Body> blobOnGround;
	//int cPrev = 0;

	public void beginContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if ( fa==null || fb == null) return;
		
		boolean gndA = isGround(fa); boolean gndB = isGround(fb);
		boolean blobA = isBlob(fa), blobB = isBlob(fb);
		
		//if ((fa.getUserData()!=null && fa.getUserData().equals("foot")) || (fb.getUserData()!=null && fb.getUserData().equals("foot"))) playerOnGround = true;
		
		if (blobA && gndB) {
			Body b = fa.getBody();
			if (!blobOnGround.contains(b,true)) blobOnGround.add(b);
		} else if (blobB && gndA) {
			Body b = fb.getBody();
			if (!blobOnGround.contains(b,true)) blobOnGround.add(b);
		}
		
		else if (isEnemy(fb)) {
			//Bunny bunny = (Bunny) fb.getUserData();
			Enemy enemy = (Enemy) fb.getUserData();
			if (gndA) {
				enemy.setOnGround(true);
			} else if (blobA) {
				System.out.println();
				//System.out.println("BUNNY.COOLDOWN = "+bunny.cooldown);
				//if (!Level1.contactingEnemies.contains(enemy, true) && enemy.cooldown == 0) { handleEnemyCollision(enemy); }
				if (enemy.cooldown == 0) { handleEnemyCollision(enemy); }
			}
		} else if (isEnemy(fa)) {
			//Bunny bunny = (Bunny) fb.getUserData();
			Enemy enemy = (Enemy) fa.getUserData();
	  	    if (gndB) {
	  	    	enemy.setOnGround(true);
		    } else if (blobB) {
		    	//System.out.println("BUNNY.COOLDOWN = "+bunny.cooldown);
				if (!Level1.contactingEnemies.contains(enemy, true) && enemy.cooldown == 0) {
					//System.out.println("START CONTACT & FIGHT");
					handleEnemyCollision(enemy);
				}
		    }
		} 
		
		  else if (isCollectible(fa) && blobB) {
			Level1.collect((Collectible)fa.getUserData());
		} else if (isCollectible(fb) && blobA) {
			Level1.collect((Collectible)fb.getUserData());
		}
		
		//System.out.println("Begin Contact: "+fa.getUserData()+", "+fb.getUserData());
	}

	public void endContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if ( fa==null || fb == null) return;
		
		boolean gndA = isGround(fa); boolean gndB = isGround(fb);
		boolean blobA = isBlob(fa), blobB = isBlob(fb);
		
		//if ((fa.getUserData()!=null && fa.getUserData().equals("foot")) || (fb.getUserData()!=null && fb.getUserData().equals("foot"))) playerOnGround = false;
		
		if (isBlob(fa) && gndB) {
			blobOnGround.removeValue(fa.getBody(),true);
		} else if (isBlob(fb) && gndA) {
			blobOnGround.removeValue(fb.getBody(),true);
		}
		
		else if (isEnemy(fb)) {
			//Bunny bunny = (Bunny) fb.getUserData();
			Enemy enemy = (Enemy) fb.getUserData();
			if (gndA) {
				enemy.setOnGround(false);
			} else if (blobA) {
				//if (Level1.contactingBunnies.contains(bunny, true)) System.out.println("END CONTACT");
				Level1.contactingEnemies.removeValue(enemy,true);
				Level1.contactingEnemies.clear();
			}
		} else if (isEnemy(fa)) {
			//Bunny bunny = (Bunny) fa.getUserData();
			Enemy enemy = (Enemy) fa.getUserData();
	  	    if (gndB) {
			    enemy.setOnGround(false);
		    } else if (blobB) {
		    	//if (Level1.contactingBunnies.contains(bunny, true)) System.out.println("END CONTACT");
				Level1.contactingEnemies.removeValue(enemy,true);
				Level1.contactingEnemies.clear();
		    }
		}
		
		/*int c = blobOnGround.size();
		if (c != cPrev) {
			cPrev = c;
			System.out.println(c+" blob bodies on ground");
		}*/
		//System.out.println("End Contact: "+fa.getUserData()+", "+fb.getUserData());
	}
	
	//TODO damage + piros blob villanás
	//TODO Bunny 
	public static void handleEnemyCollision(Enemy enemy) {
		if (Level1.player.HP == 0) return;
		//Game.res.getSound("slimehit").play();
		Level1.contactingEnemies.add(enemy);
		enemy.cooldown = enemy instanceof BossBunny ? 90 : 60;
		//System.out.println("bunny is "+(bunny.isOnGround()?"":"NOT ")+"on ground");
		if (/*bunny.isOnGround() &&*/ Level1.player.isOverEnemy(enemy) && enemy.removable) {
			Game.res.getSound("hit").play();
			enemy.HP--;
			if (enemy.HP == 0) {
				if (enemy instanceof BossBunny) {
					res.stopBossMusic();
					
				}
				enemy.removeLater(5);
			}
		} else {
			Level1.player.damage(enemy.getDamage());
			Game.res.getSound("slimehit").play();
		}
	}
	
	boolean isEnemy(Fixture f) { return f.getUserData()!=null && (f.getUserData() instanceof Enemy); }
	//boolean isBunny(Fixture f) { return f.getUserData()!=null && (f.getUserData() instanceof Bunny); }
	boolean isBlob(Fixture f) { return f.getUserData()!=null && f.getUserData().equals("blob"); }
	boolean isBlobCore(Fixture f) { return f.getUserData()!=null && f.getUserData().equals("blobCore"); }
	boolean isGround(Fixture f) { return f.getUserData()!=null && f.getUserData().equals("ground"); }
	boolean isCollectible(Fixture f) { return f.getUserData()!=null && (f.getUserData() instanceof Collectible); }
	//boolean is(Class<? extends B2DSprite> clazz, Fixture f) { return f.getUserData()!=null && (f.getUserData().getClass().equals(clazz)); }
	
	//public boolean isPlayerOnGround() { return playerOnGround; }

	public void preSolve(Contact contact, Manifold oldManifold) {}
	public void postSolve(Contact contact, ContactImpulse impulse) {}

}
