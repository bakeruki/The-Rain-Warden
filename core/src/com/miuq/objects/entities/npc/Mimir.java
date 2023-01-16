package com.miuq.objects.entities.npc;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.animation.AnimatedText;

public class Mimir extends NPC{
    private ArrayList<AnimatedText> eventOne;
    private GameScreen gameScreen;
    private boolean collisionDisabled;
    
    public Mimir(World world, Body body, Fixture sensorFixture, GameScreen gameScreen){
        super(world, body, sensorFixture);
        this.gameScreen = gameScreen;
        sensorFixture.setUserData(this);
        this.eventOne = new ArrayList<>();
        this.eventOne.add(new AnimatedText("You there.. come here.", 0.1f));
        this.eventOne.add(new AnimatedText("I can feel that you possess great power. Oh that necklace...", 0.1f));
        this.eventOne.add(new AnimatedText("This necklace represents the rain warden. The one who will stop the endless rain...", 0.1f));
        this.eventOne.add(new AnimatedText("The endless rain... ah yes... It happened centuries ago... People and animals were trapped in a flodded earth", 0.1f));
        this.eventOne.add(new AnimatedText("Only the rain warden back then was able to possess the power to seal the curse. But now it has been broken again.", 0.1f));
        this.eventOne.add(new AnimatedText("I knew the rain warden personally. She was a courageous fighter. I have some of her power imbued in one of my spell books. Here, take this.", 0.1f));
        this.eventOne.add(new AnimatedText("*You received a new ability: thunder flash!*", 0.1f));
        this.eventOne.add(new AnimatedText("Good luck on your journey.", 0.1f));
        this.eventOne.add(new AnimatedText("Thank you!", 0.1f));
        this.dialogue.add(eventOne);

        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/mimir/mimir.png"));
        this.expressions.add(new Texture("dialogue/expressions/lila/lilahappy.png"));
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
