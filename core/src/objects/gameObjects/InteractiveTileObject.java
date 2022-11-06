package objects.gameObjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
	 * Abstract TileObject class. Used as superclass of all objects than can be interacted with by the player.
     * @author Luqman Patel
	 */
public abstract class InteractiveTileObject {
    
    protected Fixture sensorFixture;
    protected Body body;
    protected World world;

    protected Fixture fixture;

    public InteractiveTileObject(World world, Body body, Fixture sensorFixture){
        this.sensorFixture = sensorFixture;
        this.body = body;
        this.world = world;
    }

    public abstract void onCollision();

    public abstract void update();
    
}
