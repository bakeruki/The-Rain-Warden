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

public class Persephone extends NPC{
    private ArrayList<AnimatedText> eventOneDialogue;
    private ArrayList<Texture> eventOneExpressions;
    private GameScreen gameScreen;
    private boolean collisionDisabled;

    public Persephone(World world, Body body, Fixture sensorFixture, GameScreen gameScreen) {
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.gameScreen = gameScreen;
        
        this.eventOneDialogue = new ArrayList<>();
        this.eventOneDialogue.add(new AnimatedText("Oh... Hello. Welcome to Springkeep Village. I'm Persephone.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Thanks.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("That necklace... If I'm not mistaken, are you the Rain Warden?", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Yes, I'm the Rain Warden.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Oh thank you for coming here. It means a lot.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("No need to thank me.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("That cherry blossom tree...", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Ah. This is the heart and soul of our village.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("This tree is the reason why the nearby forest is as beautiful as it is today.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("My job is to keep it in good shape. However when the rain fell down for the first time... The rain drowned our cherry blossom tree's soil.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Our cherry blossom tree is drowning, and when it does, our village and our people will drown. Everything we've accomplished will all go to waste!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Please rain warden, save our village.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I'll do everything I can.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Thank you.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Wow, you've changed in a matter of minutes.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Thank you for just realizing.", 0.1f));

        this.eventOneExpressions = new ArrayList<>();
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneNeutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneHappy.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneHappy.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneHappy.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneHappy.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneNeutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneSad.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneSad.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilasad.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/persephone/persephoneSad.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));

        this.expressions.add(eventOneExpressions);
        this.dialogue.add(eventOneDialogue);
    }

    @Override
    public void onCollision() {
        if(!collisionDisabled){
            Gdx.app.log("Persephone", "Begin Dialogue Event");
            gameScreen.setGameState(gameScreen.GAME_DIALOGUE);
            disableCollision();
        }else{
            Gdx.app.log("Persephone", "Collisions are disabled!");
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