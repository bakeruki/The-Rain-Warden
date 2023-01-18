package com.miuq.TheRainWarden.objects.entities.npc;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.helper.Constants;

public class Manny extends NPC{

    public Manny(World world, Body body, Fixture sensorFixture) {
        super(world, body, sensorFixture);
    }

    @Override
    public void onCollision() {
        
    }

    @Override
    public void update() {
        this.x = body.getPosition().x * Constants.PPM;
        this.y = body.getPosition().y * Constants.PPM;
    }
    
}
