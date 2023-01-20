package com.miuq.TheRainWarden.objects.entities.npc;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.animation.AnimatedText;
import com.miuq.TheRainWarden.helper.Constants;
import com.miuq.TheRainWarden.main.GameScreen;

public class Lu extends NPC{
    private ArrayList<AnimatedText> eventOneDialogue;
    private ArrayList<Texture> eventOneExpressions;
    private GameScreen gameScreen;
    private boolean collisionDisabled;
    
    public Lu(World world, Body body, Fixture sensorFixture, GameScreen gameScreen){
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.gameScreen = gameScreen;

        this.eventOneDialogue = new ArrayList<>();
        this.eventOneDialogue.add(new AnimatedText("Hi there. Welcome to Springkeep Village! The name's Lu.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I usually run that mango stall with my little brother Manny. Feel free to say hi to him as you walk by!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Springkeep used to be one of the most bountiful places in the world. We used to trade with every nation. However, due to the endless rain, all of our special fruits are drowning.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I hope you can save this village and this world Rain Warden.", 0.1f));

        this.eventOneExpressions = new ArrayList<>();
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lu/luNeutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lu/luNeutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lu/luSad.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lu/luSad.png"));

        this.expressions.add(eventOneExpressions);
        this.dialogue.add(eventOneDialogue);
    }

    @Override
    public void onCollision() {
        if(!collisionDisabled){
            Gdx.app.log("Lu", "Begin Dialogue Event");
            gameScreen.setGameState(gameScreen.GAME_DIALOGUE);
            disableCollision();
        }else{
            Gdx.app.log("Lu", "Collisions are disabled!");
        }
    }

    @Override
    public void update() {
        this.x = body.getPosition().x * Constants.PPM;
        this.y = body.getPosition().y * Constants.PPM;   
    }
    protected void disableCollision(){
        collisionDisabled = true;
    }

    public void enableCollision(){
        collisionDisabled = false;
    }
}
