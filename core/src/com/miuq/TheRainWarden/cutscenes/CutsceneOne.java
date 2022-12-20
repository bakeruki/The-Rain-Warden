package com.miuq.TheRainWarden.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.GameScreen;
import com.miuq.TheRainWarden.TheRainWarden;
import com.miuq.animation.AnimatedText;

public class CutsceneOne extends Cutscene{
    public CutsceneOne(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen){
        super(camera, viewport, game, gameScreen);

        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene1/frame1.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene1/frame2.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("assets/cutscene/cutscene1/frame3.png")));
    
        this.cutsceneTexts.add(new AnimatedText("There is something I have been holding onto ever since I was a child...", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("...", 0.3f));
        this.cutsceneTexts.add(new AnimatedText("And as the rain pours, it is starting to glow...", 0.05f));
    }

    @Override
    public void render(float delta){
        renderCutscene(delta);
    }
}