package com.miuq.TheRainWarden.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.objects.entities.npc.Mimir;
import com.miuq.TheRainWarden.objects.entities.npc.NPC;
import com.miuq.TheRainWarden.objects.entities.npc.Persephone;
import com.miuq.TheRainWarden.objects.entities.npc.Renni;
import com.miuq.TheRainWarden.objects.entities.npc.Lu;
import com.miuq.TheRainWarden.objects.entities.npc.Manny;
import com.miuq.TheRainWarden.objects.entities.npc.Rose;

/**
 * Handles all dialogue events that occur in the game.
 * @author Luqman Patel
 */
public class GameDialogueHandler {
    /**
     * Keeps count of which dialogue event should be played next.
     */
    private int dialogueCount;
    /**
     * Tells whether the current event is still playing.
     */
    private boolean currentEventFinished;
    /**
     * GameScreen used to check info about current game state.
     */
    private GameScreen gameScreen;

    //all npcs
    private Mimir mimir;
    private Renni renni;
    private Persephone persephone;
    private Lu lu;
    private Manny manny;
    private Rose rose;

    /**
     * Stores the current npc that will be interacted with.
     */
    private NPC currentNPC;
   

    public GameDialogueHandler(GameScreen gameScreen){
        this.dialogueCount = 0;
        this.gameScreen = gameScreen;
    }

    /**
     * Draws the current NPC dialogue event.
     * @param delta Time since last render.
     * @param batch The games spritebatch.
     * @author Luqman Patel
     */
    private void drawCurrentEvent(float delta, SpriteBatch batch){
        switch(dialogueCount){
            case 0:
                currentNPC = renni;
                updateNPC(delta, batch);
                break;
            case 1:
                currentNPC = mimir;
                updateNPC(delta, batch);
                break;
            case 2:
                currentNPC = renni;
                updateNPC(delta, batch);
                break;
            case 3:
                currentNPC = persephone;
                System.out.println(persephone);
                System.out.println(currentNPC);
                updateNPC(delta, batch);
                break;
            case 4:
                currentNPC = lu;
                updateNPC(delta, batch);
                break;
            case 5:
                if(currentNPC == lu){
                    currentNPC = manny;
                }
                updateNPC(delta, batch);
                break;
            case 6:
                currentNPC = rose;
                updateNPC(delta, batch);
                break;
        }
    }

    /**
     * Code to be executed once a dialogue event has finished.
     * @author Luqman Patel
     */
    private void handleDialogueFinish(){
        currentEventFinished = true; //tells the dialogue handler that the current event is now finished.
        gameScreen.setGameState(gameScreen.GAME_RUNNING); //returns the game to the running state so that the player can move again.
        dialogueCount++; //increases the dialogue counter.
    }

    /**
     * This method manually sets the dialogue counter throughout the game so that there are 
     * no issues with save files, deaths, skipping dialogue events, ect.
     * @param level The current level.
     * @author Luqman Patel
     */
    private void updateDialogueCount(int level){
        switch(level){
            case 2:
                dialogueCount = 0;
                renni.setEventCount(0);
                break;
            case 3:
                dialogueCount = 1;
                mimir.setEventCount(0);
                break;
            case 5:
                dialogueCount = 2;
                renni.setEventCount(1);
                renni.setFrame(0);
                break;
            case 8:
                dialogueCount = 3;
                persephone.setEventCount(0);
                break;
            case 9:
                if(!lu.isDialogueEventFinished()){
                    dialogueCount = 4;
                }else if(!manny.isDialogueEventFinished()){
                    dialogueCount = 5;
                    currentEventFinished = false;
                }else if(!rose.isDialogueEventFinished()){
                    dialogueCount = 6;
                    currentEventFinished = false;
                }else{
                    dialogueCount = 7;
                }
                break;
        }
    }

    /**
     * Updates and renders the current NPC's dialogue event.
     * @param delta Time since last render.
     * @param batch The game's spritebatch.
     * @author Luqman Patel
     */
    private void updateNPC(float delta, SpriteBatch batch){
        if(currentNPC.isDialogueEventFinished()){
            handleDialogueFinish();
        }
        if(!isDialogueEventFinished()){
            currentNPC.renderNPCDialogue(delta, batch);
        }
    }

    /**
     * Increments the NPC's dialogue frame.
     * @author Luqman Patel
     */
    public void nextFrame(){
        currentNPC.nextFrame();
    }

    /**
     * Updates the dialogue count throughout the game.
     * @author Luqman Patel
     */
    public void update(){
        updateDialogueCount(gameScreen.getLevel());
    }

    /**
     * Draws the current dialogue event if it has not been finished.'
     * @param delta Time since last render.
     * @param batch The game's spritebatch.
     * @author Luqman Patel
     */
    public void draw(float delta, SpriteBatch batch){
        if(!currentEventFinished){
            drawCurrentEvent(delta, batch);
        }
    }

    /**
     * Sets the mimir variable to the Mimir object that is created when the tilemap is parsed.
     * @param mimir Mimir object used by the entire game.
     * @author Luqman Patel
     */
    public void setMimir(Mimir mimir){
        this.mimir = mimir;
    }

    /**
     * Sets the renni variable to the Renni object that is created when the tilemap is parsed.
     * @param renni Renni object used by the entire game.
     * @author Luqman Patel
     */
    public void setRenni(Renni renni){
        this.renni = renni;
    }

    /**
     * Sets the persephone variable to the Persephone object that is created when the tilemap is parsed.
     * @param persephone Persephone object used by the entire game.
     * @author Luqman Patel
     */
    public void setPersephone(Persephone persephone){
        this.persephone = persephone;
    }

    /**
     * Sets the lu variable to the Lu object that is created when the tilemap is parsed.
     * @param lu Lu object used by the entire game.
     * @author Luqman Patel
     */
    public void setLu(Lu lu){
        this.lu = lu;
    }

    /**
     * Sets the manny variable to the Manny object that is created when the tilemap is parsed.
     * @param manny Manny object used by the entire game.
     * @author Luqman Patel
     */
    public void setManny(Manny manny){
        this.manny = manny;
    }

    /**
     * Sets the rose variable to the Rose object that is created when the tilemap is parsed.
     * @param rose Rose object used by the entire game.
     * @author Luqman Patel
     */
    public void setRose(Rose rose){
        this.rose = rose;
    }

    /**
     * Sets the dialogue count
     * @param count The new dialogue count
     * @author Luqman Patel
     */
    public void setDialogueCount(int count){
        dialogueCount = count;
    }

    /**
     * Gets the stored Renni object
     * @return Returns Renni object
     * @author Luqman Patel
     */
    public Renni getRenni(){
        return renni;
    }

    /**
     * Gets the stored Mimir object
     * @return Returns Mimir object
     * @author Luqman Patel
     */
    public Mimir getMimir(){
        return mimir;
    }

    /**
     * Gets the stored Persephone object
     * @return Returns Persephone object
     * @author Luqman Patel
     */
    public Persephone getPersephone(){
        return persephone;
    }

    /**
     * Gets the stored Lu object
     * @return Returns Lu object
     * @author Luqman Patel
     */
    public Lu getLu(){
        return lu;
    }

    /**
     * Gets the stored Manny object
     * @return Returns Manny object
     * @author Luqman Patel
     */
    public Manny getManny(){
        return manny;
    }

    /**
     * Gets the stored Rose object
     * @return Returns Rose object
     * @author Luqman Patel
     */
    public Rose getRose(){
        return rose;
    }

    /**
     * Gets the stored dialogue count
     * @return Returns dialogue count object
     * @author Luqman Patel
     */
    public int getDialogueCount(){
        return dialogueCount;
    }

    /**
     * Tells whether the current dialogue event has ended.
     * @return True if dialogue event has finished, false if it has not finished.
     * @author Luqman Patel
     */
    public boolean isDialogueEventFinished(){
        return currentEventFinished;
    }

    /**
     * Enables the ability to enter a new dialogue event. Used to prevent looping events.
     * @author Luqman Patel
     */
    public void enableDialogueEvent(){
        currentEventFinished = false;
        if(renni != null){
            renni.enableCollision();
        }
        if(mimir != null){
            mimir.enableCollision();
        }
    }
}
