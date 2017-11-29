package hu.marazmarci.belatheblob.entities;

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
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.main.GameMain.GameVariant;
import hu.marazmarci.belatheblob.states.levels.Level1;

import static hu.marazmarci.belatheblob.main.GameMain.time;
import static hu.marazmarci.belatheblob.states.levels.Level1.frame;
import static hu.marazmarci.belatheblob.states.levels.Level1.player;

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
		
		android = 0; //GameMain.android ? 40 : 0;
		
		//pop = GameMain.res.getSound("slimejump1");
		
		Texture tex = GameMain.res.getTexture("hud");
		heart = new TextureRegion(GameMain.res.getTexture("heart"));
		heart_empty = new TextureRegion(GameMain.res.getTexture("heart_empty"));
		
		overlay = new Texture(GameMain.WIDTH, GameMain.HEIGHT, Format.RGBA4444);
		TextureData smallObjTex = null;
		if (GameMain.gameVariant == GameVariant.ANNA) {
			smallObjTex = heart.getTexture().getTextureData();
			bigObject  =  GameMain.res.getTexture("ztepowerbank");
		} else if (GameMain.gameVariant == GameVariant.LIVIA) {
			smallObjTex = GameMain.res.getTexture("mushroom").getTextureData();
			bigObject  =  GameMain.res.getTexture("grandma");
		} else if (GameMain.gameVariant == GameVariant.MAG) {
			smallObjTex = GameMain.res.getTexture("beer").getTextureData();
			bigObject  =  GameMain.res.getTexture("kozel");
		} else { //GameVariant.DEFAULT
			smallObjTex = GameMain.res.getTexture("beer").getTextureData();
			bigObject  =  GameMain.res.getTexture("kozel");
		}
		
		bigObjX = GameMain.WIDTH/2f-bigObject.getWidth()/2f; bigObjY = GameMain.HEIGHT/2f-bigObject.getHeight()/2f;
		
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
		drawString(sb, GameMain.FPS+"", 615, 305+android);
		
		sb.end();
		
		
		
		//Level1.gameOver = true; //TODO remove
		//Level1.gameOverType = false;
		if (GameMain.gameOver) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			
			sr.begin(ShapeType.Filled);
			
			if (GameMain.gameOverType) { //HALÁL
				sr.setColor(1f, 0, 0, 0.5f);
				sr.rect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);
			} else { //WIN
				//TODO ANNA->TÖLTÕ, MAG->SÖR, LÍVIACUCC, PUBLIC CUCC, TheVR cucc???
				if (frame>nextTileDraw) {
					nextTileDraw = frame+Level1.random.nextInt(20)+10;
					//pop.play();
					//Pixmap.setBlending(Pixmap.Blending.SourceOver);
					overlay.draw(smallObject, Level1.random.nextInt(GameMain.WIDTH/smallObjectWidth+1)*smallObjectWidth, (Level1.random.nextInt(GameMain.HEIGHT/smallObjectHeight+2)-2)*smallObjectHeight); //TODO random
				}
				sr.setColor(0, 1f, 0, 0.5f);
				sr.rect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);
			}
			sr.end();
			
			if (!GameMain.gameOverType) {
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
