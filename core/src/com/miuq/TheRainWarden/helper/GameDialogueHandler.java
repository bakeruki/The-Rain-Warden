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

public class GameDialogueHandler {
    private int dialogueCount;
    private boolean currentEventFinished;
    private GameScreen gameScreen;

    private Mimir mimir;
    private Renni renni;
    private Persephone persephone;
    private Lu lu;
    private Manny manny;
    private Rose rose;

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

    private void handleDialogueFinish(){
        currentEventFinished = true;
        gameScreen.setGameState(gameScreen.GAME_RUNNING);
        dialogueCount++;
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

        System.out.println(dialogueCount);
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
        System.out.println("current NPC: " + currentNPC);
        System.out.println(manny);
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

    public void setPersephone(Persephone persephone){
        this.persephone = persephone;
    }

    public void setLu(Lu lu){
        this.lu = lu;
    }

    public void setManny(Manny manny){
        this.manny = manny;
    }

    public void setRose(Rose rose){
        this.rose = rose;
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

    public Persephone getPersephone(){
        return persephone;
    }

    public Lu getLu(){
        return lu;
    }

    public Manny getManny(){
        return manny;
    }

    public Rose getRose(){
        return rose;
    }

    public int getDialogueCount(){
        return dialogueCount;
    }

    public boolean isDialogueEventFinished(){
        return currentEventFinished;
    }

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
