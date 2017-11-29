package hu.marazmarci.belatheblob.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.marazmarci.belatheblob.main.GameMain;
import hu.marazmarci.belatheblob.main.GameMain.GameVariant;

import static hu.marazmarci.belatheblob.main.GameMain.gameVariant;

public class DesktopLauncher {
	
	
	public static void main (String[] args) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		if(args.length>0) {
			if (args[0].equalsIgnoreCase("anna")) {
				gameVariant = GameVariant.ANNA;
			} else if (args[0].equalsIgnoreCase("livia")) {
				gameVariant = GameVariant.LIVIA;
			} else if (args[0].equalsIgnoreCase("mag")) {
				gameVariant = GameVariant.MAG;
			}
		}
		
		//gameVariant = GameVariant.MAG;
		//gameVariant = GameVariant.LIVIA;
		
		GameMain.initTitle();
		
		cfg.addIcon("images/bela.png", Files.FileType.Internal);
		
		cfg.title = GameMain.TITLE;
		cfg.width = GameMain.WIDTH * GameMain.SCALE;
		cfg.height = GameMain.HEIGHT * GameMain.SCALE;
		cfg.vSyncEnabled = true;
		//cfg.resizable = false;
		//cfg.foregroundFPS = 40;

        //int c=0;
		//for (String arg : args) System.out.println((c++) + ".arg: "+arg);
		
		new LwjglApplication(new GameMain(false), cfg);
	}

}
