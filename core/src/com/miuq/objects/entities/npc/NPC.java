package com.miuq.objects.entities.npc;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Align;
import com.miuq.animation.AnimatedText;
import com.miuq.objects.gameObjects.InteractiveTileObject;

public abstract class NPC extends InteractiveTileObject{
    protected ArrayList<ArrayList<AnimatedText>> dialogue;
    protected ArrayList<Texture> expressions;
    protected int eventCount;
    private int frame;
    private GlyphLayout layout;
    private BitmapFont font;
    private boolean hasIncremented;

    public NPC(World world, Body body, Fixture sensorFixture){
        super(world, body, sensorFixture);
        this.dialogue = new ArrayList<>();
        this.expressions = new ArrayList<>();
        this.frame = 0;
        this.eventCount = 0;

        this.layout = new GlyphLayout();
        this.font = new BitmapFont();
    }

    public void increaseEventCount(){
        eventCount++;
    }

    public void nextFrame(){
        if(!dialogue.get(eventCount).get(frame).hasFinished()){
            dialogue.get(eventCount).get(frame).skipAnimation();
        }else{
            if(!hasIncremented){
                frame++;
                hasIncremented = true;
            }
        }
    }

    public boolean isDialogueEventFinished(){
        if(dialogue.get(eventCount).size() == frame){
            System.out.println("dialoge event finished");
            return true;
        }
        System.out.println("frame: " + frame + ", size: " + dialogue.get(eventCount).size());
        return false;
    }

    public void renderNPCDialogue(float delta, SpriteBatch batch){
        batch.draw(expressions.get(frame), 400, 800);
        dialogue.get(eventCount).get(frame).update(delta);
        layout.setText(font, dialogue.get(eventCount).get(frame).getText(), Color.WHITE, 700, Align.center, true);
        font.getData().setScale(2f);
        font.draw(batch, layout, Gdx.graphics.getWidth()/2-300, 1000);
        dialogue.get(eventCount).get(frame).update(delta);
        hasIncremented = false;
    }
}
