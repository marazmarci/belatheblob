package hu.marazmarci.belatheblob.handlers;

import hu.marazmarci.belatheblob.main.Game;
import hu.marazmarci.belatheblob.main.Game.GameMode;
import hu.marazmarci.belatheblob.states.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

public class ContentManager {
	
	private static ArrayMap<String, Texture> textures;
	private static ArrayMap<String, Music> music;
	private static ArrayMap<String, Sound> sounds;
	public static Array<String> playingSounds;
	
	public static final String dir = "";
	
	public static Background mountains, clouds;
	public static TextureRegion zokni;
	
	public ContentManager() throws Exception {
		textures = new ArrayMap<String, Texture>();
		music = new ArrayMap<String, Music>();
		sounds = new ArrayMap<String, Sound>();
		//playingSoundsAndFinishActions = new ArrayMap<String,Task<?>>();
		playingSounds = new Array<String>();
		
		loadTexture("bgs");
		loadTexture("hud");
		loadTexture("bunny");
		loadTexture("redcrystal");
		//loadTexture("bluecrystal");
		loadTexture("dinnye");
		loadTexture("bean");
		loadTexture("sajt");
		//loadTexture("door");
		//loadTexture("dooranim");
		loadTexture("spike");
		loadTexture("spikes");
		loadTexture("grandma");
		loadTexture("cat");
		loadTexture("images/zokni.jpg","zokni");
		loadTexture("bossbunny");
		//loadTexture("key1");
		//loadTexture("key2");
		loadTexture("mushroom");
		loadTexture("pill1");
		loadTexture("pill2");
		loadTexture("heart");
		loadTexture("heart_empty");
		
		if (Game.gameMode == GameMode.ANNA) {
			loadTexture("ztepowerbank");
		} else if (Game.gameMode == GameMode.MAG || Game.gameMode == GameMode.LIVIA || Game.gameMode == GameMode.DEFAULT) {
			loadTexture("beer");
			loadTexture("kozel");
		} else {
			loadTexture("beer");
			loadTexture("kozel");
		}
		
			//true:wav, false:mp3
		loadSound("hit",true);
		loadSound("slimehit",false);
		loadSound("slimejump1",false);
		loadSound("slimejump2",false);
		loadSound("crystal",true);
		loadSound("magic1",false);
		loadSound("magic2",false);
		loadSound("ilonka1",false);
		loadSound("ilonka2",false);
		
		loadMusic("csecs");
		loadMusic("ytmnd");
	}
	
	/***********/
	/* Texture */
	/***********/
	
	public void loadTexture(String path, String key) throws Exception {
		if (textures.containsKey(key)) throw new Exception("Duplicate texture: {path="+path+"; key="+key+"}");
		//boolean success = true;
		try {
			textures.put(key, new Texture(Gdx.files.internal(dir+path)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//DEBUG
		System.out.println("TEXTURE LOADED: "+key+";  ContentManager.java:56");
	}
	
	public void loadTexture(String key) throws Exception {
		loadTexture("images/"+key+".png", key);
	}
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
	public void removeTexture(String key) {
		Texture tex = textures.get(key);
		if(tex != null) {
			textures.removeKey(key);
			tex.dispose();
		}
	}
	
	/*********/
	/* Music */
	/*********/
	
	public void loadMusic(String path, String key) throws Exception {
		if (music.containsKey(key)) throw new Exception("Duplicate music: {path="+path+"; key="+key+"}");
		Music mus = null;
		try {
			mus = Gdx.audio.newMusic(Gdx.files.internal(dir+path));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		music.put(key, mus);
		System.out.println("MUSIC LOADED: "+key+"  ("+path+");  ContentManager.java:114");
	}
	
	public void loadMusic(String key) throws Exception {
		loadMusic("music/"+key+".mp3", key);
	}
	
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	public Music playMusic(String key) {
		Music m = getMusic(key);
		m.play();
		return m;
	}
	
	public void playBossMusic() {
		Music m = getMusic("csecs");
		m.play();
		m.setVolume(0);
		Level1.tasks.add(new FadeTask<Music>(m, Level1.frame+180){
			public void run() {}

			@Override
			public void update() {
				data.setVolume(getVal());
				System.out.println("SET VOLUME: "+(float)(Level1.frame-start)/(float)(when-start));
			}
		});
	}

	public void stopBossMusic() {
		Music m = getMusic("csecs");
		Level1.tasks.add(new FadeTask<Music>(m, Level1.frame+300){
			
			public void run() {data.stop();}

			@Override
			public void update() {
				data.setVolume(1-getVal());
				System.out.println("SET VOLUME: "+(1-(float)(Level1.frame-start)/(float)(when-start)));
			}
		});
	}
	
	public void playBrainfuckMusic() {
		Music m = getMusic("ytmnd");
		m.play();
		m.setLooping(true);
	}

	public void stopBrainfuckMusic() {
		Music m = getMusic("ytmnd");
		Level1.tasks.add(new FadeTask<Music>(m, Level1.frame+120){
			
			public void run() {data.stop();}

			@Override
			public void update() {
				data.setVolume(1-getVal());
				System.out.println("SET VOLUME: "+(1-(float)(Level1.frame-start)/(float)(when-start)));
			}
		});
	}
	
	public void removeMusic(String key) {
		Music mus = music.get(key);
		if(mus != null) {
			music.removeKey(key);
			mus.dispose();
		}
	}
	
	/*******/
	/* SFX */
	/*******/
	
	public void loadSound(String path, String key) throws Exception {
		if (sounds.containsKey(key)) throw new Exception("Duplicate sound: {path="+path+"; key="+key+"}");
		Sound snd = null;
		try {
			snd = Gdx.audio.newSound(Gdx.files.internal(dir+path));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		sounds.put(key, snd);
		//System.out.println("YEAHYEAHYEAHYEAHYEAHYEAHYEAH");
		System.out.println("SOUND LOADED: "+key+"  ("+path+");  ContentManager.java:114");
	}
	
	public void loadSound(String key, boolean wavmp3) throws Exception {
		loadSound("sfx/"+key+'.'+(wavmp3?"wav":"mp3"), key);
		
		//System.out.println("Error loading sound: "+key+";  ContentManager.java:125"); }
	}
	
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	
	public void playSoundUnique(String key) {
		playSoundUnique(key,null);
	}
	
	public void playSound(String key) {
		getSound(key).play();
	}
	
	public void playSoundUnique(String key, Task<?> task) {
		//System.out.println("MUZSIKA");
		//new Exception().printStackTrace();
		if (playingSounds.contains(key,true)) return; //TODO lehet, hogy valamikor false kell
		sounds.get(key).play();
		playingSounds.add(key);
		Level1.tasks.add(task);
	}
	
	public void removeSound(String key) {
		Sound snd = sounds.get(key);
		if(snd != null) {
			sounds.removeKey(key);
			snd.dispose();
		}
	}
	
	/*********/
	/* Other */
	/*********/
	
	public void initBackgrounds(BoundedCamera cam) {
		Texture bgs = getTexture("bgs");
		mountains = new Background(new TextureRegion(bgs, 0, 240, 320, 240), cam, 0.2f);
		clouds = new Background(new TextureRegion(bgs, 0, 0, 320, 240), cam, 0.1f);
		//bgs.dispose();
		zokni = new TextureRegion(getTexture("zokni"));
	}
	
	public void removeAll() {
		for(Object o : textures.values()) {
			Texture tex = (Texture) o;
			tex.dispose();
		}
		textures.clear();
		for(Object o : music.values()) {
			Music music = (Music) o;
			music.dispose();
		}
		music.clear();
		for(Object o : sounds.values()) {
			Sound sound = (Sound) o;
			sound.dispose();
		}
		sounds.clear();
	}
	
	/*public String path(String s) {
		return Game.desktopOrAndroid ? s : ("assets/"+s);
	}*/

}
