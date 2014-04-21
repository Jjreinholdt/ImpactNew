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
import com.badlogic.gdx.physics.box2d.FixtureDef;

import Game.impact.manager.ResourcesManager;


public abstract class Player extends AnimatedSprite
{
	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------
	
	private Body body;
	private float finalVelocity;
	private boolean launched;
	private Vector2 last;
	//private static boolean invulnerable = false; 
	//private static int launchSpeed;
	
	// ---------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------
	
	public Player(float pX, float pY, VertexBufferObjectManager vbo, Camera camera, PhysicsWorld physicsWorld)
	{
		super(pX, pY, ResourcesManager.getInstance().player_region, vbo);
		launched = false;
		createPhysics(camera, physicsWorld);
		camera.setChaseEntity(this);
		last = body.getLinearVelocity();
	}
	
	// ---------------------------------------------
	// CLASS LOGIC
	// ---------------------------------------------
	
	private void createPhysics(final Camera camera, PhysicsWorld physicsWorld)
	{	
		FixtureDef def = PhysicsFactory.createFixtureDef(0, 0, 0);
		def.isSensor = true;
		body = PhysicsFactory.createBoxBody(physicsWorld, this, BodyType.DynamicBody, def);

		body.setUserData("player");
		body.setFixedRotation(true);
		
		physicsWorld.registerPhysicsConnector(new PhysicsConnector(this, body, true, false)
		{
			@Override
	        public void onUpdate(float pSecondsElapsed)
	        {
				super.onUpdate(pSecondsElapsed);
				camera.onUpdate(0.1f);
				
				if(body.getLinearVelocity().y-last.y >10)
					body.setLinearVelocity(last);
				
				if (getX() - getWidth() < 0) 
				{
					setVelocityX(body.getLinearVelocity().x+10);
				    setX(getWidth()/2);
				} else if (getX() + getWidth() > camera.getWidth()) 
				{
					setVelocityX(-body.getLinearVelocity().x-10);
				    setX(camera.getWidth() - getWidth()/2);
				}
				
				last = body.getLinearVelocity();
	        }
		});
		body.setActive(false);
	}
	public void setLaunch(boolean x)
	{
		launched=x;
	}
	public boolean getLaunch()
	{
		return launched;
	}
	public Body getBody()
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
	
	public void launch(int velocity) 
	{
		body.setActive(true);
		body.setLinearVelocity(new Vector2(body.getLinearVelocity().x,velocity));
		launched = true;
	}
	

	public abstract void onDie();

	public void setVelocityX(float f) 
	{
		if(launched)
			body.setLinearVelocity(f,body.getLinearVelocity().y);
	}
	public void setVelocityY(float f)
	{
		body.applyForce(new Vector2(0, f), body.getWorldCenter());
	}
		
		
}//end
