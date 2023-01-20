package com.miuq.TheRainWarden.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.helper.Constants;

/**
 * Stores the mango object, a collectible that can be picked up by the player.
 * @author Michelle Vuong
 */
public class Mango extends InteractiveTileObject {
    private boolean isRemoved;
    private Body toBeDestroyed;

    private float x;
    private float y;

    public Mango(World world, Body body, Fixture sensorFixture) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);

        this.world = world;
        this.body = body;
        this.sensorFixture = sensorFixture;
        this.isRemoved = false;

        this.x = body.getPosition().x * Constants.PPM;
        this.y = body.getPosition().y * Constants.PPM;
    }

    /**
     * Code to be run when the mango collides with the player.
     * @author Michelle Vuong
     */
    @Override
    public void onCollision() {
        Gdx.app.log("Mango", "Collision");
        removeBody(body);
    }

    /**
     * Updates the mango each game cycle.
     * @author Michelle Vuong
     */
    @Override
    public void update() {
        //destroying object in collision method causes issues
        if(toBeDestroyed != null){
            world.destroyBody(toBeDestroyed);
            toBeDestroyed = null;
            isRemoved = true;
        }

    }

    /**
     * Checks to see whether the mango has been removed from the world yet.
     * @return True if the mango is removed, false if the mango is not removed.
     * @author Michelle Vuong
     */
    public boolean isRemoved(){
        if(isRemoved){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Puts a mango back into the world.
     * @author Michelle Vuong
     */
    public void respawn(){
        isRemoved = false;
    }

    /**
     * Tells the code that the current object should be destroyed (calling in update method gives error, 
     * using this method ensures that one full game cycle finishes before it is destroyed)
     * @author Luqman Patel
     */
    private void removeBody(Body body){
        toBeDestroyed = body;
    }

    /**
     * Returns the X coordinate of the mango.
     * @return X coordinate of mango.
     * @author Michelle Vuong
     */
    public float getX(){
        return x;
    }

    /**
     * Returns the Y coordinate of the mango.
     * @return Y coordinate of mango.
     * @author Michelle Vuong
     */
    public float getY(){
        return y;
    }
}