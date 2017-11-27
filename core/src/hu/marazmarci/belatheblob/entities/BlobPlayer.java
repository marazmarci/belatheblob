package hu.marazmarci.belatheblob.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;
import hu.marazmarci.belatheblob.entities.enemies.Enemy;
import hu.marazmarci.belatheblob.main.Game;
import hu.marazmarci.belatheblob.states.Level1;

import static hu.marazmarci.belatheblob.handlers.B2DVars.*;

public class BlobPlayer {
	
	//Object ID;
	
	int numberOfOuterSpheres = 14;
	float surfaceBallRadius = 7.5f / PPM;
	float coreBallRadius = 11f / PPM;
	float radius = 32f / PPM;
	
	//final static float coreBugCorrection = 8.75f;
	final static float coreBugCorrection = 9.25f;//9.35f;
	float coreBugCorrection_ = coreBugCorrection;
	
	/*private static FloatArray camPosX = new FloatArray();
	private static FloatArray camPosY = new FloatArray();*/
	
	//private static Array<Float> camPosX = new Array<Float>();
	//private static Array<Float> camPosY = new Array<Float>();
	
	
	Color pupillaColor = new Color(.05f, 0.7f, .05f, 1);
	Color bodyColor = new Color(0, 0.75f, 0, 0.75f); //#00BF00
	//Color emptyColor = new Color(0, 0, 0, 0);
	
	Vector2 eye1pos = new Vector2(-21/PPM, 18/PPM);
	Vector2 eye2pos = new Vector2(21/PPM, 18/PPM);
	public Vector2 pupilla1 = new Vector2(-1,0);
	public Vector2 pupilla2 = new Vector2(1,0);
	Vector2 mouthPos1 = new Vector2(-15/PPM, -12/PPM);
	Vector2 mouthPos2 = new Vector2(15/PPM, -12/PPM);
	private float[] eye1avg, eye2avg, mouth1avg, mouth2avg;
	
	int SCORE = 0;
	int maxScore;
	private int maxHP = 20;
	public int HP = maxHP; //TODO private
	private int damageTicks = 0;
	private final int damageTicksMax = 8;
	
	//private float smile = 0.5f;
	
	public Body coreBall;
	public Array<Body> surfaceBalls;
	public Array<DistanceJoint> radialJoints, surfaceJoints;
	//public Array<DistanceJoint> radialExcenterJoints;
	private Vector2[] originPositions;
	//private RopeJoint coreJoint;

	public float slinkiness = 0;

	public boolean disableUserSlinkinessChange = false;
	
	Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
	Color pixmapColor = Color.GREEN;
    TextureRegion textureRegion;
	
	
	public BlobPlayer () {
		surfaceBalls = new Array<Body>();
		radialJoints = new Array<DistanceJoint>();
		surfaceJoints = new Array<DistanceJoint>();
		originPositions = new Vector2[numberOfOuterSpheres+1];
		
		eye1avg = new float[numberOfOuterSpheres+1];   //  // =============================================\
		eye2avg = new float[numberOfOuterSpheres+1];   // //   last element = total weight                  |
		mouth1avg = new float[numberOfOuterSpheres+1]; // \\   //nem aktuális [n-1]th el. = coreBody weight |
		mouth2avg = new float[numberOfOuterSpheres+1]; //  \\ =============================================/
		
		
		//					  Wi * Xi
		//súlyozott átlag = ------------
		//					 W1+W2+...Wn
		
	}
	
	public void spawn(World world, float x0, float y0) {
		
		CircleShape cs = new CircleShape();
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		FixtureDef fdef = new FixtureDef();
		fdef.restitution = 0.2f;
		fdef.friction = 0.5f;
		fdef.density = 0.45f;
		fdef.filter.categoryBits = BIT_BLOB;
		fdef.filter.maskBits = BIT_GROUND | BIT_BLOB | BIT_COLLECTIBLE | BIT_WALL | BIT_CEIL | BIT_BUNNY | BIT_ENEMY;
		
		//add surface balls
		
		cs.setRadius(surfaceBallRadius);
		fdef.shape = cs;
		bdef.fixedRotation = true;
		
		for (int i = 0; i < numberOfOuterSpheres; i++) {
			double angle = 2*Math.PI*i/numberOfOuterSpheres;
			float x = radius * (float) Math.sin(angle);
			float y = radius * (float) Math.cos(angle);
			Vector2 pos = new Vector2(x,y);
			originPositions[i] = pos;
			
			eye1avg[numberOfOuterSpheres] += (eye1avg[i] = 1/pow(pos.dst(eye1pos),1.4f));
			eye2avg[numberOfOuterSpheres] += (eye2avg[i] = 1/pow(pos.dst(eye2pos),1.4f));
			mouth1avg[numberOfOuterSpheres] += (mouth1avg[i] = 1/pow(pos.dst(mouthPos1),3));
			mouth2avg[numberOfOuterSpheres] += (mouth2avg[i] = 1/pow(pos.dst(mouthPos2),3));
			
			bdef.position.set(x0/PPM + x, y0/PPM + y);
			Body b = world.createBody(bdef);
			b.createFixture(fdef).setUserData("blob");
			surfaceBalls.add(b);
		}
		
		//add core ball
		
		bdef.position.set(x0/PPM, y0/PPM);
		cs.setRadius(coreBallRadius);
		fdef.shape = cs;
		bdef.fixedRotation = false;
		fdef.density = 0.55f;
		fdef.filter.categoryBits = BIT_BLOBCORE;
		coreBall = world.createBody(bdef);
		coreBall.createFixture(fdef).setUserData("blobCore");
		
		//TODOx calculate **coreBody** into weighted average

		
		
		//add radial joints
		
		DistanceJointDef jdef = new DistanceJointDef();
		jdef.bodyA = coreBall;
		jdef.frequencyHz = 4.5f;
		jdef.dampingRatio = 0.4f;
		//joint.length = radius*0.75f;
		
		/*for (Body b : surfaceBalls) {
			joint.bodyB = b;
			world.createJoint(joint);
		}*/
		for (int i=0; i<surfaceBalls.size; i++) {
			jdef.bodyB = surfaceBalls.get(i);
			double angle = 2*Math.PI*i/numberOfOuterSpheres;
			float x = (float)Math.sin(angle)*coreBallRadius*0.7f;
			float y = (float)Math.cos(angle)*coreBallRadius*0.7f;
			jdef.localAnchorA.set(x, y);
			//joint.length = joint.localAnchorA.dst(joint.localAnchorB);
			Vector2 vec1 = new Vector2(jdef.bodyA.getPosition().x + jdef.localAnchorA.x, jdef.bodyA.getPosition().y + jdef.localAnchorA.y);
			Vector2 vec2 = new Vector2(jdef.bodyB.getPosition().x + jdef.localAnchorB.x, jdef.bodyB.getPosition().y + jdef.localAnchorB.y);
			jdef.length = vec1.dst(vec2);
			//System.out.println("blob.spawn(..):\n x = "+x+"\n y = "+y+"\n");
			radialJoints.add((DistanceJoint) world.createJoint(jdef));
			jdef.localAnchorA.set(0, 0);
			jdef.length = jdef.bodyA.getPosition().dst(jdef.bodyB.getPosition());
			radialJoints.add((DistanceJoint) world.createJoint(jdef));
		}
		
		
		//add surface joints
		jdef.dampingRatio = 0.7f; jdef.frequencyHz = 4; jdef.localAnchorA.set(0, 0);
		
		int next;
		for (int i = 0; i<surfaceBalls.size; i++) {
			next = i+1;
			if (i == surfaceBalls.size-1) {
				next = 0;
			}
			Body a = surfaceBalls.get(i);
			Body b = surfaceBalls.get(next);
			jdef.bodyA = a;
			jdef.bodyB = b;
			jdef.length = a.getPosition().dst(b.getPosition());
			surfaceJoints.add((DistanceJoint) world.createJoint(jdef));
		}
	}
	
	public void jump(float f) {
		for (Body b : surfaceBalls) {
			b.applyForceToCenter(0, f, true);
		}
		coreBall.applyForceToCenter(0, f, true);
	}
	
	public void render(PolygonSpriteBatch polyBatch, ShapeRenderer sr) {
		Vector2[] controlPoints = new Vector2[surfaceBalls.size];
		Vector2 core = coreBall.getPosition();
		int c = 0;
		for (Body b : surfaceBalls) {
			Vector2 pos = b.getPosition();
			/*pos.x = ( core.x + (pos.x - core.x) * (radius+surfaceBallRadius) * 1.12f / radius ) * PPM;
			pos.y = ( core.y + (pos.y - core.y) * (radius+surfaceBallRadius) * 1.12f / radius ) * PPM;*/
			pos.x = ( core.x + (pos.x - core.x) * ((radius+surfaceBallRadius) + 4/PPM) / radius ) * PPM;
			pos.y = ( core.y + (pos.y - core.y) * ((radius+surfaceBallRadius) + 4/PPM) / radius ) * PPM;
			
			controlPoints[c++] = pos;
		}
		
		/* SOURCE: http://stackoverflow.com/questions/18641199/catmullromsplines-and-other-smooth-paths */
		CatmullRomSpline<Vector2> path = new CatmullRomSpline<Vector2>(controlPoints, true);
		
		final int RESOLUTION = numberOfOuterSpheres * (Game.lowPerformanceMode?3:4);
		float[] vertices = new float[RESOLUTION * 2];
		Vector2 pos = new Vector2();
		for (int i=0; i<RESOLUTION; i++) {
			path.valueAt(pos, i/(float)RESOLUTION);
			vertices[2*i] = pos.x;
			vertices[2*i+1] = pos.y;
		}

		Color color = bodyColor.cpy();
		float red = (damageTicks / (float)damageTicksMax) * 0.75f;
		color.a = 0.8f - slinkiness/2.5f;
		color.r = red;
		color.g = (1-red)*0.75f;
		if (pixmapColor!=color) {
			pix.setColor(color); //pix.setColor(0x00AD0055); //0xRRGGBBAA
		    pix.fill();
		    textureRegion = new TextureRegion(new Texture(pix));
		    pixmapColor = color;
		}
	    
	    ShortArray triangleIndices = new EarClippingTriangulator().computeTriangles(vertices);

	    PolygonSprite polySprite = new PolygonSprite(new PolygonRegion(textureRegion, vertices, triangleIndices.toArray()));    
	    
	    /* SOURCE: http://stackoverflow.com/questions/31030592/drawing-filled-polygon-with-libgdxs-earclippingtriangulator-and-polygonspriteba */
	    //////////////////////////render body
	    polyBatch.begin();
	    polyBatch.enableBlending();
	    polySprite.draw(polyBatch);
	    polyBatch.end();
		
		float eye1x = 0, eye1y = 0, eye2x = 0, eye2y = 0, mouth1x = 0, mouth1y = 0, mouth2x = 0, mouth2y = 0;
		c = 0;
		float avgX = 0, avgY = 0;
		
		for(Body b : surfaceBalls) {
			pos = b.getPosition();
			avgX += pos.x; avgY += pos.y;
			eye1x += (pos.x - core.x) * eye1avg[c];
			eye1y += (pos.y - core.y) * eye1avg[c];
			eye2x += (pos.x - core.x) * eye2avg[c];
			eye2y += (pos.y - core.y) * eye2avg[c];
			mouth1x += (pos.x - core.x) * mouth1avg[c];
			mouth1y += (pos.y - core.y) * mouth1avg[c];
			mouth2x += (pos.x - core.x) * mouth2avg[c];
			mouth2y += (pos.y - core.y) * mouth2avg[c];
			
			c++;
		}
		
		core.x *= PPM; core.y *= PPM;
		
		eye1x = core.x + eye1x * PPM / eye1avg[numberOfOuterSpheres];
		eye1y = core.y + eye1y * PPM / eye1avg[numberOfOuterSpheres];
		eye2x = core.x + eye2x * PPM / eye2avg[numberOfOuterSpheres];
		eye2y = core.y + eye2y * PPM / eye2avg[numberOfOuterSpheres];
		mouth1x = core.x + mouth1x * PPM / mouth1avg[numberOfOuterSpheres];
		mouth1y = core.y + mouth1y * PPM / mouth1avg[numberOfOuterSpheres];
		mouth2x = core.x + mouth2x * PPM / mouth2avg[numberOfOuterSpheres];
		mouth2y = core.y + mouth2y * PPM / mouth2avg[numberOfOuterSpheres];
		
		float mouthX = (mouth1x+mouth2x)/2f,  mouthY = (mouth1y+mouth2y)/2f;
		
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.GREEN); //szemfehérje
	    sr.circle(eye1x, eye1y, 5);
	    sr.circle(eye2x, eye2y, 5);
	    sr.setColor(pupillaColor); //pupilla
	    sr.circle(eye1x+pupilla1.x, eye1y+pupilla1.y, 2);
	    sr.circle(eye2x+pupilla2.x, eye2y+pupilla2.y, 2);
	    //sr.end();
	    
	    float mouthSize = (float) (Math.sqrt(pow(mouth1x-mouth2x,2) + pow(mouth1y-mouth2y,2)) / 3);
	    float angle = new Vector2(mouth2x-mouth1x, mouth2y-mouth1y).angle();
	    
	    
	    //TODO lekerekítés
	    //TODO színek felcserélése
	    //TODO lehetne kicsit nagyobb
	    
	    //száj
	    //sr.begin(ShapeType.Filled);
	    sr.setColor(bodyColor);
	    sr.arc(mouthX, mouthY, mouthSize, 180+angle, 180);
	    sr.setColor(Color.GREEN);
	    sr.arc(mouthX, mouthY, mouthSize*0.8f, 180+angle, 180);
	    sr.setColor(bodyColor);
	    sr.arc(mouthX, mouthY, mouthSize*0.6f, 180+angle, 180);
	    sr.end();
	    
	    //TODO felesleges rész kitörlése a szájból Pixmap-pel VAGY ShapeType.Line
	    //pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
	    
	    avgX = avgX*PPM/surfaceBalls.size;
		avgY = avgY*PPM/surfaceBalls.size;
		
		float dst = core.dst(new Vector2(avgX,avgY));
		
		//System.out.println("Blob core dst = "+dst+";   Blob.java:311");
		//if (frame++ % 20 == 0) System.out.println("Blob pos = "+core+";   Blob.java:318");
		
		if (dst>3) {
			//float x = core.x + (avgX-core.x)/(dst/8);
			if (dst>9.5) dst = 9.5f;
			//System.out.println("Core bug correction(x) = "+resultX);
			coreBall.setTransform((core.x + (((avgX - core.x) * (pow(dst / coreBugCorrection_, coreBugCorrection_))) / 2.5f)) / PPM, (core.y + (((avgY - core.y) * (pow(dst / coreBugCorrection_, coreBugCorrection_))) / 2.5f)) / PPM, coreBall.getAngle());
			
		}
		
		//if (slinkiness>getMaxSlinkiness()) setSlinkiness(getMaxSlinkiness());
		if (slinkiness<getMinSlinkiness()) changeSlinkinessTo(getMinSlinkiness());
		
		if (damageTicks>0) damageTicks--;
	}
	
	public static float pow(float f, float p) { return (float) Math.pow(f, p); }
	
	
	
	
	float[] camAvgPosXarray = new float[10];
	float[] camAvgPosYarray = new float[10];
	float camAvgPosX = 0, camAvgPosY = 0;
	int camAvgPosIndex = 0;
	boolean camAvgPosReady = false;
	
	private Vector2 corePos;
	int i_ = 0;
	
	public void calcAvgPos() {
		corePos = coreBall.getPosition();
		float x = corePos.x;
		float y = corePos.y;
		camAvgPosXarray[camAvgPosIndex]   = x;
		camAvgPosYarray[camAvgPosIndex++] = y;
		if (camAvgPosIndex == 10) { camAvgPosIndex = 0; camAvgPosReady = true; }
		if (camAvgPosReady) {
			camAvgPosX = 0; camAvgPosY = 0;
			for (i_=0; i_<10; i_++) {
				camAvgPosX += camAvgPosXarray[i_];
				camAvgPosY += camAvgPosYarray[i_];
			}
			camAvgPosX /= 10; camAvgPosY /= 10;
		} else {
			camAvgPosX = 0; camAvgPosY = 0;
			for (i_=0; i_<camAvgPosIndex; i_++) {
				camAvgPosX += camAvgPosXarray[i_];
				camAvgPosY += camAvgPosYarray[i_];
			}
			camAvgPosX /= camAvgPosIndex; camAvgPosY /= camAvgPosIndex;
		}
	}
	
	public float getAvgPosX() { return camAvgPosX; }
	public float getAvgPosY() { return camAvgPosY; }
	
	/*public float getAveragedPosX() {
		if (Game.lowPerformanceMode) return coreBall.getPosition().x;
		float x = coreBall.getPosition().x;
		camPosX.add(x);
		if (camPosX.size>10) camPosX.removeIndex(0);
		x = 0;
		for(float pos : camPosX) x += pos*2;
		return x/2/camPosX.size;
	}

	public float getAveragedPosY() {
		if (Game.lowPerformanceMode) return coreBall.getPosition().y;
		float y = coreBall.getPosition().y;
		camPosY.add(y);
		if (camPosY.size>10) camPosY.removeIndex(0);
		y = 0;
		for(float pos : camPosY) y += pos*2;
		return y/2/camPosY.size;
	}*/
	
	public void changeSlinkinessBy(float f) {
		if (disableUserSlinkinessChange) return;
		float prev = slinkiness;
		slinkiness += f;
		if (slinkiness < getMinSlinkiness()) slinkiness = getMinSlinkiness();
		else if (slinkiness<0) slinkiness = 0;
		else if (slinkiness>1) slinkiness = 1;
		if (prev!=slinkiness) {
			setSlinkiness(slinkiness);
			coreBugCorrection_ = coreBugCorrection - (1-slinkiness);
		}
	}
	
	public void changeSlinkinessTo(float f) {
		float prev = slinkiness;
		slinkiness = f;
		if (slinkiness < getMinSlinkiness()) slinkiness = getMinSlinkiness();
		else if (slinkiness<0) slinkiness = 0;
		else if (slinkiness>1) slinkiness = 1;
		if (prev!=slinkiness) setSlinkiness(slinkiness);
	}
	
	public void setSlinkiness(float f) {
		System.out.println("Slinkiness = "+f);
		//radial:  freq=4.5f; damp=0.4f
		//surface: freq=4f; damp=0.7f
		slinkiness = f;
		f *= 3;
		for (DistanceJoint j : radialJoints) {
			j.setFrequency(4.5f-f);
			j.setDampingRatio(0.4f-f/10);
		}
		for (DistanceJoint j : surfaceJoints) { //TODO nagyobb slinkiness >> nagyobb surfaeJoint freq
			j.setFrequency(4f+3*f);
			j.setDampingRatio(0.7f+f/1.5f);
		}
		for (Body b : surfaceBalls) {
			Fixture fixture = b.getFixtureList().get(0);
			fixture.setFriction(0.5f-slinkiness/10f);
			fixture.getShape().setRadius((8-slinkiness/2f)/PPM);
		}
		//7, 10.5
		Fixture fixture = coreBall.getFixtureList().get(0);
		fixture.setFriction(0.5f+slinkiness/10f);
		fixture.getShape().setRadius((11-slinkiness/2.5f)/PPM);
	}
	
	public float getMinSlinkiness() {
		return 1-(float)HP/(float)maxHP;
	}
	
	public boolean isOverEnemy(Enemy bunny) {
		Vector2 pos = coreBall.getPosition();
		Vector2 bunnyPos = bunny.getB2DPosition();
		//float xDist = Math.abs(pos.x-bunnyPos.x);
		//float yDist = 
		boolean b1 = (pos.y > bunnyPos.y);
		boolean b2 = (Math.abs(pos.x-bunnyPos.x) < (radius+surfaceBallRadius)*1.165f);
		System.out.println("FIGHT: "+b1+", "+b2); //DEBUG
		return (pos.y > bunnyPos.y) && (Math.abs(pos.x-bunnyPos.x) < (radius+surfaceBallRadius)*1.165f);
	}
	
	public void damage(int damage) {
		HP-=damage;
		if (HP<=0) {
			Game.gameOver = true;
			Game.gameOverFrame = Level1.frame;
			HP = 0;
		}
		System.out.println("BLOB HP: "+HP+"/"+maxHP);
		damageTicks = damageTicksMax;
		//TODO update slinkiness
		//TODO damage effect
	}
	
	public float getPosX() { return coreBall.getPosition().x; }
	public float getPosY() { return coreBall.getPosition().y; }
	public float dstLinear(float x, float y) {
		return Math.abs(getPosX()*PPM-x) + Math.abs(getPosY()*PPM-y);
	}
	public float dstLinear(Vector2 v) {
		return dstLinear(v.x, v.y);
	}
	
	//public Vector2 getPos() {return coreBall.getPosition();}

	//TODO move to Level1.java ??
	public int getScore() { return SCORE; }
	public int getMaxScore() { return maxScore; }
	public void setMaxScore(int size) { maxScore = size; }
	public void addScore() { SCORE ++; }

}
