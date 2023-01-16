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
    private ArrayList<AnimatedText> eventOne;
    private GameScreen gameScreen;
    private boolean collisionDisabled;

    public Renni(World world, Body body, Fixture sensorFixture, GameScreen gameScreen){
        super(world, body, sensorFixture);
        sensorFixture.setUserData(this);
        this.eventOne = new ArrayList<>();
        this.eventOne.add(new AnimatedText("Ugh.. that hurt. I am never doing that again.", 0.1f));
        this.eventOne.add(new AnimatedText("Honestly, that was kind of fun.", 0.1f));
        this.eventOne.add(new AnimatedText("??? Who said that??", 0.1f));
        this.eventOne.add(new AnimatedText("Me.", 0.1f));
        this.eventOne.add(new AnimatedText("What the..!!!?", 0.1f));
        this.eventOne.add(new AnimatedText("Hehehe hi there fellow rain warden!", 0.1f));
        this.eventOne.add(new AnimatedText("Rain warden..!!? What are you talking about !!?", 0.1f));
        this.eventOne.add(new AnimatedText("You!", 0.1f));
        this.eventOne.add(new AnimatedText("I know you're talking about me.. who else is here?", 0.1f));
        this.eventOne.add(new AnimatedText("No one?", 0.1f));
        this.eventOne.add(new AnimatedText("*Sigh*", 0.1f));
        this.eventOne.add(new AnimatedText("I have no idea what you are or where you came from, but I'm no rain warden.", 0.1f));
        this.eventOne.add(new AnimatedText("I see.. you don't know the meaning of having this necklace, do you?", 0.1f));
        this.eventOne.add(new AnimatedText("Oh, you mean this necklace on my chest?", 0.1f));
        this.eventOne.add(new AnimatedText("What other necklace is there?", 0.1f));
        this.eventOne.add(new AnimatedText("...", 0.1f));
        this.eventOne.add(new AnimatedText("Got you good huh?", 0.1f));
        this.eventOne.add(new AnimatedText("Continue...", 0.1f));
        this.eventOne.add(new AnimatedText("I'm sure you recall your necklace glowing in the rain. Long story short, you were chosen to be the next rain warden and I'm here to guide you!", 0.1f));
        this.eventOne.add(new AnimatedText("Okay... guide me to what exactly?", 0.1f));
        this.eventOne.add(new AnimatedText("You'll see as we go. Come on, time for an adventure!", 0.1f));
        this.eventOne.add(new AnimatedText("Ugh... what a drag.", 0.1f));

        this.expressions.add(new Texture("dialogue/expressions/lila/lilasad.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaneutral.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilaconfused.png"));
        this.expressions.add(new Texture("dialogue/expressions/renni/renni.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilasad.png"));

        this.dialogue.add(eventOne);

        this.gameScreen = gameScreen;
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

    private void disableCollision(){
        collisionDisabled = true;
    }

    @Override
    public void update() {

    }
}
