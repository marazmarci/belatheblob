package hu.marazmarci.belatheblob.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import hu.marazmarci.belatheblob.entities.collectibles.Collectible;
import hu.marazmarci.belatheblob.handlers.Animation;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.states.levels.Level1;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;

public abstract class B2DSprite {

	protected short BIT = BIT_SPRITE;
	protected Body body;
	protected Animation animation;
	protected float width;
	protected float height;
	protected boolean isOnGround = false;
	//protected long frame = 0;
	public boolean removed = false;
	public boolean brainfuck = false;
	public String textureName;
	protected int tileSize = 16;
	protected int tileSizeY = 0;
	protected int textureOffsetX = 0;
	protected float textureOffsetY = 0;
	protected int xOffset = 0;
	protected int yOffset = 0;
	protected float bodySize = 0;
	protected float bodySizeY = 0;
	protected boolean shape = true; //true: circle, false: rectangle 
	protected float animationTime = 1/12f;
	protected boolean singleFrame = false;
	protected TextureRegion tex;
	//protected static TextureRegion[] texs;
	boolean flip = false;
	public float red = 0f;
	protected boolean hasBody = true;
	Vector2 pos;
	protected int textureScale = 1;
	protected boolean flipped = false;
	
	public B2DSprite() {}
	
	public void setAnimation(TextureRegion[] frames, float delay) {
		int length = frames.length;
		if (length == 1) {
			singleFrame = true;
			this.tex = frames[0];//setAnimation(frames[0], delay);
		} else {
			animation = new Animation(length, delay);
			//texs = frames;
			setTexture(frames);
			//animation.setFrames(frames, getClass(), delay);
		}
		width = frames[0].getRegionWidth();
		height = frames[0].getRegionHeight();
	}
	
	/*public void setAnimation(TextureRegion tex, float delay) {
		singleFrame = true;
		this.tex = tex;
		width = tex.getRegionWidth();
		height = tex.getRegionHeight();
	}*/
	
	public void update(float dt) {
		if(!singleFrame) {
			animation.update(dt);
			//System.out.println("B2DSprite.update(); !singleFrame !!!! class = "+getClass());
			//return;
		}
		//System.out.println("B2DSprite.update(); SINGLE FRAME class = "+getClass());
	}
	
	public void render(SpriteBatch sb) {
		if (red != 0) sb.setColor(1, 1-red, 1-red, 1);
		//System.out.println("B2DSprite.render(); class = "+getClass());
		sb.draw(singleFrame?tex:getTexture()[animation.currentFrame],//animation.getFrame(),
				getPosition().x + textureOffsetX - width / 2,
				getPosition().y + textureOffsetY - height / 2);
		//sb.draw(texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
		if (red != 0) sb.setColor(1,1,1,1);
	}
	
	public void remove() {
		removed = true;
		body.getWorld().destroyBody(body);
	}
	
	public void removeLater(int frames) {
		Level1.removeSpriteLater(this, frames);
	}

	public B2DSprite spawn(World world, float x0, float y0) {
		
		if (hasBody) {
			
			BodyDef bdef = new BodyDef();
			FixtureDef fdef = new FixtureDef();
			bdef.type = BodyType.StaticBody;
			Shape sh = null;
			
			try { sh = (shape?CircleShape.class:PolygonShape.class).newInstance(); }
			catch (Exception e) { e.printStackTrace(); }
			
			float rX = bodySize==0?tileSize/2/PPM:bodySize/PPM;
			float rY = bodySize==0 ? (tileSizeY==0 ? tileSize : tileSizeY)/2/PPM : (bodySizeY==0 ? bodySize : bodySizeY)/PPM;
			
			if (shape) sh.setRadius(rX);
			else {((PolygonShape)sh).setAsBox(rX, rY);}

			fdef.shape = sh;
			fdef.isSensor = this instanceof Collectible;
			fdef.filter.categoryBits = (this instanceof Collectible) ? BIT_COLLECTIBLE : BIT_SPRITE;
			fdef.filter.maskBits = BIT_BLOB;

			bdef.position.set((x0+xOffset)/PPM,(y0+yOffset)/PPM);

			body = world.createBody(bdef);
			body.createFixture(fdef).setUserData(this);

		} else {
			pos = new Vector2(x0,y0);
		}
		
		return this;
	}
	
	public void initTexture() {
		//System.out.println("B2DSprite.initTexture(); class = "+getClass());
		//System.out.println("B2DSprite.initTexture(); textureName = "+textureName);
		Texture tex = GameMain.res.getTexture(textureName);
		TextureRegion[] sprites = TextureRegion.split(tex, tileSize, tileSizeY==0?tileSize:tileSizeY)[0];
		setAnimation(sprites, animationTime);
	}
	
	protected abstract void setTexture(TextureRegion[] texs);
	protected abstract TextureRegion[] getTexture();
	
	public void setOnGround(boolean b) { isOnGround = b; }
	public void setPosition (Vector2 v) { body.setTransform(v, body.getAngle());}
	public void setPosition (float x, float y) { body.setTransform(x, y, body.getAngle()); }
	
	public boolean isOnGround() { return isOnGround; }
	public Body getBody() { return body; }
	public Vector2 getB2DPosition() {
		/*System.out.println("getB2DPosition() class = "+getClass());
		System.out.println("getB2DPosition() hasBody = "+hasBody);
		if (hasBody) System.out.println("getB2DPosition() body.getPosition() = "+body.getPosition());
		if (!hasBody) System.out.println("getB2DPosition() pos = "+pos);*/
		return hasBody ? body.getPosition() : new Vector2(pos.x/PPM, pos.y/PPM);
	}
	public Vector2 getPosition() {
		return new Vector2(hasBody ? body.getPosition().x*PPM : pos.x, hasBody ? body.getPosition().y*PPM : pos.y);
	}
	public float getWidth() { return width; }
	public float getHeight() { return height; }
	
}
