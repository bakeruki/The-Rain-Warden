package com.miuq.TheRainWarden.objects.entities.npc;

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
import com.miuq.TheRainWarden.animation.AnimatedText;
import com.miuq.TheRainWarden.helper.Constants;
import com.miuq.TheRainWarden.objects.gameObjects.InteractiveTileObject;

/**
 * Abstract superclass for all non-player characters. They need to be 
 * seperate classes because they have their own colliders, handled in 
 * TileMapHelper.
 * @author Luqman Patel
 */
public abstract class NPC extends InteractiveTileObject{
    /**
     * ArrayList that stores sub-array lists of text for each dialogue event.
     */
    protected ArrayList<ArrayList<AnimatedText>> dialogue;
    /**
     * ArrayList that stores sub-array lists of all expressions for each dialogue event,
     */
    protected ArrayList<ArrayList<Texture>> expressions;
    /**
     * Whether collisions are currently disabled on the NPC.
     */
    protected boolean collisionDisabled;
    /**
     * Keeps track of which event the NPC is on.
     */
    private int eventCount;
    /**
     * Keeps track of the frame of each dialogue event,
     */
    private int frame;
    /**
     * X position of the NPC collider.
     */
    protected float x;
    /**
     * Y position of the NPC collider.
     */
    protected float y;
    /**
     * Used to modify font.
     */
    private GlyphLayout layout;
    /**
     * Font used to draw dialogue text.
     */
    private BitmapFont font;
    /**
     * Whether the frame has already incremented, used to prevent accidental double clicks in render method.
     */
    private boolean hasIncremented;

    public NPC(World world, Body body, Fixture sensorFixture){
        super(world, body, sensorFixture);
        this.dialogue = new ArrayList<>();
        this.expressions = new ArrayList<>();
        this.frame = 0;
        this.eventCount = 0;

        this.layout = new GlyphLayout();
        this.font = new BitmapFont();

        this.x = body.getPosition().x * Constants.PPM;
        this.y = body.getPosition().y * Constants.PPM;
    }

    /**
     * Increases the NPCs event counter.
     * @author Luqman Patel
     */
    public void increaseEventCount(){
        eventCount++;
    }

    /**
     * Sets the NPCs event counter.
     * @param count New count.
     * @author Luqman Patel
     */
    public void setEventCount(int count){
        eventCount = count;
    }

    /**
     * Logic for when the continue button is clicked.
     * @author Luqman Patel
     */
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

    /**
     * Checks if the NPCs event has finished.
     * @return True if event is finished. False if event is not finished.
     * @author Luqman Patel
     */
    public boolean isDialogueEventFinished(){
        if(dialogue.get(eventCount).size() == frame){
            return true;
        }
        return false;
    }

    /**
     * Renders all NPC dialogue text.
     * @param delta Time since last render.
     * @param batch SpriteBatch used to draw text.
     * @author Luqman Patel
     */
    public void renderNPCDialogue(float delta, SpriteBatch batch){
        batch.draw(expressions.get(eventCount).get(frame), 400, 800);
        dialogue.get(eventCount).get(frame).update(delta);
        layout.setText(font, dialogue.get(eventCount).get(frame).getText(), Color.WHITE, 700, Align.center, true);
        font.getData().setScale(2f);
        font.draw(batch, layout, Gdx.graphics.getWidth()/2-300, 1000);
        dialogue.get(eventCount).get(frame).update(delta);
        hasIncremented = false;
    }

    /**
     * Used to set the NPCs body.
     * @param body New body.
     * @author Luqman Patel
     */
    public void setBody(Body body){
        this.body = body;
    }

    /**
     * Used to set the world that the NPC is in.
     * @param world New world.
     * @author Luqman Patel
     */
    public void setWorld(World world){
        this.world = world;
    }

    /**
     * Gets x value of NPC collider.
     * @return X position of NPC collider.
     * @author Luqman Patel
     */
    public float getX(){
        return x;
    }

    /**
     * Gets y value of NPC collider.
     * @return Y position of NPC collider.
     * @author Luqman Patel
     */
    public float getY(){
        return y;
    }

    /**
     * Gets the frame that the dialogue event is on.
     * @return Frame that dialogue event is on.
     * @author Luqman Patel
     */
    public int getFrame(){
        return frame;
    }

    /**
     * Gets the event counter of the NPC.
     * @return Event counter of NPC.
     * @author Luqman Patel
     */
    public int getEventCount(){
        return eventCount;
    }

    /**
     * Sets the fixture of the NPC.
     * @param sensorFixture New fixture.
     * @author Luqman Patel
     */
    public void setFixture(Fixture sensorFixture){
        this.sensorFixture = sensorFixture;
        sensorFixture.setUserData(this);
    }

    /**
     * Sets the frame of the NPCs dialogue event.
     * @param frame New frame.
     * @author Luqman Patel
     */
    public void setFrame(int frame){
        this.frame = frame;
    }
}
