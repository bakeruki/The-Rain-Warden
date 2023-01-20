package com.miuq.TheRainWarden.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.helper.Constants;
import com.miuq.TheRainWarden.objects.entities.Player;

/**
 * The shiny raindrop object is a collectible that resets the player's dash counter when it is collected,
 * giving them an extra dash in the air.
 * @author Michelle Vuong
 */
public class ShinyRaindrop extends InteractiveTileObject {
    private Player player;
    private Body body;
    private Body toBeDestroyed;

    private float x;
    private float y;

    private boolean isRemoved;

    public ShinyRaindrop(World world, Body body, Fixture sensorFixture, Player player) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.world = world;
        this.player = player;
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
    public void onCollision(){
        if(!isRemoved){
            Gdx.app.log("Shiny Raindrop", "Collision");
            player.resetDashCounter();
            removeBody(body);
        }
    }

    /**
     * Updates the mango each game cycle.
     * @author Michelle Vuong
     */
    @Override
    public void update(){
        if(toBeDestroyed != null){
            toBeDestroyed = null;
            isRemoved = true;
        }
    }

    /**
     * Checks to see whether the shiny raindrop has been removed from the world yet.
     * @return True if the shiny raindrop is removed, false if the shiny raindrop is not removed.
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
     * Puts a shiny raindrop back into the world.
     * @author Michelle Vuong
     */
    public void respawn(){
        isRemoved = false;
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

    /**
     * Tells the code that the current object should be destroyed (calling in update method gives error, 
     * using this method ensures that one full game cycle finishes before it is destroyed)
     * @author Luqman Patel
     */
    public void removeBody(Body body){
        toBeDestroyed = body;
    }

    /**
     * Sets the player to a new player object.
     * @param player New player object.
     * @author Michelle Vuong
     */
    public void setPlayer(Player player){
        this.player = player;
    }
}
