package com.miuq.TheRainWarden.objects.gameObjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Credit: Brent Aureli Codes on YouTube
 * https://www.youtube.com/watch?v=tcH6Mp03KC0&list=PLZm85UZQLd2SXQzsF-a0-pPF6IWDDdrXt&index=12&ab_channel=BrentAureliCodes
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
