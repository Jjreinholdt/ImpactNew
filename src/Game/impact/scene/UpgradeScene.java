package Game.impact.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import Game.impact.base.BaseScene;
import Game.impact.manager.SceneManager;
import Game.impact.manager.SceneManager.SceneType;

public class UpgradeScene extends BaseScene implements IOnMenuItemClickListener
{
	//---------------------------------------------
	// VARIABLES
	//---------------------------------------------
	
	private MenuScene menuChildScene;
	
	private final int LAUNCH = 0;
	private final int BOOST = 1;
	private final int U1 = 2;
	
	private int u1C = 0;
	//---------------------------------------------
	// METHODS FROM SUPERCLASS
	//---------------------------------------------

	@Override
	public void createScene()
	{
		createBackground();
		createMenuChildScene();
	}

	@Override
	public void onBackKeyPressed()
	{
		SceneManager.getInstance().loadMenuSceneFromUpgrade(engine);
	}

	@Override
	public SceneType getSceneType()
	{
		return SceneType.SCENE_UPGRADE;
	}
	

	@Override
	public void disposeScene()
	{
		// TODO Auto-generated method stub
	}
	
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY)
	{
		switch(pMenuItem.getID())
		{
			case LAUNCH:
				//Load Game Scene!
				if(u1C == 0 && resourcesManager.upgradePoints >= 300)
				{
					resourcesManager.launchVelocity += 10;
					resourcesManager.u1Pos +=100;
					u1C++;
					resourcesManager.upgradePoints -= 300;
					SceneManager.getInstance().loadMenuSceneFromUpgrade(engine);
				}
				else if(u1C == 1 && resourcesManager.upgradePoints >= 500)
				{
					resourcesManager.launchVelocity += 10;
					resourcesManager.u1Pos +=100;
					u1C++;
					resourcesManager.upgradePoints -= 500;
					SceneManager.getInstance().loadMenuSceneFromUpgrade(engine);
				}
				else if(u1C == 2 && resourcesManager.upgradePoints >= 700)
				{
					resourcesManager.launchVelocity += 10;
					resourcesManager.u1Pos +=100;
					u1C++;
					resourcesManager.upgradePoints -= 700;
					SceneManager.getInstance().loadMenuSceneFromUpgrade(engine);
				}
				return true;
			case BOOST:
				resourcesManager.boostValue += 4;
				return true;
			default:
				return false;
		}
	}
	
	//---------------------------------------------
	// CLASS LOGIC
	//---------------------------------------------
	
	private void createBackground()
	{
		attachChild(new Sprite(340, 600, resourcesManager.menu_background_region, vbom)
		{
    		@Override
            protected void preDraw(GLState pGLState, Camera pCamera) 
    		{
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
		});
	}
	
	private void createMenuChildScene()
	{
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(360,1045);
		
		final IMenuItem launchMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(LAUNCH, resourcesManager.launch_region, vbom), .5f, 1);
		//final IMenuItem boostMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(BOOST, resourcesManager.boost_region, vbom), 1.2f, 1);
		final IMenuItem u1MenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(U1, resourcesManager.u1_region, vbom), 1f, 1);
		
		menuChildScene.addMenuItem(launchMenuItem);
		//menuChildScene.addMenuItem(boostMenuItem);
		menuChildScene.addMenuItem(u1MenuItem);
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);
		
		launchMenuItem.setPosition(+300, 0);
		//boostMenuItem.setPosition(0, -55);
		u1MenuItem.setPosition(0+resourcesManager.u1Pos,0);
		
		menuChildScene.setOnMenuItemClickListener(this);
		
		setChildScene(menuChildScene);
	}
}
