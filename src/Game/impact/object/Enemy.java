package Game.impact.object;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import Game.impact.manager.ResourcesManager;


public abstract class Enemy extends AnimatedSprite
{
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	
	private static Body body;
	
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------
	
	public Enemy(float pX, float pY, VertexBufferObjectManager vbo, PhysicsWorld physicsWorld)
	{
		super(pX, pY, ResourcesManager.getInstance().bird_region, vbo);
		createPhysics(physicsWorld);
	}
	
	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------
	
	private void createPhysics(PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		body.setUserData("enemy");
		body.setFixedRotation(true);
		body.setLinearVelocity(-8, 0);
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
			public void onUpdate(float pSecondsElapsed)
			{
				super.onUpdate(pSecondsElapsed);
				if (getX() - getWidth() <= 0)
		        {
					body.setLinearVelocity(body.getLinearVelocity().x * -1, 0);
		        }
		        if (getX() + getWidth() >= 680 )
		        {
		        	body.setLinearVelocity(body.getLinearVelocity().x * -1, 0);
		        }
			}
		});
	}
	public static Body getBody()
	{	
		return body;
	}

	public abstract void onDie();

	public static void setVelocityX(float f) 
	{
			body.setLinearVelocity(new Vector2(f,body.getLinearVelocity().y));
	}
	public void setVelocityY(float f)
	{
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x,f));
	}
		
		
}//end
