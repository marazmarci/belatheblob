package hu.marazmarci.belatheblob.entities;

import static hu.marazmarci.belatheblob.states.Level1.frame;
import static hu.marazmarci.belatheblob.main.Game.time;
import static hu.marazmarci.belatheblob.states.Level1.player;
import hu.marazmarci.belatheblob.main.Game;
import hu.marazmarci.belatheblob.main.Game.GameMode;
import hu.marazmarci.belatheblob.states.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HUD {
	
	//private TextureRegion container;
	private TextureRegion[] font, blocks;
	private TextureRegion crystal, heart, heart_empty;
	
	private Pixmap smallObject;
	private int smallObjectWidth, smallObjectHeight;
	private Texture bigObject, overlay;
	
	private int android;
	
	private long nextTileDraw = 0;
	
	//private Sound pop;
	
	float bigObjX, bigObjY;
	
	public HUD() {
		
		android = 0; //Game.android ? 40 : 0;
		
		//pop = Game.res.getSound("slimejump1");
		
		Texture tex = Game.res.getTexture("hud");
		heart = new TextureRegion(Game.res.getTexture("heart"));
		heart_empty = new TextureRegion(Game.res.getTexture("heart_empty"));
		
		overlay = new Texture(Game.WIDTH, Game.HEIGHT, Format.RGBA4444);
		TextureData smallObjTex = null;
		if (Game.gameMode == GameMode.ANNA) {
			smallObjTex = heart.getTexture().getTextureData();
			bigObject  =  Game.res.getTexture("ztepowerbank");
		} else if (Game.gameMode == GameMode.LIVIA) {
			smallObjTex = Game.res.getTexture("mushroom").getTextureData();
			bigObject  =  Game.res.getTexture("grandma");
		} else if (Game.gameMode == GameMode.MAG) {
			smallObjTex = Game.res.getTexture("beer").getTextureData();
			bigObject  =  Game.res.getTexture("kozel");
		} else { //GameMode.DEFAULT
			smallObjTex = Game.res.getTexture("beer").getTextureData();
			bigObject  =  Game.res.getTexture("kozel");
		}
		
		bigObjX = Game.WIDTH/2f-bigObject.getWidth()/2f; bigObjY = Game.HEIGHT/2f-bigObject.getHeight()/2f;
		
		if (!smallObjTex.isPrepared()) smallObjTex.prepare();
		smallObject = smallObjTex.consumePixmap();
		smallObjectWidth = smallObject.getWidth(); smallObjectHeight = smallObject.getHeight();
		
		smallObjTex.disposePixmap(); //TODO ?????
		
		blocks = new TextureRegion[3];
		for(int i = 0; i < blocks.length; i++) {
			blocks[i] = new TextureRegion(tex, 32 + i * 16, 0, 16, 16);
		}
		
		crystal = new TextureRegion(tex, 80, 0, 16, 16);
		
		font = new TextureRegion[13];
		for(int i = 0; i < 6; i++) {
			font[i] = new TextureRegion(tex, 32 + i * 9, 16, 9, 9);
		}
		for(int i = 0; i < 5; i++) {
			font[i + 6] = new TextureRegion(tex, 32 + i * 9, 25, 9, 9);
		}
		font[11] = new TextureRegion(tex, 32 + (6) * 9, 16, 9, 9);
		font[12] = new TextureRegion(tex, 32 + (5) * 9, 25, 9, 9);
		
	}
	
	public void render(SpriteBatch sb, ShapeRenderer sr) {
		
		sb.begin();
		
		// draw container
		////sb.draw(container, 32, 200);
		
		// draw blocks
		/*short bits = player.getBody().getFixtureList().first().getFilterData().maskBits;
		if((bits & B2DVars.BIT_RED_BLOCK) != 0) {
			sb.draw(blocks[0], 40, 208);
		}
		else if((bits & B2DVars.BIT_GREEN_BLOCK) != 0) {
			sb.draw(blocks[1], 40, 208);
		}
		else if((bits & B2DVars.BIT_BLUE_BLOCK) != 0) {
			sb.draw(blocks[2], 40, 208);
		}*/
		
		// draw crystal
		sb.draw(crystal, 7, 280+android);
		
		// draw crystal amount
		drawString(sb, player.getScore() + " / " + player.getMaxScore() + "  -  " + (Level1.collected==0?"":Level1.collected+""), 28, 283+android);
		
		//if(Level1.collected != 0) drawString(sb, Level1.collected+"", 10, 266+android);
		
		
		drawString(sb, ((float)(int)(time*10)/10)+"", 336, 302+android);
		
		//drawString(sb, "HP: " + player.HP, 0, 300);
		
		for (int i=0; i<player.HP; i++) {
			sb.draw(heart, 8 + i*16, 300+android);
		}
		for (int i = player.HP; i<20; i++) {
			sb.draw(heart_empty, 8 + i*16, 300+android);
		}
		
		//if (Level1.debug) {
		drawString(sb, Game.FPS+"", 615, 305+android);
		
		sb.end();
		
		
		
		//Level1.gameOver = true; //TODO remove
		//Level1.gameOverType = false;
		if (Game.gameOver) {
			Gdx.gl20.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			sr.begin(ShapeType.Filled);
			
			if (Game.gameOverType) { //HALÁL
				sr.setColor(1f, 0, 0, 0.5f);
				sr.rect(0, 0, Game.WIDTH, Game.HEIGHT);
			} else { //WIN
				//TODO ANNA->TÖLTÕ, MAG->SÖR, LÍVIACUCC, PUBLIC CUCC, TheVR cucc???
				if (frame>nextTileDraw) {
					nextTileDraw = frame+Level1.random.nextInt(20)+10;
					//pop.play();
					//Pixmap.setBlending(Pixmap.Blending.SourceOver);
					overlay.draw(smallObject, Level1.random.nextInt(Game.WIDTH/smallObjectWidth+1)*smallObjectWidth, (Level1.random.nextInt(Game.HEIGHT/smallObjectHeight+2)-2)*smallObjectHeight); //TODO random
				}
				sr.setColor(0, 1f, 0, 0.5f);
				sr.rect(0, 0, Game.WIDTH, Game.HEIGHT);
			}
			sr.end();
			
			if (!Game.gameOverType) {
				sb.begin();
				sb.draw(overlay, 0, 0);
				sb.draw(bigObject, bigObjX, bigObjY);
				sb.end();
			}
			
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
		
		
		
	}
	
	private void drawString(SpriteBatch sb, String s, float x, float y) {
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '/') c = 10; //. -> 11   : -> 12
			else if(c == '.') c = 11;
			else if(c == ':') c = 12;
			else if(c >= '0' && c <= '9') c -= '0';
			else continue;
			sb.draw(font[c], x + i * 9, y);
		}
	}
	
}
