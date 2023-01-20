package com.miuq.TheRainWarden.helper;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Credit: Small Pixel Games on YouTube
 * https://www.youtube.com/watch?v=8rBG7IWdDis&ab_channel=SmallPixelGames
 * Code is not the exactly the same, but was mainly taken from this video.
 */
public class BodyHelperService {
    /**
     * Creates a body.
     * @param x X coordinate to place the body.
     * @param y Y coordinate to place the body.
     * @param width Width of the body.
     * @param height Height of the body.
     * @param isStatic Whether the body will move or not.
     * @param world The world to place the body in.
     * @return
     */
    public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / Constants.PPM, y / Constants.PPM);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0;
        body.createFixture(fixtureDef);

        shape.dispose();
        
        return body;
    }

    /**
     * Creates Fixture that does not have physical collisions (used for sensing)
     */
    public static Fixture createSensorFixture(float x, float y, float width, float height, boolean isStatic, World world, Body body){
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);
    
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.isSensor = true;
            return(body.createFixture(fixtureDef));
    }
    
}
