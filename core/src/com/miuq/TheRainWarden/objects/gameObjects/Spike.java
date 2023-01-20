package com.miuq.TheRainWarden.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.objects.entities.Player;

/**
 * Spike object that respawns the player when collided with.
 * @author Luqman Patel
 */
public class Spike extends InteractiveTileObject {

    private boolean collided;
    private Player player;

    public Spike(World world, Body body, Fixture sensorFixture, Player player) {
        super(world, body, sensorFixture);
        this.player = player;
        sensorFixture.setUserData(this);
        collided = false;
    }

    /**
     * Code to be executed on collision
     * @author Luqman Patel
     */
    @Override
    public void onCollision() {
        Gdx.app.log("Spike", "Collision");
        collided = true;
    }

    /**
     * Updates the spike object. Continually checks to see if a collision has happened.
     * If it has, it respawns the player.
     * @author Luqman Patel
     */
    @Override
    public void update() {
        //trying to move the player in the collision method causes issues
        if(collided){
            System.out.println("collided event triggered");
            player.kill();
            collided = false;
        }
    }

    /**
     * Sets the player to a new player object.
     * @param player New player object.
     * @author Luqman Patel
     */
    public void setPlayer(Player player){
        this.player = player;
    }
}