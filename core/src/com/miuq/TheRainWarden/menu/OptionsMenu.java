package com.miuq.TheRainWarden.menu;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.miuq.TheRainWarden.TheRainWarden;

public class OptionsMenu extends ScreenAdapter{
    private TheRainWarden game;
    private Stage stage;

    public OptionsMenu(TheRainWarden game){
        this.game = game;
        
        this.stage = new Stage();
    }

    private void handleSpeedrunClick(){
        //disable cutscenes and dialogue
    }

    private void handleDeathClick(){
        //show the death counter in the top right of the gamescreen
    }

    private void handleGodModeClick(){
        //player starts with all abilities unlocked
    }

    //speedrun toggle (no cutscenes)
    //display death counter in top right 
    //godmode toggle

    public Stage getStage(){
        return stage;
    }
}
