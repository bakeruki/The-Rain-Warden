package com.miuq.TheRainWarden.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.miuq.TheRainWarden.animation.AnimatedText;
import com.miuq.TheRainWarden.main.GameScreen;
import com.miuq.TheRainWarden.main.TheRainWarden;

public class CutsceneFour extends Cutscene{
    public CutsceneFour(OrthographicCamera camera, FitViewport viewport, TheRainWarden game, GameScreen gameScreen) {
        super(camera, viewport, game, gameScreen);

        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene4/frame1.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene4/frame2.png")));
        this.cutsceneImages.add(new Texture(Gdx.files.internal("cutscene/cutscene4/frame3.png")));

        this.cutsceneTexts.add(new AnimatedText("As I exit the forest, a set of houses strike my eyes.", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("Pink leaves gather in my hand as I approach the village.", 0.05f));
        this.cutsceneTexts.add(new AnimatedText("They lead me to the base of a brilliant pink tree, where a girl awaits me...", 0.05f));
    }

    @Override
    public void render(float delta){
        renderCutscene(delta);
    }
}