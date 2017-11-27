package hu.marazmarci.belatheblob.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.marazmarci.belatheblob.main.Game;
import hu.marazmarci.belatheblob.main.Game.GameMode;

import static hu.marazmarci.belatheblob.main.Game.gameMode;

public class DesktopLauncher {
	
	
	public static void main (String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		if(args.length>0) {
			if (args[0].equalsIgnoreCase("anna")) {
				gameMode = GameMode.ANNA;
			} else if (args[0].equalsIgnoreCase("livia")) {
				gameMode = GameMode.LIVIA;
			} else if (args[0].equalsIgnoreCase("mag")) {
				gameMode = GameMode.MAG;
			}
		}
		
		//gameMode = GameMode.MAG;
		//gameMode = GameMode.LIVIA;
		
		Game.initTitle();
		
		cfg.addIcon("images/bela.png", Files.FileType.Internal);
		
		cfg.title = Game.TITLE;
		cfg.width = Game.WIDTH * Game.SCALE;
		cfg.height = Game.HEIGHT * Game.SCALE;
		cfg.vSyncEnabled = true;
		//cfg.resizable = false;
		//cfg.foregroundFPS = 40;
		
		//int c=0;
		//for (String arg : args) System.out.println((c++) + ".arg: "+arg);
		
		new LwjglApplication(new Game(false), cfg);
	}
}
