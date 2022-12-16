package com.miuq.objects.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.helper.Constants;

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

    @Override
    public void onCollision() {
        Gdx.app.log("Mango", "Collision");
        removeBody(body);
    }

    @Override
    public void update() {
        //destroying object in collision method causes issues
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

    public void respawn(){
        isRemoved = false;
    }

    private void removeBody(Body body){
        toBeDestroyed = body;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }
}