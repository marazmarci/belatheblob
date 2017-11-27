package hu.marazmarci.belatheblob.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import hu.marazmarci.belatheblob.handlers.FadeTask;
import hu.marazmarci.belatheblob.handlers.Task;
import hu.marazmarci.belatheblob.main.Game;
import hu.marazmarci.belatheblob.states.Level1;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;
import static hu.marazmarci.belatheblob.states.Level1.frame;

public class Cat extends B2DSprite {
	
	private static TextureRegion[] texs;
	
	//TODO texture flip in direction
	
	boolean beszippantva = false;
	DistanceJoint joint;
	boolean insideBlob = false;
	Body blobLinkBody;
	
	public Cat() {
		textureName = "cat";
		tileSize = 28;
		tileSizeY = 23;
		initTexture();
		//autoJumpForceX = -3/4f;
		//autoJumpForceXrandomness = 6/4f;
		//autoJumpForceY = 3.5f/4f;
	}

	public Cat spawn(World world, float x0, float y0) {
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		bdef.type = BodyType.DynamicBody;
		//bdef.fixedRotation = true;
		bdef.position.set(x0/PPM, y0/PPM);
		fdef.density = 0.05f;
		shape.setRadius(7.5f/PPM);
		fdef.shape = shape;
		fdef.friction = 0.05f;
		fdef.filter.maskBits = BIT_BLOB | BIT_GROUND | BIT_WALL | BIT_CEIL;
		fdef.filter.categoryBits = BIT_BUNNY;
		body = world.createBody(bdef);
		body.createFixture(fdef).setUserData(this);
		return this;
	}
	
	public void update(float dt) {
		//frame++;
		animation.update(dt);
		//System.out.println("CAT DIST: " + Level1.player.dstLinear(getPosition()));
		if (frame%10==0 && !beszippantva && Level1.player.dstLinear(getPosition()) < 100) {
			System.out.println("CICA CICA CICA CICA CICA");
			
			beszippantva = true;
			
			if (!Level1.debug && Level1.player.getScore() < 85) {
				//TODO nem gyűjtöttél össze elég piros kristályt felirat
				Game.res.getSound("slimehit").play();
				Level1.player.damage(20);
			} else {
				
				DistanceJointDef jdef = new DistanceJointDef();

				final float dst = Math.abs(Level1.player.coreBall.getPosition().add(0,3/PPM).dst(getB2DPosition()));
				System.out.println("START DST = "+dst);
				jdef.length = dst;
				jdef.bodyA = Level1.player.coreBall;
				jdef.bodyB = this.body;
				jdef.localAnchorB.y = 3/PPM;
				jdef.collideConnected = false;
				jdef.dampingRatio = 1f;
				jdef.frequencyHz = 25f;
				joint = (DistanceJoint) Level1.world.createJoint(jdef);

				insideBlob = true;

				/*BodyDef bdef = new BodyDef();
				FixtureDef fdef = new FixtureDef();
				CircleShape shape = new CircleShape();
				bdef.type = BodyType.DynamicBody;
				bdef.fixedRotation = true;
				bdef.position.set(x0/PPM, y0/PPM);
				fdef.density = 0.05f;
				shape.setRadius(12f/PPM);
				fdef.shape = shape;
				fdef.friction = 0.05f;
				body = Level1.world.createBody(bdef);
				body.createFixture(fdef);*/

				Level1.theCat = this;
				Level1.tasks.add(new FadeTask<Cat>(this, frame+70) {

					float length;

					@Override
					public void run() {
						Level1.tasks.add(new Task<Cat>(data, frame+5) {
							@Override
							public void run() {
								joint.setDampingRatio(0.2f);
								joint.setFrequency(1.5f);
							}
						});
					}

					@Override
					public void update() {
						length = (1 - getVal()) * dst;
						System.out.println("rope: "+length);
						data.joint.setLength(length);

					}
				});
				/*
				Level1.tasks.add(new FadeTask<Music>(m, Level1.frame+240){

				public void run() {data.stop();}

				@Override
				public void update() {
					data.setVolume(1-(float)(Level1.frame-start)/(float)(when-start));
					System.out.println("SET VOLUME: "+(1-(float)(Level1.frame-start)/(float)(when-start)));
				}
			});
				 */
			}
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		//if (red!=0) sb.setColor(1, 1-red, 1-red, 1);
		/*sb.draw(singleFrame?tex:getTexture()[animation.currentFrame],
				getPosition().x + textureOffsetX - width / 2,
				getPosition().y + textureOffsetY - height / 2);*/
		if (insideBlob) {
			sb.draw(texs[animation.currentFrame], getPosition().x + textureOffsetX - width / 2, getPosition().y + textureOffsetY - height / 2, 14, 11.5f, 28, 23, 1, 1, body.getAngle()*180/3.14159265359f);
		} else {
			sb.draw(texs[animation.currentFrame],
					getPosition().x + textureOffsetX - width / 2,
					getPosition().y + textureOffsetY - height / 2);
		}
		//if (red!=0) sb.setColor(1,1,1,1);
	}

	@Override
	protected void setTexture(TextureRegion[] texs) {
		Cat.texs = texs;
		
	}

	@Override
	protected TextureRegion[] getTexture() {
		return Cat.texs;
	}
}
