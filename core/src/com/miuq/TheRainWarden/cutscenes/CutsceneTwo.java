package com.miuq.TheRainWarden.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.animation.AnimatedText;

public class CutsceneTwo extends Cutscene {
    public CutsceneTwo(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen) {
        super(camera, viewport, game, gameScreen);

        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene2/frame1.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene2/frame2.png")));

        this.cutsceneTexts.add(new AnimatedText("hello world (frame 1)", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("hello world (frame 2)", 0.05f));
    }

    @Override
    public void render(float delta){
        renderCutscene(delta);
    }
}