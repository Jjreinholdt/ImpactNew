package Game.impact;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.view.KeyEvent;

import Game.impact.manager.ResourcesManager;
import Game.impact.manager.SceneManager;
import Game.impact.object.Player;


public class GameActivity extends BaseGameActivity
{
	private BoundCamera camera;
	public static Music myMusic;
	public static Music explosion1;
	public static Music explosion2;
	public static Music explosion3;
	public static boolean onOffToggle=false;
	
	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) 
	{
		return new LimitedFPSEngine(pEngineOptions, 60);
	}
	
	public EngineOptions onCreateEngineOptions()
	{
		camera = new BoundCamera(0, 0, 680, 1200);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.getRenderOptions().getConfigChooserOptions().setRequestedMultiSampling(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{  
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	SceneManager.getInstance();
			SceneManager.getCurrentScene().onBackKeyPressed();
	    }
	    switch (keyCode) 
	    {
        case KeyEvent.KEYCODE_D:
        	if(Player.getLaunch())
            Player.setVelocityX(15f);
            return true;
        case KeyEvent.KEYCODE_S:
        	if(Player.getLaunch())
        	Player.setVelocityX(-15f);
        	return true;
       
	    default: return false; 
	    }
	}

	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{
		
		ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager());
		MusicFactory.setAssetBasePath( "sfx/" );
		SoundFactory.setAssetBasePath("sfx/");
		try
		{	
		    myMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"group1-Menu.mp3");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			explosion1 = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"explodingsample.mp3");
			explosion2 = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"explodingsample.mp3");
			explosion3 = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this,"explodingsample.mp3");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException
	{
		SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
		GameActivity.myMusic.play();
		myMusic.setLooping(true);
	}

	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException
	{
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() 
		{
            public void onTimePassed(final TimerHandler pTimerHandler) 
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                SceneManager.getInstance().createMenuScene();
            }
		}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		System.exit(0);	
	}
	
	public static void toggle(Engine engine)
	{
		if(onOffToggle)
		{
			onOffToggle=false;
			engine.getMusicManager().setMasterVolume(0);
		}
		else
		{
			onOffToggle=true;
			engine.getMusicManager().setMasterVolume(1);
		}
	}

}