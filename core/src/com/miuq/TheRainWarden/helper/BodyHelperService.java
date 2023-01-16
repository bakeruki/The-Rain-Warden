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

    public static Fixture createSensorFixture(float x, float y, float width, float height, boolean isStatic, World world, Body body){
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(width / 2 / Constants.PPM, height / 2 / Constants.PPM);
    
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.isSensor = true;
            return(body.createFixture(fixtureDef));
    }
    
}
