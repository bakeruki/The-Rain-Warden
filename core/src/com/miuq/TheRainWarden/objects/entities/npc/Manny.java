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

public class Manny extends NPC{
    private ArrayList<AnimatedText> eventOneDialogue;
    private ArrayList<Texture> eventOneExpressions;
    private GameScreen gameScreen;
    private boolean collisionDisabled;
    
    

    public Manny(World world, Body body, Fixture sensorFixture, GameScreen gameScreen) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.gameScreen = gameScreen;

        this.eventOneDialogue = new ArrayList<>();
        this.eventOneDialogue.add(new AnimatedText("Hi there miss! You want mango???", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Oh, sure!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("You received a mango!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("This is delicious!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Yay! Mango mangoes~", 0.1f));

        this.eventOneExpressions = new ArrayList<>();
        this.eventOneExpressions.add(new Texture("dialogue/expressions/manny/manny.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilahappy.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilahappy.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilahappy.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/manny/manny.png"));

        this.expressions.add(eventOneExpressions);
        this.dialogue.add(eventOneDialogue);
    }

    @Override
    public void onCollision() {
        if(!collisionDisabled){
            Gdx.app.log("Manny", "Begin Dialogue Event");
            gameScreen.setGameState(gameScreen.GAME_DIALOGUE);
            disableCollision();
        }else{
            Gdx.app.log("Manny", "Collisions are disabled!");
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
