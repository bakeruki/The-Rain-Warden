package com.miuq.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.objects.entities.Player;

public class Spike extends InteractiveTileObject {

    private boolean collided;
    private Player player;

    public Spike(World world, Body body, Fixture sensorFixture, Player player) {
        super(world, body, sensorFixture);
        this.player = player;
        sensorFixture.setUserData(this);
        collided = false;
    }

    @Override
    public void onCollision() {
        Gdx.app.log("Spike", "Collision");
        collided = true;
    }

    @Override
    public void update() {
        //trying to move the player in the collision method causes issues
        if(collided){
            System.out.println("collided event triggered");
            player.kill();
            collided = false;
        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }
}