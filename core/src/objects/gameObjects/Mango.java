package objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

public class Mango extends InteractiveTileObject {

    private boolean isRemoved;
    private Body toBeDestroyed;

    public Mango(World world, Body body, Fixture sensorFixture) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);

        this.world = world;
        this.body = body;
        this.sensorFixture = sensorFixture;
        this.isRemoved = false;
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Mango", "Collision");
        removeBody(body);
    }

    @Override
    public void update() {
        if(toBeDestroyed != null){
            world.destroyBody(toBeDestroyed);
            toBeDestroyed = null;
            isRemoved = true;
        }

    }

    public boolean isRemoved(){
        if(isRemoved){
            return true;
        }else{
            return false;
        }
    }

    private void removeBody(Body body){
        toBeDestroyed = body;
    }

}