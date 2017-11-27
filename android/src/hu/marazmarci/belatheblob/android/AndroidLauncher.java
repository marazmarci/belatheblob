package hu.marazmarci.belatheblob.android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import hu.marazmarci.belatheblob.main.GameMain;

public class AndroidLauncher extends AndroidApplication {
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi") @Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		cfg.hideStatusBar = true;
		cfg.useImmersiveMode = true;
		
		//TODO check device model ->  GameMain.gameVariant = GameMain.GameVariant.ANNA;
		
		GameMain.gameVariant = GameMain.GameVariant.DEFAULT;
		
		/*Point size = new Point(0,0);
		
		Display d = getWindowManager().getDefaultDisplay();
		
		if (android.os.Build.VERSION.SDK_INT>=13) {
			d.getSize(size);
			//System.out.println("getSize(): "+size);
			size = new Point(d.getWidth(),d.getHeight());
			//System.out.println("getWidth,getHeight(): "+size);
		} else {
			size = new Point(d.getWidth(),d.getHeight());
		}*/
		
		initialize(new GameMain(true), cfg);
	}
}