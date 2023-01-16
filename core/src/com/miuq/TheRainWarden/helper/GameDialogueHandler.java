package com.miuq.TheRainWarden.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.objects.entities.npc.Mimir;
import com.miuq.TheRainWarden.objects.entities.npc.NPC;
import com.miuq.TheRainWarden.objects.entities.npc.Renni;

public class GameDialogueHandler {
    private int dialogueCount;
    private boolean currentEventFinished;
    private GameScreen gameScreen;

    private Mimir mimir;
    private Renni renni;
    private NPC currentNPC;

    public GameDialogueHandler(GameScreen gameScreen){
        this.dialogueCount = 0;
        this.gameScreen = gameScreen;
    }

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
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
        System.out.println("dialogue count:" + dialogueCount);
    }

    private void handleDialogueFinish(){
        currentEventFinished = true;
        System.out.println("dialogue count:" + dialogueCount);
        gameScreen.setGameState(gameScreen.GAME_RUNNING);
    }

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
                break;
        }
    }

    private void updateNPC(float delta, SpriteBatch batch){
        if(currentNPC.isDialogueEventFinished()){
            handleDialogueFinish();
        }
        if(!isDialogueEventFinished()){
            currentNPC.renderNPCDialogue(delta, batch);
        }
    }

    public void nextFrame(){
        currentNPC.nextFrame();
    }

    public void update(){
        updateDialogueCount(gameScreen.getLevel());
    }

    public void draw(float delta, SpriteBatch batch){
        if(!currentEventFinished){
            drawCurrentEvent(delta, batch);
        }
    }

    public void setMimir(Mimir mimir){
        this.mimir = mimir;
    }

    public void setRenni(Renni renni){
        this.renni = renni;
    }

    public void setDialogueCount(int count){
        dialogueCount = count;
    }

    public Renni getRenni(){
        return renni;
    }

    public Mimir getMimir(){
        return mimir;
    }

    public int getDialogueCount(){
        return dialogueCount;
    }

    public boolean isDialogueEventFinished(){
        return currentEventFinished;
    }

    public void enableDialogueEvent(){
        currentEventFinished = false;
    }
}
