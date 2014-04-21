package Game.impact.object;


import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import Game.impact.manager.ResourcesManager;
import Game.impact.scene.GameScene;


public abstract class Enemy extends Sprite
{
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	private Body body;
	private Sprite enemySprite;
	private PhysicsConnector pConnect;
	// ---------------------------------------------
	// CONSTRUCTORS
	// ---------------------------------------------
	public Enemy(Sprite sprite, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld)
	{
		super(sprite.getX(), sprite.getY(), sprite.getTextureRegion(), vbo);
		enemySprite = sprite;
		createPhysics(sprite.getTextureRegion(),physicsWorld);
	}
	
	public Enemy(float pX, float pY, ITextureRegion region, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld)
	{	
		super(pX, pY, region, vbo);
		createPhysics(region,physicsWorld);
	}
	
	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------

	
	private void createPhysics(ITextureRegion region, PhysicsWorld physicsWorld)
	{	
		FixtureDef def = PhysicsFactory.createFixtureDef(0, 0, 0);
		def.isSensor = true;
		if(region == ResourcesManager.bird_region)
		{	
			body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, def);
			body.setUserData(getSprite());
			body.setLinearVelocity(-3, 0);
		}

		if(region == ResourcesManager.platform2_region)
		{
			body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.KinematicBody, def);
			body.setUserData(getSprite());
		}
		
		pConnect = new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				
				if(getSprite().getTextureRegion() == ResourcesManager.bird_region)
				{  
					if (getX() - getWidth() <= 0)
						getBody().setLinearVelocity((getBody().getLinearVelocity().x * -1), 0);
		      
					if (getX() + getWidth() >= 680 )
						getBody().setLinearVelocity(getBody().getLinearVelocity().x * -1, 0);
				}
				
				if(getSprite().getTextureRegion() == ResourcesManager.platform2_region)
				{
					getBody().setLinearVelocity(0,0);
				}   
	        }
		};
		
		physicsWorld.registerPhysicsConnector(pConnect);

	}
	
	// --------
	// Getters
	// --------
	
	public Body getBody()
	{	
		return body;
	}
	
	public Sprite getSprite()
	{
		return enemySprite;
	}
	
	public PhysicsConnector getPhysicsConnector()
	{
		return pConnect;
	}
	
		
}//end
