package hu.marazmarci.belatheblob.handlers;

import hu.marazmarci.belatheblob.entities.B2DSprite;

public class Animation {
	
	//private TextureRegion[] frames;
	//static ArrayMap<Class<? extends B2DSprite>, TextureRegion[]> frames = new ArrayMap<Class<? extends B2DSprite>, TextureRegion[]>();
	private float time;
	private float delay;
	public int currentFrame;
	Class<? extends B2DSprite> clazz;
	private int length;
	//private int timesPlayed;
	
	public Animation(int length) { this(length, 1/12f); }
	
	public Animation(int length, float delay) {
		this.delay = delay;
		this.length = length;
		time = 0;
		currentFrame = 0;
	}
	
	//public void setFrames(TextureRegion[] frameData, Class<? extends B2DSprite> clazz, float delay) {
	/*public void setFramesa(float delay) {
		/*if (!frames.containsKey(clazz)) {
			frames.put(clazz, frameData);
			//DEBUG
			System.out.println("frames.size = "+frames.size+";  Animation.java:32");
			System.out.println("frames.containsKey(clazz) = "+frames.containsKey(clazz)+";  Animation.java:33");
			System.out.println("frames.get(clazz).length = "+frames.get(clazz).length+";  Animation.java:34");
			System.out.println("clazz.getSimpleName() = "+clazz.getSimpleName()+";  Animation.java:34");
			System.out.println("=============================");
			
		}*/
		//this.clazz = clazz;
		/*this.delay = delay;
		time = 0;
		currentFrame = 0;
		//timesPlayed = 0;
	}*/
	
	public void setDelay(float delay) {
		/*if (!frames.containsKey(clazz)) {
			frames.put(clazz, frameData);
			//DEBUG
			System.out.println("frames.size = "+frames.size+";  Animation.java:32");
			System.out.println("frames.containsKey(clazz) = "+frames.containsKey(clazz)+";  Animation.java:33");
			System.out.println("frames.get(clazz).length = "+frames.get(clazz).length+";  Animation.java:34");
			System.out.println("clazz.getSimpleName() = "+clazz.getSimpleName()+";  Animation.java:34");
			System.out.println("=============================");
			
		}*/
		//this.clazz = clazz;
		this.delay = delay;
		//timesPlayed = 0;
	}
	
	/*public void setFrames(TextureRegion[] frameData, Class<? extends B2DSprite> clazz) {
		setFrames(frameData, clazz, 1 / 12f);
	}*/
	
	public void update(float dt) {
		if (delay <= 0) return;
		time+=dt;
		while (time>=delay) {
			step();
		}
	}
	
	/*public TextureRegion getFrame() {
		//return frames[currentFrame];
		return frames.get(clazz)[currentFrame];
	}*/
	
	private void step() {
		time -= delay;
		currentFrame++;
		if (currentFrame == length) {
			currentFrame = 0;
			//timesPlayed++;
		}
	}

	//public TextureRegion[] getFrames() { return frames.get(clazz); }
	//public int getTimesPlayed() { return timesPlayed; }
	
}
