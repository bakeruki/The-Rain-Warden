package com.miuq.TheRainWarden.objects.entities.npc;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.animation.AnimatedText;
import com.miuq.TheRainWarden.main.GameScreen;

public class Renni extends NPC{
    private ArrayList<AnimatedText> eventOneDialogue;
    private ArrayList<Texture> eventOneExpressions;
    private ArrayList<AnimatedText> eventTwoDialogue;
    private ArrayList<Texture> eventTwoExpressions;
    private GameScreen gameScreen;
    private boolean collisionDisabled;

    public Renni(World world, Body body, Fixture sensorFixture, GameScreen gameScreen){
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.gameScreen = gameScreen;
        
        this.eventOneDialogue = new ArrayList<>();
        this.eventOneDialogue.add(new AnimatedText("Ugh.. that hurt. I am never doing that again.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Honestly, that was kind of fun.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("??? Who said that??", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Me.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("What the..!!!?", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Hehehe hi there fellow rain warden!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Rain warden..!!? What are you talking about !!?", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("You!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I have no idea what you are or where you came from, but I'm no rain warden.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I see.. you don't know the meaning of having this necklace, do you?", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I'm sure you recall your necklace glowing in the rain. Long story short, you were chosen to be the next rain warden and I'm here to guide you!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Okay... guide me to what exactly?", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("You'll see as we go. Come on, time for an adventure!", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Ugh... what a drag.", 0.1f));

        this.eventOneExpressions = new ArrayList<>();
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilasad.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilasad.png"));

        this.eventTwoDialogue = new ArrayList<>();
        this.eventTwoDialogue.add(new AnimatedText("Wow, this place is so pretty!", 0.1f));
        this.eventTwoDialogue.add(new AnimatedText("...", 0.1f));
        this.eventTwoDialogue.add(new AnimatedText("What's wrong?", 0.1f));
        this.eventTwoDialogue.add(new AnimatedText("I'm not sure. As beautiful as it is, I feel like this place is fooling us.", 0.1f));
        this.eventTwoDialogue.add(new AnimatedText("What do you mean?", 0.1f));
        this.eventTwoDialogue.add(new AnimatedText("Not sure, just a feeling. Lets keep moving.", 0.1f));

        this.eventTwoExpressions = new ArrayList<>();
        this.eventTwoExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventTwoExpressions.add(new Texture("dialogue/expressions/lila/lilasad.png"));
        this.eventTwoExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventTwoExpressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.eventTwoExpressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.eventTwoExpressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));

        this.dialogue.add(eventOneDialogue);
        this.dialogue.add(eventTwoDialogue);
        this.expressions.add(eventOneExpressions);
        this.expressions.add(eventTwoExpressions);
    }

    @Override
    public void onCollision() {
        if(!collisionDisabled){
            Gdx.app.log("Renni", "Begin Dialogue Event");
            gameScreen.setGameState(gameScreen.GAME_DIALOGUE);
            disableCollision();
        }else{
            Gdx.app.log("Renni", "Collisions are disabled!");
        }
        
    }

    @Override
    public void update() {

    }
    protected void disableCollision(){
        collisionDisabled = true;
    }

    public void enableCollision(){
        collisionDisabled = false;
    }
}
