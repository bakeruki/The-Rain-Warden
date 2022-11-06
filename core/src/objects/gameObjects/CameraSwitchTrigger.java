package objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;


/**
	 * Invisible sensor object that the player can collide with. Will change the location of the camera when hit.
     * @author Luqman Patel
	 */
public class CameraSwitchTrigger extends InteractiveTileObject {

    private boolean isRemoved;
    private Body toBeDestroyed;

    public CameraSwitchTrigger(World world, Body body, Fixture sensorFixture) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.isRemoved = false;
    }
    /**
	 * Checks and returns true or false based on if the object has been destroyed (collected) by the player
     * @author Luqman Patel
	 */
    public boolean isRemoved(){
        if(isRemoved){
            return true;
        }else{
            return false;
        }
    }

    /**
	 * Code that is run on collision.
     * @author Luqman Patel
	 */
    @Override
    public void onCollision() {
        Gdx.app.log("CameraTrigger", "Collision");
        removeBody(body);
    }
    /**
	 * Runs every game cycle. Destroys the object if it has been collected.
     * @author Luqman Patel
	 */
    @Override
    public void update() {
        if(toBeDestroyed != null){
            world.destroyBody(toBeDestroyed);
            toBeDestroyed = null;
            isRemoved = true;
        }

    }
     /**
	 * Tells the code that the current object should be destroyed (calling in update method gives error, 
     * using this method ensures that one full game cycle finishes before it is destroyed)
     * @author Luqman Patel
	 */
    private void removeBody(Body body){
        toBeDestroyed = body;
    }

}