package com.miuq.TheRainWarden.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.animation.AnimatedText;

public class CutsceneFour extends Cutscene{
    public CutsceneFour(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen) {
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