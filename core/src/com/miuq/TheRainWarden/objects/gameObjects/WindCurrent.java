package com.miuq.TheRainWarden.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.objects.entities.Player;

/**
 * WindCurrent object that causes the player to fly when they collide with it and hold a button.
 * @author Luqman Patel
 */
public class WindCurrent extends InteractiveTileObject{

    private Player player;

    public WindCurrent(World world, Body body, Fixture sensorFixture, Player player) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.player = player;
    }

    /**
     * Code to be executed on collision.
     * @author Luqman Patel
     */
    @Override
    public void onCollision() {
        Gdx.app.log("Wind Current", "Collision");
        player.startWindCarry();
    }

    /**
     * Code to be executed once the player leaves the wind current.
     * @author Luqman Patel
     */
    public void endContact(){
        player.endWindCarry();
        Gdx.app.log("Wind Current", "End Collision");
    }

    /**
     * Sets the player to a new player object.
     * @param player New player object.
     * @author Luqman Patel
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public void update() {
        
    }    
}
