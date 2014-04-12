package Game.impact.object;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import Game.impact.manager.ResourcesManager;


public abstract class Player extends AnimatedSprite
{
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	
	private static Body body;
	private float finalVelocity;
	private static boolean invulnerable = false;
	private static boolean launched = false;
	private static int launchSpeed;
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------
	
	public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, ResourcesManager.getInstance().player_region, vbo);
		createPhysics(camera, physicsWorld);
		camera.setChaseEntity(this);
	}
	
	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{		
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		body.setUserData("player");
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				
				if (getX() < 0) 
				{
					setVelocityX(body.getLinearVelocity().x+10);
				    setX(getWidth()/2);
				} else if (getX() + getWidth() > camera.getWidth()) 
				{
					setVelocityX(-body.getLinearVelocity().x-10);
				    setX(camera.getWidth() - getWidth()/2);
				}
	        }
		});
	}
	public void setLaunch(boolean x)
	{
		launched=x;
	}
	public static boolean getLaunch()
	{
		return launched;
	}
	public static Body getBody()
	{	
		return body;
	}
	public float getFinalVelocity()
	{
		return finalVelocity;
	}
	public void setFinalVelocity(float y)
	{
		finalVelocity=y;
	}
	
	public void launch() 
	{
		if(launched)
			return;
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x,36));
		launched = true;
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
