package com.miuq.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.TheRainWarden.cutscenes.CutsceneThree;
import com.miuq.TheRainWarden.cutscenes.CutsceneTwo;

public class GameCutsceneLoader {
    private boolean cutscenePlayed;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TheRainWarden game;

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

    /**
     * Changes the cutscenePlayed boolean to false 1 level after a cutscene has finished. This ensures
     * that the cutscene does not repeatedly play after it has been finished.
     * @param level The players current level.
     * @author Luqman Patel
     */
    private void updateCutscenePlayedBoolean(int level){
        switch(level){
            case 3:
                cutscenePlayed = false;
                break;
            case 6:
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
        }
    }

    /**
     * Loads the cutscene Screen objects so that the cutscene can be played.
     * @param cutsceneNum The number of the cutscene to be played.
     * @param gameScreen The current Game Screen.
     * @author Luqman Patel
     */
    private void loadCutscene(int cutsceneNum, GameScreen gameScreen){
        if(!cutscenePlayed){
            cutscenePlayed = true;
            switch(cutsceneNum){
                case 2:
                    game.setScreen(new CutsceneTwo(camera, viewport, game, gameScreen));
                    break;
                case 3:
                    game.setScreen(new CutsceneThree(camera, viewport, game, gameScreen));
                    break;
            }
        }
    }
}
