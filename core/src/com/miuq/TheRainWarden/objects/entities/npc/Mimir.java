package com.miuq.TheRainWarden.objects.entities.npc;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.animation.AnimatedText;
import com.miuq.TheRainWarden.main.GameScreen;

public class Mimir extends NPC{
    private ArrayList<AnimatedText> eventOneDialogue;
    private ArrayList<Texture> eventOneExpressions;
    private GameScreen gameScreen;
    private boolean collisionDisabled;
    
    public Mimir(World world, Body body, Fixture sensorFixture, GameScreen gameScreen){
        super(world, body, sensorFixture);
        this.gameScreen = gameScreen;
        
        sensorFixture.setUserData(this);
        this.eventOneDialogue = new ArrayList<>();
        this.eventOneDialogue.add(new AnimatedText("You there.. come here.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I can feel that you possess great power. Oh that necklace...", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("This necklace represents the rain warden. The one who will stop the endless rain...", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("The endless rain... ah yes... It happened centuries ago... People and animals were trapped in a flodded earth", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Only the rain warden back then was able to possess the power to seal the curse. But now it has been broken again.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("I knew the rain warden personally. She was a courageous fighter. I have some of her power imbued in one of my spell books. Here, take this.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("*You received a new ability: thunder flash!*", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Good luck on your journey.", 0.1f));
        this.eventOneDialogue.add(new AnimatedText("Thank you!", 0.1f));
        this.dialogue.add(eventOneDialogue);

        this.eventOneExpressions = new ArrayList<>();
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.eventOneExpressions.add(new Texture("dialogue/expressions/lila/lilahappy.png"));
        this.expressions.add(eventOneExpressions);
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

    public void setBody(Body body){
        this.body = body;
    }

    public void setWorld(World world){
        this.world = world;
    }

    public void setFixture(Fixture sensorFixture){
        this.sensorFixture = sensorFixture;
        sensorFixture.setUserData(this);
    }

    @Override
    public void update() {

    }
}
