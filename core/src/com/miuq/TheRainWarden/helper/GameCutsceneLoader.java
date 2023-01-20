package com.miuq.TheRainWarden.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.cutscenes.CutsceneFive;
import com.miuq.TheRainWarden.cutscenes.CutsceneFour;
import com.miuq.TheRainWarden.cutscenes.CutsceneThree;
import com.miuq.TheRainWarden.cutscenes.CutsceneTwo;
import com.miuq.TheRainWarden.main.EndScreen;
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.main.TheRainWarden;

public class GameCutsceneLoader {
    private boolean cutscenePlayed;
    private boolean recentlyLoadedSave;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TheRainWarden game;
    private boolean cutsceneDisabled;

    public GameCutsceneLoader(OrthographicCamera camera, FitViewport viewport, TheRainWarden game){
        this.camera = camera;
        this.viewport = viewport;
        this.game = game;
        this.cutscenePlayed = false;
    }

    /**
     * Use in render method to perform all logic required for cutscenes to play at appropriate times.
     * @param level The players current level.
     * @param gameScreen The current Game Screen.
     */
    public void update(int level, GameScreen gameScreen){        
        updateCutscenePlayedBoolean(level);
        checkCutscene(level, gameScreen);
    }

    public void recentlyLoadedSave(){
        recentlyLoadedSave = true;
    }
    
    public void disableCutscene(){
        cutsceneDisabled = true;
    }

    /**
     * Changes the cutscenePlayed boolean to false 1 level after a cutscene has finished. This ensures
     * that the cutscene does not repeatedly play after it has been finished.
     * @param level The players current level.
     * @author Luqman Patel
     */
    private void updateCutscenePlayedBoolean(int level){
        switch(level){
            case 1:
                recentlyLoadedSave = false;
                break;
            case 3:
                cutscenePlayed = false;
                recentlyLoadedSave = false;
                break;
            case 4:
                recentlyLoadedSave = false;
                break;
            case 6:
                cutscenePlayed = false;
                recentlyLoadedSave = false;
                break;
            case 7:
                recentlyLoadedSave = false;
                break;
            case 9:
                cutscenePlayed = false;
                recentlyLoadedSave = false;
                break;
            case 10:
                recentlyLoadedSave = false;
                break;
            case 16:
                recentlyLoadedSave = false;
                cutscenePlayed = false;
                break;
        }
    }

    /**
     * Checks if a cutscene should be played.
     * @param level The players current level.
     * @param gameScreen The current Game Screen.
     * @author Luqman Patel
     */
    private void checkCutscene(int level, GameScreen gameScreen){
        switch(level){
            case 2:
                loadCutscene(2, gameScreen);
                break;
            case 5:
                loadCutscene(3, gameScreen);
                break;
            case 8:
                loadCutscene(4, gameScreen);
                break;
            case 11:
                loadCutscene(5, gameScreen);
                break;
            case 17:
                loadCutscene(6, gameScreen);
                break;
        }
    }

    /**
     * Loads the cutscene Screen objects so that the cutscene can be played.
     * @param cutsceneNum The number of the cutscene to be played.
     * @param gameScreen The current Game Screen.
     * @author Luqman Patel
     */
    private void loadCutscene(int cutsceneNum, GameScreen gameScreen){
        if(!cutscenePlayed && !recentlyLoadedSave){
            cutscenePlayed = true;
            switch(cutsceneNum){
                case 2:
                    CutsceneTwo cutscene = new CutsceneTwo(camera, viewport, game, gameScreen);
                    game.setScreen(cutscene);
                    if(cutsceneDisabled){
                        cutscene.disableCutscene();
                    }
                    break;
                case 3:
                    CutsceneThree cutscene2 = new CutsceneThree(camera, viewport, game, gameScreen);
                    game.setScreen(cutscene2);
                    if(cutsceneDisabled){
                        cutscene2.disableCutscene();
                    }
                    break;
                case 4:
                    CutsceneFour cutscene3 = new CutsceneFour(camera, viewport, game, gameScreen);
                    game.setScreen(cutscene3);
                    if(cutsceneDisabled){
                        cutscene3.disableCutscene();
                    }
                    break;
                case 5:
                    CutsceneFive cutscene4 = new CutsceneFive(camera, viewport, game, gameScreen);
                    game.setScreen(cutscene4);
                    if(cutsceneDisabled){
                        cutscene4.disableCutscene();   
                    }
                    break;
                case 6:
                    EndScreen end = new EndScreen(camera, viewport, game);
                    game.setScreen(end);
                    gameScreen.dispose();
                    break;
            }
        }
    }
}
