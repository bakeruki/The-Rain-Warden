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
        
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/filler.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/filler.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/filler.png")));

        this.cutsceneTexts.add(new AnimatedText("filler text 1", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("filler text 2", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("filler text 3", 0.05f));
    }

    @Override
    public void render(float delta){
        renderCutscene(delta);
    }   
}
