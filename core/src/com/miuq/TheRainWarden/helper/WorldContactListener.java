package com.miuq.TheRainWarden.helper;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.miuq.TheRainWarden.objects.gameObjects.InteractiveTileObject;
import com.miuq.TheRainWarden.objects.gameObjects.WindCurrent;

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        //if one of the fixtures in the contact is the player object
        if(fixA.getUserData() == "player" || fixB.getUserData() == "player"){
            Fixture player = fixA.getUserData() == "player" ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            //checks if the collided object is a subclass of InteractiveTileObject
            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onCollision();
            }
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        
        if(fixA.getUserData() == "player" || fixB.getUserData() == "player"){
            Fixture player = fixA.getUserData() == "player" ? fixA : fixB;
            Fixture object = player == fixA ? fixB : fixA;

            //checks if the collided object is a subclass of WindCurrent
            if(object.getUserData() != null && WindCurrent.class.isAssignableFrom(object.getUserData().getClass())){
                ((WindCurrent) object.getUserData()).endContact();
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }
    
}
