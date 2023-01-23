package com.miuq.TheRainWarden.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.animation.AnimatedText;
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.main.TheRainWarden;

/**
 * Cutscene number 5 screen.
 * @author Michelle Vuong
 */
public class CutsceneFive extends Cutscene{
    public CutsceneFive(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen) {
        super(camera, viewport, game, gameScreen);
        
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene5/frame1.png")));

        this.cutsceneTexts.add(new AnimatedText("Up the mountain we go...", 0.05f));
    }

    @Override
    public void render(float delta){
        renderCutscene(delta);
    }   
}
